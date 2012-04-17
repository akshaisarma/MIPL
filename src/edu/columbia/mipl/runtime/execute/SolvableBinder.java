/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: SolvableBinder.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: SolvableBinder
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

class VariableGrouper implements Traverser {
	VariableStack vs;
	Map<String, Term> map;

	public VariableGrouper(VariableStack vs) {
		this.vs = vs;
		map = new HashMap<String, Term>();
	}

	public Method getMethod() {
		return Method.POST;
	}

	public boolean reach(Traversable target) {
		if (target instanceof Term) {
			Term t = (Term) target;
			if (t.getType() == Term.Type.VARIABLE) {
				Term exist = map.get(t.getName());
				if (exist == null)
					map.put(t.getName(), t);
				else
					vs.group(exist, t);
			}
			return true;
		}
		return false;
	}

	public Map<String, Term> getVariableMap() {
		return map;
	}

	public void finish() {
	}
}

class StringGenerator implements Traverser {
	Stack<String> stack;

	public StringGenerator(Term t) {
		stack = new Stack<String>();
		t.traverse(this);
	}

	public Method getMethod() {
		return Method.POST;
	}

	public String toString() {
		return stack.pop();
	}

	public boolean reach(Traversable t) {
		String line;
		int i;

		if (t instanceof Term) {
			Term target = (Term) t;
			switch(target.getType()) {
				case NUMBER:
					stack.push(new Double(target.getValue()).toString());
					break;
				case STRING:
				case VARIABLE:
					stack.push(target.getName());
					break;
				case TERM:
					line = target.getName();
					i = target.size();
					if (i > 0)
						line += "(";
					for (i--; i >= 0; i--) {
						line += stack.pop();
						if (i > 0)
							line += ", ";
					}
					if (target.size() > 0)
						line += ")";
					stack.push(line);
					break;
			}
			return true;
		}
		return false;
	}

	public void finish() {
	}
}

public class SolvableBinder extends Binder implements Solvable {
	Goal goal;
	VariableStack vs;

	SolvableBinder(Term term) {
		goal = new Goal(term);
		vs = new VariableStack();
		VariableGrouper grouper = new VariableGrouper(vs);
		term.traverse(grouper);
		goal.setInitialVariableMap(grouper.getVariableMap());
	}

	boolean bind() {
		System.out.println(" ----- Query '" + new StringGenerator(goal.getInitialGoal()) + "?':");
		boolean result = bind(goal, vs, this);
		return result;
	}

	boolean match(Term target, Term source, VariableStack vs) {
		if (target.getType() != Term.Type.VARIABLE &&
				source.getType() != Term.Type.VARIABLE &&
				source.getType() != Term.Type.MATRIX &&
				target.getType() != source.getType())
			return false;

		if (target.getType() == Term.Type.VARIABLE &&
				source.getType() == Term.Type.VARIABLE) {

			if (vs.group(target, source))
				return true;

			return match(vs.get(target), vs.get(source), vs);
		}
		else if (target.getType() == Term.Type.VARIABLE &&
				source.getType() != Term.Type.VARIABLE) {

			if (vs.get(target) == null) {
				vs.put(target, source); //TODO clone and put
				return true;
			}

			return match(vs.get(target), source, vs);
		}
		else if (target.getType() != Term.Type.VARIABLE &&
				source.getType() == Term.Type.VARIABLE) {

			if (vs.get(source) == null) {
				vs.put(source, target); //TODO clone and put
				return true;
			}

			return match(vs.get(source), target, vs);
		}

		int size = target.getArguments().size();
		int i;

		if (!target.getName().equals(source.getName()))
			return false;

		if (source.getType() == Term.Type.TERM) {
			if (size != source.getArguments().size())
				return false;

			List<Term> tgtArgs = target.getArguments();
			List<Term> srcArgs = source.getArguments();
			for (i = 0; i < size; i++) {
				if (!match(tgtArgs.get(i), srcArgs.get(i), vs))
					return false;
			}
		}
		/* NUMBER, MATRIX, STRING, REGEX */
		else { /* source.getType == Term.Type.MATRIX */
			//TODO
		}

		return true;
	}

	boolean matchMatrix(Term target, PrimitiveMatrix<Double> matrix, int row, VariableStack vs) {
		int i = 0;
		if (target.getType() == Term.Type.MATRIX) {
			PrimitiveMatrix<Double> tm = target.getMatrix();
			if (tm.getCol() != matrix.getCol())
				return false;
			for (i = 0; i < tm.getCol(); i++) {
				if (tm.getValue(0, i) != matrix.getValue(row, i))
					return false;
			}
			return true;
		}
		else if (target.getType() == Term.Type.TERM) {
			List<Term> arguments = target.getArguments();
			if (arguments.size() != matrix.getCol())
				return false;
			for (Term arg : arguments) {
				if (arg.getType() == Term.Type.NUMBER) {
					if (matrix.getValue(row, i) != arg.getValue())
						return false;
				}
				else if (arg.getType() == Term.Type.VARIABLE)
					vs.put(arg, new Term(Term.Type.NUMBER, matrix.getValue(row, i)));
				else
					return false;
				i++;
			}
			return true;
		}
		return false;
	}

	boolean bind(Goal goal, VariableStack vs, Solvable solver) {
		boolean result = false;
		Term currentGoal;

		if (goal.empty()) {
			solver.solve(goal, vs);
			return true;
		}

		currentGoal = goal.pop();

		List<Knowledge> knowledges;
		Term newTerm;
		Rule rule;

		knowledges = KnowledgeTableFactory.getKnowledgeTable().get(currentGoal.getName());
		if (knowledges == null)
			return false;

		for (Knowledge knowledge : knowledges) {
			VariableStack newVs = new VariableStack(vs);
			knowledge.traverse(new VariableGrouper(newVs));

			if (knowledge instanceof Job)
				continue;

			if (knowledge.getTerm().getType() == Term.Type.MATRIX) {
				int i;
				PrimitiveMatrix<Double> matrix = knowledge.getTerm().getMatrix();
				for (i = 0; i < matrix.getRow(); i++) {
					VariableStack rowVs = new VariableStack(newVs);
					if (matchMatrix(currentGoal, matrix, i, rowVs)) {
						result = bind(new Goal(goal), rowVs, solver) || result;
					}
				}
				continue;
			}

			if (!match(currentGoal, knowledge.getTerm(), newVs))
				continue;

			if (knowledge instanceof Fact) {
				result = bind(new Goal(goal), newVs, solver) || result;
				continue;
			}

			rule = (Rule) knowledge;
			Term source = rule.getSource();
			Term orTermRhs = null;

			if (source.getType() == Term.Type.ORTERMS)
				orTermRhs = source;

			do {
				if (orTermRhs != null) {
					source = source.getTerm1();
					orTermRhs = source.getTerm2();
				}

				while (source.getType() == Term.Type.ANDTERMS) {
					goal.push(source.getTerm1());
					source = source.getTerm2();
				}
				assert (source.getType() == Term.Type.TERM);
				goal.push(source);

				result = bind(new Goal(goal), newVs, solver) || result;
			} while (orTermRhs != null);
		}

		return result;
	}

	public void solve(Goal goal, VariableStack vs) {
		int i = 0;
		//new Exception().printStackTrace();
		Map<String, Term> variables = goal.getInitialVariableMap();

		for (String variable : variables.keySet()) {
			if (!variable.equals("_") && i == 0)
				System.out.println("A Possible Solution Set:");

			i++;

			Term valueTerm = vs.get(variables.get(variable));
			if (variable.equals("_"))
				i--;
			else if (valueTerm == null)
				System.out.println(variable + " = " + "(undecided)");
			else
				System.out.println(variable + " = " + new StringGenerator(valueTerm));
		}
	}
}
