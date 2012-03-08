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

	String variableName;
	double value;

	Expression left;
	Expression right;

	double calculateValue(VariableStack vs) /* throws InsuffArgInitException, NonArithmeticArgException */ {
		switch (type) {
			case EXPR_TYPE_VARIABLE:
				Term term  = vs.getValue(variableName);
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
