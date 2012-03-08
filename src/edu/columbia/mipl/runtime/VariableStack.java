/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: VariableStack.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: VariableStack
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class VariableStack {
	Map<String, Term> variables;

	Term getValue(String name) {
		return variables.get(name);
	}
}
