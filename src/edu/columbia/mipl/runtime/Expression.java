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

import edu.columbia.mipl.runtime.traverse.*;

public class Expression extends Traversable {
	public enum Type {
		TERM,
		MINUS,
		PLUS,
		MULTI,
		DIVIDE,
	};
	Type type;

	Term term;
//	double value;

	Expression left;
	Expression right;

	public Expression(Type type, Term term) {
		assert (type == Type.TERM &&
			(term.getType() == Term.Type.VARIABLE || term.getType() == Term.Type.NUMBER));

		this.type = type;
		this.term = term;

		add(term);
	}

	/*
	public Expression(Type type, int value) {
		this(type, (double) value);
	}

	public Expression(Type type, double value) {
		assert (type == Type.TERM && term.getType() == Term.Type.NUMBER);

		term.setValue(value);
	}
	*/

	public Expression(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.MINUS || type == Type.PLUS ||
			type == Type.MULTI || type == Type.DIVIDE);

		this.type = type;

		left = expr1;
		right = expr2;

		add(expr1);
		add(expr2);
	}

	public Term getTerm() {
		if (term != null)
			return term;

		return new Term(Term.Type.EXPRESSION, this);
	}

	public Type getType() {
		return type;
	}

	public Expression getExpr1() {
		return left;
	}

	public Expression getExpr2() {
		return right;
	}

	/*
	double calculateValue(VariableStack vs) /* throws InsuffArgInitException, NonArithmeticArgException / {
		switch (type) {
			case TERM:
				Term term  = vs.get(this.term);
				if (term == null)
					/* throw new InsuffArgInitException() /;
				if (term.getType() != Term.Type.NUMBER)
					/* throw new NonArithmeticArgException() /;

				value = term.getValue();
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
	*/
}
