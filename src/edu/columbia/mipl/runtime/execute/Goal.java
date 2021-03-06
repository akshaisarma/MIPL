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

public class Goal extends LinkedList<Term> {
	Map<String, Term> initialVariableMap;
	Term initialGoal;

	Goal(Term initialGoal) {
		this.initialGoal = initialGoal;
		add(initialGoal);
	}

	Goal(Goal clone) {
		addAll(clone);
		initialVariableMap = clone.initialVariableMap;
		initialGoal = clone.initialGoal;
	}

	void setInitialVariableMap(Map<String, Term> map) {
		initialVariableMap = map;
	}

	Map<String, Term> getInitialVariableMap() {
		return initialVariableMap;
	}

	Term getInitialGoal() {
		return initialGoal;
	}
}
