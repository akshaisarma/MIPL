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
		VARIABLE,
		INTEGER,
		DOUBLE,
		MINUS,
		PLUS,
		MULTI,
		DIVIDE,
	};
	Type type;

	Term variable;
	double value;

	Expression left;
	Expression right;

	public Expression(Type type, Term term) {
		assert (type == Type.VARIABLE);
		assert (term.getType() == Term.Type.VARIABLE);

		variable = term;
	}

	public Expression(Type type, int value) {
		this(type, (double) value);
	}

	public Expression(Type type, double value) {
		assert (type == Type.INTEGER || type == Type.DOUBLE);
		
		this.value = value;
	}

	public Expression(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.MINUS || type == Type.PLUS ||
			type == Type.MULTI || type == Type.DIVIDE);

		left = expr1;
		right = expr2;
	}

	double calculateValue(VariableStack vs) /* throws InsuffArgInitException, NonArithmeticArgException */ {
		switch (type) {
			case VARIABLE:
				Term term  = vs.get(variable);
				if (term == null)
					/* throw new InsuffArgInitException() */;
				if (term.getType() != Term.Type.NUMBER)
					/* throw new NonArithmeticArgException() */;

				value = term.getValue();
				break;
			case INTEGER:
			case DOUBLE:
				break;
			case MINUS:
				value = left.calculateValue(vs) - right.calculateValue(vs);
				break;
			case PLUS:
				value = left.calculateValue(vs) + right.calculateValue(vs);
				break;
			case MULTI:
				value = left.calculateValue(vs) * right.calculateValue(vs);
				break;
			case DIVIDE:
				value = left.calculateValue(vs) / right.calculateValue(vs);
				break;
		}
		return value;
	}
}
