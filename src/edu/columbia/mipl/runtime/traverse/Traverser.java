/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Traverser.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Traverser
 */
package edu.columbia.mipl.runtime.traverse;

import java.util.*;

public interface Traverser {
	public enum Method {
		PRE,
		IN,
		POST,
	};

	Method getMethod();

	void reach(Traversable target);
	void finish();
}
