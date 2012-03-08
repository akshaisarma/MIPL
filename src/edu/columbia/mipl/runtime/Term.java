/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Term.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Term
 */
package edu.columbia.mipl.runtime;

import java.util.*;
import java.lang.reflect.*;

public class Term {
	public enum Type {
		TERM_TYPE_IS,
		TERM_TYPE_EQ,
		TERM_TYPE_LT,
		TERM_TYPE_LE,
		TERM_TYPE_GT,
		TERM_TYPE_GE,
		TERM_TYPE_NE,
		TERM_TYPE_MATRIX,
		TERM_TYPE_TERM,
		TERM_TYPE_NOTTERM,
		TERM_TYPE_NUMBER,
	};
	Type type;

	double value;

	Type getType() {
		return type;
	}

	double getValue() {
		assert (type == Type.TERM_TYPE_NUMBER);

		return value;
	}
}
