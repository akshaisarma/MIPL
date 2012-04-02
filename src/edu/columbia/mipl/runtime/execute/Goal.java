/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Goal.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Goal
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.runtime.*;

public class Goal extends Stack<Term> {
	List<Term> targetArguments;

	Goal(Term initialGoal) {
		targetArguments = initialGoal.getArguments();
		push(initialGoal);
	}

	Goal(Goal clone) {
		addAll(clone);
		targetArguments = new ArrayList<Term>();
		targetArguments.addAll(clone.targetArguments);
	}

	List<Term> getTargetArguments() {
		return targetArguments;
	}
}
