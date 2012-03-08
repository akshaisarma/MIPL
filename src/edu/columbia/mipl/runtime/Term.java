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

import edu.columbia.mipl.datastr.*;

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
		TERM_TYPE_ANDTERMS,
		TERM_TYPE_ORTERMS,
		TERM_TYPE_NOTTERM,
		TERM_TYPE_NUMBER,
		TERM_TYPE_VARIABLE,
	};
	Type type;

	double value;
	List<Term> arguments;
	String name;
	PrimitiveMatrix matrix;
	Expression expr1;
	Expression expr2;
	Term term1;
	Term term2;

	Term(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.TERM_TYPE_EQ || type == Type.TERM_TYPE_LT ||
			type == Type.TERM_TYPE_LE || type == Type.TERM_TYPE_GT ||
			type == Type.TERM_TYPE_GE || type == Type.TERM_TYPE_NE);

		this.type = type;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	/* X is Y - 1 */
	Term(Type type, String variable, Expression expr) {
		assert (type == Type.TERM_TYPE_IS);

		this.type = type;
		name = variable;
		expr1 = expr;
	}

	Term(Type type, double value) {
		assert (type == Type.TERM_TYPE_NUMBER);

		this.type = type;
		this.value = value;
	}

	Term(Type type, int value) {
		this(type, (double) value);
	}

	Term(Type type, String name, PrimitiveArray data) {
		this(type, name, new PrimitiveMatrix(data));
	}

	Term(Type type, String name, PrimitiveMatrix matrix) {
		assert (type == Type.TERM_TYPE_MATRIX);

		this.type = type;
		this.name = name;
		this.matrix = matrix;
	}

	Term(Type type, Term term1, Term term2) {
		assert (type == Type.TERM_TYPE_ANDTERMS || type == Type.TERM_TYPE_ORTERMS);

		this.type = type;
		this.term1 = term1;
		this.term2 = term2;
	}

	Term(Type type, String name, List<Term> arguments) {
		assert (type == Type.TERM_TYPE_TERM || type == Type.TERM_TYPE_NOTTERM);

		this.type = type;
		this.name = name;
		this.arguments = arguments;
	}

	Term(String name) {
		assert (type == Type.TERM_TYPE_VARIABLE);

		this.type = type;
		this.name = name;
	}

	Type getType() {
		return type;
	}

	double getValue() {
		assert (type == Type.TERM_TYPE_NUMBER);

		return value;
	}
/*
	boolean evaluate(VariableStack vs) {
		switch (type) {
			case Type.TERM_TYPE_IS:
				vs.setValue(variable, new Term(Type.TERM_TYPE_NUMBER)
	}
*/
}
