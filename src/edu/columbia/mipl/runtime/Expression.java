/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Expression.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Expression
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class Expression {
	public enum Type {
		EXPR_TYPE_VARIABLE,
		EXPR_TYPE_INTEGER,
		EXPR_TYPE_DOUBLE,
		EXPR_TYPE_MINUS,
		EXPR_TYPE_PLUS,
		EXPR_TYPE_MULTI,
		EXPR_TYPE_DIVIDE,
	};
	Type type;

	Term variable;
	double value;

	Expression left;
	Expression right;

	public Expression(Type type, Term term) {
		assert (type == Type.EXPR_TYPE_VARIABLE);
		assert (term.getType() == Term.Type.TERM_TYPE_VARIABLE);

		variable = term;
	}

	public Expression(Type type, int value) {
		this(type, (double) value);
	}

	public Expression(Type type, double value) {
		assert (type == Type.EXPR_TYPE_INTEGER || type == Type.EXPR_TYPE_DOUBLE);
		
		this.value = value;
	}

	public Expression(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.EXPR_TYPE_MINUS || type == Type.EXPR_TYPE_PLUS ||
			type == Type.EXPR_TYPE_MULTI || type == Type.EXPR_TYPE_DIVIDE);

		left = expr1;
		right = expr2;
	}

	double calculateValue(VariableStack vs) /* throws InsuffArgInitException, NonArithmeticArgException */ {
		switch (type) {
			case EXPR_TYPE_VARIABLE:
				Term term  = vs.get(variable);
				if (term == null)
					/* throw new InsuffArgInitException() */;
				if (term.getType() != Term.Type.TERM_TYPE_NUMBER)
					/* throw new NonArithmeticArgException() */;

				value = term.getValue();
				break;
			case EXPR_TYPE_INTEGER:
			case EXPR_TYPE_DOUBLE:
				break;
			case EXPR_TYPE_MINUS:
				value = left.calculateValue(vs) - right.calculateValue(vs);
				break;
			case EXPR_TYPE_PLUS:
				value = left.calculateValue(vs) + right.calculateValue(vs);
				break;
			case EXPR_TYPE_MULTI:
				value = left.calculateValue(vs) * right.calculateValue(vs);
				break;
			case EXPR_TYPE_DIVIDE:
				value = left.calculateValue(vs) / right.calculateValue(vs);
				break;
		}
		return value;
	}
}
