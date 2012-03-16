/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: JobExpr.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: JobExpr
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.runtime.traverse.*;

public class JobExpr extends Traversable {
	public enum Type {
		COMPOUND,
		ASSIGN,
		MULASSIGN,
		DIVASSIGN,
		MODASSIGN,
		ADDASSIGN,
		SUBASSIGN,
		OR,
		AND,
		EQ,
		NE,
		LT,
		GT,
		LE,
		GE,
		ADD,
		SUB,
		MULT,
		DIV,
		MOD,
		NEGATE,
		ARRAY,
		JOBCALL,
		TERM,
	};
	Type type;

	List<JobExpr> exprs;

	JobExpr expr1;
	JobExpr expr2;

	List<ArrayIndex> indices1;
	List<ArrayIndex> indices2;

	String name;

	Term term;

	public JobExpr(Type type, Term term) {
		assert (type == Type.TERM);

		this.type = type;
		this.term = term;

		add(term);
	}

	public JobExpr(Type type, String name, List<JobExpr> exprs) {
		assert (type == Type.JOBCALL);

		this.type = type;
		this.name = name;
		this.exprs = exprs;

		addAll(exprs);
	}

	public JobExpr(Type type, Term term, List<ArrayIndex> indices1, List<ArrayIndex> indices2) {
		assert (type == Type.ARRAY);

		this.type = type;
		this.term = term;
		this.indices1 = indices1;
		this.indices2 = indices2;

		add(term);
	}

	public JobExpr(Type type, JobExpr expr) {
		assert (type == Type.NEGATE);

		this.type = type;
		this.expr1 = expr;

		add(expr);
	}

	public JobExpr(Type type, JobExpr target, JobExpr source) {
		assert (type == Type.ASSIGN || type == Type.MULASSIGN ||
				type == Type.DIVASSIGN || type == Type.MODASSIGN ||
				type == Type.ADDASSIGN || type == Type.SUBASSIGN ||
				type == Type.COMPOUND || type == Type.OR ||
				type == Type.AND || type == Type.EQ || type == Type.NE ||
				type == Type.LT || type == Type.GT || type == Type.LE ||
				type == Type.GE || type == Type.ADD || type == Type.SUB ||
				type == Type.MULT || type == Type.DIV || type == Type.MOD);
		this.type = type;
		this.expr1 = target;
		this.expr2 = source;

		add(target);
		add(source);
	}

	public Type getType() {
		return type;
	}

	public List<JobExpr> getExprs() {
		return exprs;
	}

	public JobExpr getExpr1() {
		return expr1;
	}

	public JobExpr getExpr2() {
		return expr2;
	}

	public List<ArrayIndex> getIndices1() {
		return indices1;
	}

	public List<ArrayIndex> getIndices2() {
		return indices2;
	}

	public String getName() {
		return name;
	}

	public Term getTerm() {
		return term;
	}
}
