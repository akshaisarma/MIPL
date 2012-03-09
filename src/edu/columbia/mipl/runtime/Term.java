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
	boolean hasVariables = false;

	double value;
	List<Term> arguments;
	String name;
	PrimitiveMatrix<Double> matrix;
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
	Term(Type type, Term variable, Expression expr) {
		assert (type == Type.TERM_TYPE_IS);

		this.type = type;
		term1 = variable;
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

		hasVariables = (term1.containVariables() || term2.containVariables());
	}

	Term(Type type, String name, List<Term> arguments) {
		assert (type == Type.TERM_TYPE_TERM || type == Type.TERM_TYPE_NOTTERM);

		this.type = type;
		this.name = name;
		this.arguments = arguments;

		for (Term t : arguments) {
			if (t.containVariables()) {
				hasVariables = true;
				break;
			}
		}
	}

	Term(Type type, String name) {
		assert (type == Type.TERM_TYPE_VARIABLE);

		this.type = type;
		this.name = name;

		hasVariables = true;
	}

	Type getType() {
		return type;
	}

	double getValue() {
		assert (type == Type.TERM_TYPE_NUMBER);

		return value;
	}

	boolean containVariables() {
		return hasVariables;
	}

	String getName() {
		return name;
	}

	List<Term> getArguments() {
		return arguments;
	}

	PrimitiveMatrix<Double> getMatrix() {
		return matrix;
	}

	static boolean checkStoreVS(VariableStack vs, Term term1, Term term2) {
		Term prev = vs.get(term1);
		if (prev != null && !prev.match(term2, vs))
			return false;
		vs.put(term1, term2);
		return true;
	}

	boolean match(Term term, VariableStack vs) {
		int i;
		int j;

		assert (type == Type.TERM_TYPE_VARIABLE || type == Type.TERM_TYPE_NUMBER ||
			type == Type.TERM_TYPE_TERM || type == Type.TERM_TYPE_MATRIX);
		assert (term.getType() == Type.TERM_TYPE_VARIABLE || term.getType() == Type.TERM_TYPE_NUMBER ||
			term.getType() == Type.TERM_TYPE_TERM || term.getType() == Type.TERM_TYPE_MATRIX);
		assert (type != Type.TERM_TYPE_MATRIX || term.getType() != Type.TERM_TYPE_MATRIX);

		if (type == Type.TERM_TYPE_VARIABLE) {
			return checkStoreVS(vs, this, term);
		}
		if (term.getType() == Type.TERM_TYPE_VARIABLE) {
			return term.match(this, vs);
		}

		switch(type) {
			case TERM_TYPE_TERM:
				if (term.getType() == Type.TERM_TYPE_MATRIX)
					return term.match(this, vs);
				if (term.getType() != Type.TERM_TYPE_TERM)
					return false;
				if (!name.equals(term.getName()))
					return false;
				if (arguments.size() != term.getArguments().size())
					return false;
				for (i = 0; i < arguments.size(); i++)
					if (!arguments.get(i).match(term.getArguments().get(i), vs))
						return false;
				return true;

			case TERM_TYPE_NUMBER:
				if (term.getType() != Type.TERM_TYPE_NUMBER)
					return false;
				return value == term.getValue();

			case TERM_TYPE_MATRIX:
				if (term.getType() != Type.TERM_TYPE_TERM)
					return false;
				if (!name.equals(term.getName()))
					return false;
				if (matrix.getCol() != term.getArguments().size())
					return false;
				for (j = 0; j < arguments.size(); j++) {
					if (arguments.get(j).getType() != Type.TERM_TYPE_NUMBER &&
							arguments.get(j).getType() != Type.TERM_TYPE_VARIABLE)
						return false;
				}
				for (i = 0; i < matrix.getRow(); i++) {
					for (j = 0; j < arguments.size(); j++) {
						if (arguments.get(j).getType() == Type.TERM_TYPE_VARIABLE) {
							if (!checkStoreVS(vs, arguments.get(j), new Term(Type.TERM_TYPE_NUMBER, matrix.getValue(i, j))))
								break;
							continue;
						}
						if (matrix.getValue(i, j) != arguments.get(i).getValue())
							break;
					}
					if (j == arguments.size())
						return true;
				}
				return false;
		}
		return false;
	}

	boolean deduce(List<Term> targetVariables, Solvable solver, VariableStack vs) {
		switch (type) {
			case TERM_TYPE_IS:
//				vs.setValue(term1, new Term(Type.TERM_TYPE_NUMBER), expr.calculateValue(vs));
				break;
			case TERM_TYPE_TERM:
				break;
			case TERM_TYPE_ANDTERMS:
//				term1.deduce();
//				term2.deduce();
				break;
		}
		return true;
	}

}
