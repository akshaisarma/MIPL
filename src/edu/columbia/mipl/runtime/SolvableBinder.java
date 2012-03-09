/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: SolvableBinder.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: SolvableBinder
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class SolvableBinder extends Binder implements Solvable {
	Goal goal;
	VariableStack vs;

	SolvableBinder(Goal goal) {
		this.goal = goal;
		vs = new VariableStack();
	}

	boolean bind() {
		return bind(goal, vs, this);
	}

	boolean bind(Goal goal, VariableStack vs, Solvable solver) {
		boolean result = false;
		Term currentGoal = goal.pop();

		while (currentGoal == null) {
			Knowledge know;
			Term newTerm;
			Rule rule;

			know = KnowledgeTableFactory.getKnowledgeTable().get(currentGoal.getName());
			if (know == null)
				return false;

			newTerm = know.getTerm();
			if (!currentGoal.match(newTerm, vs))
				return false;
	
			if (know instanceof Fact) {
				solver.solve(goal, vs);
				return true;
			}
			rule = (Rule) know;
			Term source = rule.getSource();

			if (source.getType() == Term.Type.ANDTERMS) {
				goal.push(source.getTerm2());
				goal.push(source.getTerm1());
			}
			else if (source.getType() == Term.Type.ORTERMS) {
				goal.push(source.getTerm1());
				result = result || bind(new Goal(goal), new VariableStack(vs), solver);
				goal.push(source.getTerm2());
			}
		}
		solver.solve(goal, vs);

		return true;
	}

	public boolean solve(Goal goal, VariableStack vs) {
		if (vs.keySet().containsAll(goal.getTargetArguments())) {
			// print
		}
		return true;
	}
}
