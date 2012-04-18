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
import java.util.regex.*;

import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

class KnowledgeDuplicator implements Traverser {
	Knowledge result;
	Traversable last;
	Stack<Traversable> stack;
	Map<Traversable, Traversable> cache;

	KnowledgeDuplicator() {
		stack = new Stack<Traversable>();
		cache = new HashMap<Traversable, Traversable>();
	}

	public Method getMethod() {
		return Method.POST;
	}

	public boolean reach(Traversable target) {
		int i;
		Traversable result = cache.get(target);
		last = target;
		if (result != null) {
			if (target instanceof Term && ((Term) target).getType() == Term.Type.EXPRESSION)
				stack.pop();
			if (target instanceof Term && ((Term) target).getType() == Term.Type.TERM)
				for (i = ((Term) target).getArguments().size(); i > 0; i--)
					stack.pop();
			if (target instanceof Term && ((Term) target).getType() == Term.Type.ANDTERMS) {
				stack.pop();
				stack.pop();
			}
			if (target instanceof Fact && ((Fact) target).getType() == Fact.Type.FACT)
				stack.pop();
			if (target instanceof Rule) {
				stack.pop();
				stack.pop();
			}
			stack.push(result);
			return true;
		}

		if (target instanceof Term) {
			Term t = (Term) target;
			Term.Type type = t.getType();
			switch (type) {
				case VARIABLE:
					result = new Term(type, t.getName());
					break;
				case NUMBER: 
					result = new Term(type, t.getValue());
					break;
				case STRING: 
					result = new Term(type, t.getName());
					break;
				case EXPRESSION: 
					result = new Term(type, (Expression) stack.pop());
					break;
				case MATRIX: 
					result = new Term(type, t.getName(), t.getMatrix());
					break;
				case ANDTERMS: 
					Term t1 = (Term) stack.pop();
					result = new Term(type, t1, (Term) stack.pop());
					break;
				case TERM: 
					i = t.getArguments().size();
					List<Term> args = new ArrayList<Term>();
					while (i-- > 0) {
						args.add((Term) stack.pop());
					}
					result = new Term(type, t.getName(), args);
					break;
				default:
					new Exception("Not Implemented!" + type).printStackTrace();
			}
		}
		else if (target instanceof Expression) {
			Expression e = (Expression) target;
		}
		else if (target instanceof Fact) {
			Fact f = (Fact) target;
			Fact.Type type = f.getType();
			switch (type) {
				case FACT:
					result = new Fact((Term) stack.pop());
					break;
				case MATRIXASFACTS:
					new Exception("Not reach here!").printStackTrace();
					break;
			}
		}
		else if (target instanceof Rule) {
			Term t = (Term) stack.pop();
			result = new Rule(t, (Term) stack.pop());
		}

		cache.put(target, result);
		stack.push(result);

		return true;
	}

	public static Knowledge duplicate(Knowledge k) {
		KnowledgeDuplicator duplicator = new KnowledgeDuplicator();
		k.traverse(duplicator);
		return duplicator.result;
	}

	public void finish() {
		result = (Knowledge) last;
	}
}

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
	String result = null;
	VariableStack vs;

	public StringGenerator(Term t) {
		this(t, null);
	}

	public StringGenerator(Term t, VariableStack vs) {
		this.vs = vs;
		stack = new Stack<String>();
		t.traverse(this);
	}

	public Method getMethod() {
		return Method.POST;
	}

	public String toString() {
		if (result == null)
			result = stack.pop();

		return result;
	}

	public static String matrixToString(PrimitiveMatrix matrix, int row) {
		int i;
		String result = "(";
		for (i = 0; i < matrix.getCol(); i++) {
			if (i > 0)
				result += ", ";
			result += matrix.getValue(row, i);
		}
		result += ")";

		return result;
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
					if (vs != null && vs.get(target) != null)
						stack.push(new StringGenerator(vs.get(target), vs).toString());
					else
						stack.push(target.getName());
					break;
				case MATRIX:
					PrimitiveMatrix matrix = target.getMatrix();
					if (matrix.getRow() != 1)
						new Exception("Multirow matrix query!").printStackTrace();
					stack.push(StringGenerator.matrixToString(matrix, 0));
					break;
				case TERM:
				case REGEXTERM:
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
				case QUERYALL:
					stack.push(target.getName() + "(*)");
					break;
				default:
					new Exception("Not implemented!" + t).printStackTrace();
			}
			return true;
		}
		else
			new Exception("Not implemented!" + t).printStackTrace();

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

	boolean match(Term target, Term source, VariableStack vs) {
//System.out.println("match(" + new StringGenerator(target) + ", " + new StringGenerator(source) + ")");
		if (target.getType() != Term.Type.VARIABLE &&
				target.getType() != Term.Type.QUERYALL &&
				target.getType() != Term.Type.REGEXQUERYALL &&
				target.getType() != Term.Type.REGEXTERM &&
				source.getType() != Term.Type.VARIABLE &&
				source.getType() != Term.Type.MATRIX &&
				target.getType() != source.getType()) {
			return false;
		}

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

		if (target.getType() == Term.Type.REGEXTERM || target.getType() == Term.Type.REGEXQUERYALL
				|| target.getType() == Term.Type.STRING) {
			Pattern p = Pattern.compile(target.getName());
			Matcher m = p.matcher(source.getName());
			if (!m.matches())
				return false;

			if (target.getType() == Term.Type.STRING)
				return true;
		}
		else if (!target.getName().equals(source.getName())) {
			return false;
		}

		if (source.getType() == Term.Type.TERM) {
			int size = 0;
			if (target.getArguments() != null)
				size = target.getArguments().size();
			int srcSize = 0;
			if (source.getArguments() != null)
				srcSize = source.getArguments().size();

			int i;

			if (size != srcSize) {
				return false;
			}

			List<Term> tgtArgs = target.getArguments();
			List<Term> srcArgs = source.getArguments();
			for (i = 0; i < size; i++) {
				if (!match(tgtArgs.get(i), srcArgs.get(i), vs)) {
					return false;
				}
			}
		}
		/* NUMBER, STRING, REGEX */
		else {
			//TODO
			new Exception("Not Implemented :" + source.getType()).printStackTrace();
		}

		return true;
	}

	boolean matchMatrix(Term target, PrimitiveMatrix<Double> matrix, int row, VariableStack vs) {
//System.out.println("matchMat(" + new StringGenerator(target) + ", " + StringGenerator.matrixToString(matrix, row) + ")");
		int i = 0;
		if (target.getType() == Term.Type.MATRIX) {
			PrimitiveMatrix<Double> tm = target.getMatrix();
			if (tm.getCol() != matrix.getCol()) {
				return false;
			}
			for (i = 0; i < tm.getCol(); i++) {
				if (Double.compare(tm.getValue(0, i), matrix.getValue(row, i)) != 0) {
					return false;
				}
			}
			return true;
		}
		else if (target.getType() == Term.Type.TERM) {
			List<Term> arguments = target.getArguments();
			if (arguments.size() != matrix.getCol())
				return false;
			for (Term arg : arguments) {
				if (arg.getType() == Term.Type.NUMBER) {
					if (Double.compare(matrix.getValue(row, i), arg.getValue()) != 0)
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

	boolean bind() {
		System.out.println(" ----- Query '" + new StringGenerator(goal.getInitialGoal()) + "?':");
		boolean result;
		if (goal.getInitialGoal().getType() == Term.Type.QUERYALL ||
				goal.getInitialGoal().getType() == Term.Type.REGEXQUERYALL)
			result = bindAll(goal, vs, this);
		else
			result = bind(goal, vs, this);

		return result;
	}

	Map<String, Term> createVariableMapForQueryAll(Term target, int argc) {
		Map<String, Term> map = new HashMap<String, Term>();
		List<Term> list = new ArrayList<Term>();
		while (argc-- > 0) {
			Term arg = new Term(Term.Type.VARIABLE, "X" + argc);
			map.put("X" + argc, arg);
			list.add(arg);
		}
		target.setArguments(list);
		return map;
	}

	boolean bindAll(Goal goal, VariableStack vs, Solvable solver) {
		Term currentGoal;

		currentGoal = goal.pop();

		List<Knowledge> knowledges;
		Term newTerm;
		Rule rule;

		/* use getRegex() for REGEXTERM */
		if (currentGoal.getType() == Term.Type.REGEXQUERYALL)
			knowledges = KnowledgeTableFactory.getKnowledgeTable().getRegex(currentGoal.getName());
		else
			knowledges = KnowledgeTableFactory.getKnowledgeTable().get(currentGoal.getName());
		if (knowledges == null)
			return false;

		for (Knowledge k : knowledges) {
			Knowledge knowledge = KnowledgeDuplicator.duplicate(k);
			VariableStack newVs = new VariableStack(vs);

			if (knowledge instanceof Job)
				continue;

			if (knowledge.getTerm().getType() == Term.Type.MATRIX) {
				PrimitiveMatrix<Double> matrix = knowledge.getTerm().getMatrix();
				if (knowledge instanceof Fact) {
					matrix.getData().printMatrix();
				}
				else if (knowledge instanceof Rule) {
					int i;
					for (i = 0; i < matrix.getRow(); i++) {
						VariableStack rowVs = new VariableStack(newVs);
						bindRule((Rule) knowledge, goal, rowVs, solver);
					}
				}
				continue;
			}

			int argc = 0;
			if (knowledge.getTerm().getArguments() != null)
				argc = knowledge.getTerm().getArguments().size();
			goal.setInitialVariableMap(createVariableMapForQueryAll(currentGoal, argc));

			if (!match(currentGoal, knowledge.getTerm(), newVs))
				continue;

			if (knowledge instanceof Fact) {
				bind(new Goal(goal), newVs, solver);
				continue;
			}

			bindRule((Rule) knowledge, goal, newVs, solver);
		}

		return true;
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

		/* use getRegex() for REGEXTERM */
		if (currentGoal.getType() == Term.Type.REGEXTERM)
			knowledges = KnowledgeTableFactory.getKnowledgeTable().getRegex(currentGoal.getName());
		else
			knowledges = KnowledgeTableFactory.getKnowledgeTable().get(currentGoal.getName());
		if (knowledges == null)
			return false;

		for (Knowledge k : knowledges) {
			Knowledge knowledge = KnowledgeDuplicator.duplicate(k);
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
						if (knowledge instanceof Fact)
							result = bind(new Goal(goal), rowVs, solver) || result;
						else if (knowledge instanceof Rule)
							result = bindRule((Rule) knowledge, goal, rowVs, solver) || result;
					}
				}
				continue;
			}

			/* goal == MATRIX && knowledge = Term */
			if (currentGoal.getType() == Term.Type.MATRIX) {
				VariableStack rowVs = new VariableStack(newVs);
				if (matchMatrix(knowledge.getTerm(), currentGoal.getMatrix(), 0, rowVs)) {
					if (knowledge instanceof Fact)
						result = bind(new Goal(goal), rowVs, solver) || result;
					else if (knowledge instanceof Rule)
						result = bindRule((Rule) knowledge, goal, rowVs, solver) || result;
				}
				continue;
			}

			if (!match(currentGoal, knowledge.getTerm(), newVs))
				continue;

			if (knowledge instanceof Fact) {
				result = bind(new Goal(goal), newVs, solver) || result;
				continue;
			}

			result = bindRule((Rule) knowledge, goal, newVs, solver) || result;
		}

		return result;
	}

	private boolean bindRule(Rule rule, Goal goal, VariableStack vs, Solvable solver) {
		boolean result = false;

		Term source = rule.getSource();
		Term orTermRhs = null;
		Goal newGoal = new Goal(goal);

		if (source.getType() == Term.Type.ORTERMS)
			orTermRhs = source;

		do {
			if (orTermRhs != null) {
				source = source.getTerm1();
				orTermRhs = source.getTerm2();
			}

			while (source.getType() == Term.Type.ANDTERMS) {
				newGoal.push(source.getTerm1());
				source = source.getTerm2();
			}
			assert (source.getType() == Term.Type.TERM);
			newGoal.push(source);

			result = bind(newGoal, vs, solver) || result;
		} while (orTermRhs != null);

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
				System.out.println(variable + " = " + new StringGenerator(valueTerm, vs));
		}
	}
}
