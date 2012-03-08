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
		TERM_TYPE_NOTTERM,
		TERM_TYPE_NUMBER,
	};
	Type type;

	double value;
	List<String> variables;
	String name;
	PrimitiveMatrix matrix;
	Expression expr1;
	Expression expr2;

	Term(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.TERM_TYPE_EQ || type == Type.TERM_TYPE_LT ||
			type == Type.TERM_TYPE_LE || type == Type.TERM_TYPE_GT ||
			type == Type.TERM_TYPE_GE || type == Type.TERM_TYPE_NE);

		this.type = type;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	Term(String variable, Expression expr) {
		type = Type.TERM_TYPE_IS;
		name = variable;
		expr1 = expr;
	}

	Term(double value) {
		type = Type.TERM_TYPE_NUMBER;
		this.value = value;
	}

	Term(int value) {
		this((double) value);
	}

	Term(String name, PrimitiveArray data) {
		this.name = name;
		matrix = new PrimitiveMatrix(data);
	}

	Term(String name, 

	Type getType() {
		return type;
	}

	double getValue() {
		assert (type == Type.TERM_TYPE_NUMBER);

		return value;
	}

	boolean evaluate(VariableStack vs) {
		switch (type) {
			case TERM_TYPE_IS:
				vs.setValue(variable, new Term(TERM_TYPE_NUMBER)
	}

}
