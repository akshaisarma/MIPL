/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Binder.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Binder
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public abstract class Binder {
	abstract boolean bind(Goal goal, VariableStack vs, Solvable solver);
}
