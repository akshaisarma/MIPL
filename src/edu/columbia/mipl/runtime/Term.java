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

import edu.columbia.mipl.runtime.traverse.*;

public class Term extends Traversable {
	public enum Type {
		IS,
		EQ,
		LT,
		LE,
		GT,
		GE,
		NE,
		MATRIX,
		REGEXTERM,
		TERM,
		ANDTERMS,
		ORTERMS,
		NOTTERM,
		NUMBER,
		VARIABLE,
		QUERYALL,
		REGEXQUERYALL,
		STRING,
		EXPRESSION,
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

	public Term(Type type, Expression expr1, Expression expr2) {
		assert (type == Type.EQ || type == Type.LT ||
			type == Type.LE || type == Type.GT ||
			type == Type.GE || type == Type.NE);

		this.type = type;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	/* X is Y - 1 */
	public Term(Type type, Term variable, Expression expr) {
		assert (type == Type.IS);

		this.type = type;
		term1 = variable;
		expr1 = expr;
	}

	public Term(Type type, double value) {
		assert (type == Type.NUMBER);

		this.type = type;
		this.value = value;
	}

	public Term(Type type, int value) {
		this(type, (double) value);
	}

	public Term(Type type, String name, PrimitiveArray data) {
		this(type, name, new PrimitiveMatrix<Double>(data));
	}

	public Term(Type type, String name, PrimitiveMatrix<Double> matrix) {
		assert (type == Type.MATRIX);

		this.type = type;
		this.name = name;
		this.matrix = matrix;
	}

	public Term(Type type, Term term1, Term term2) {
		assert (type == Type.ANDTERMS || type == Type.ORTERMS);

		this.type = type;
		this.term1 = term1;
		this.term2 = term2;

		hasVariables = (term1.containVariables() || term2.containVariables());
	}

	public Term(Type type, Term term) {
		assert (type == Type.NOTTERM);

		this.type = type;
		this.term1 = term;
	}

	public Term(Type type, String name, List<Term> arguments) {
		assert (type == Type.TERM || type == Type.REGEXTERM);

		this.type = type;
		this.name = name;
		if (arguments == null)
			arguments = new ArrayList<Term>();

		this.arguments = arguments;

		for (Term t : arguments) {
			if (t.containVariables()) {
				hasVariables = true;
				break;
			}
		}
	}

	public Term(Type type, String name) {
		assert (type == Type.QUERYALL || type == Type.REGEXQUERYALL ||
			type == Type.VARIABLE || type == Type.STRING);

		this.type = type;
		this.name = name;

		if (type == Type.VARIABLE)
			hasVariables = true;
	}

	public Term(Type type, Expression expr) {
		assert (type == Type.EXPRESSION);

		this.type = type;
		this.expr1 = expr;
	}

	public Type getType() {
		return type;
	}

	public void setValue(double value) {
		assert (type == Type.NUMBER);

		this.value = value;
	}

	public double getValue() {
		assert (type == Type.NUMBER);

		return value;
	}

	public boolean containVariables() {
		return hasVariables;
	}

	public String getName() {
		return name;
	}

	public List<Term> getArguments() {
		return arguments;
	}

	public PrimitiveMatrix<Double> getMatrix() {
		return matrix;
	}

	public Term getTerm1() {
		return term1;
	}

	public Term getTerm2() {
		return term2;
	}

	public Expression getExpr1() {
		return expr1;
	}

	public Expression getExpr2() {
		return expr2;
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

		assert (type == Type.VARIABLE || type == Type.NUMBER ||
			type == Type.TERM || type == Type.MATRIX);
		assert (term.getType() == Type.VARIABLE || term.getType() == Type.NUMBER ||
			term.getType() == Type.TERM || term.getType() == Type.MATRIX);
		assert (type != Type.MATRIX || term.getType() != Type.MATRIX);

		if (type == Type.VARIABLE) {
			return checkStoreVS(vs, this, term);
		}
		if (term.getType() == Type.VARIABLE) {
			return term.match(this, vs);
		}

		switch(type) {
			case TERM:
				if (term.getType() == Type.MATRIX)
					return term.match(this, vs);
				if (term.getType() != Type.TERM)
					return false;
				if (!name.equals(term.getName()))
					return false;
				if (arguments.size() != term.getArguments().size())
					return false;
				for (i = 0; i < arguments.size(); i++)
					if (!arguments.get(i).match(term.getArguments().get(i), vs))
						return false;
				return true;

			case NUMBER:
				if (term.getType() != Type.NUMBER)
					return false;
				return value == term.getValue();

			case MATRIX:
				if (term.getType() != Type.TERM)
					return false;
				if (!name.equals(term.getName()))
					return false;
				if (matrix.getCol() != term.getArguments().size())
					return false;
				for (j = 0; j < arguments.size(); j++) {
					if (arguments.get(j).getType() != Type.NUMBER &&
							arguments.get(j).getType() != Type.VARIABLE)
						return false;
				}
				for (i = 0; i < matrix.getRow(); i++) {
					for (j = 0; j < arguments.size(); j++) {
						if (arguments.get(j).getType() == Type.VARIABLE) {
							if (!checkStoreVS(vs, arguments.get(j), new Term(Type.NUMBER, matrix.getValue(i, j))))
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
			case IS:
//				vs.setValue(term1, new Term(Type.NUMBER), expr.calculateValue(vs));
				break;
			case TERM:
				break;
			case ANDTERMS:
//				term1.deduce();
//				term2.deduce();
				break;
		}
		return true;
	}

}
