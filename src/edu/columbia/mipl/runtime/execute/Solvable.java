/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Solvable.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Solvable
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

public interface Solvable {
	void solve(Goal goal, VariableStack vs);
}
