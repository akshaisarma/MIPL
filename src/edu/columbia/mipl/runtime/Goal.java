/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Goal.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Goal
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class Goal extends Stack<Term> {
	List<Term> targetArguments;

	Goal(Term initialGoal) {
		targetArguments = initialGoal.getArguments();
		push(initialGoal);
	}
}
