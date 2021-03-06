/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: JobStmt.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: JobStmt
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.runtime.traverse.*;

public class JobStmt extends Traversable {
	public enum Type {
		IF,
		WHILE,
		DOWHILE,
		COMPOUND,
		RETURN,
		EXPR,
	};
	Type type;

	JobStmt stmt1;
	JobStmt stmt2;
	JobExpr expr;
	List<JobStmt> stmts;

	public JobStmt(Type type, JobExpr expr, JobStmt stmt) {
		this(type, expr, stmt, null);
	}

	public JobStmt(Type type, JobExpr expr, JobStmt stmt1, JobStmt stmt2) {
		assert (type == Type.IF || type == Type.WHILE ||
			type == Type.DOWHILE);

		/*
		 * Can still assert the following to ensure only IFs have stmt2
		 * assert (type == Type.IF || stmt2 == null);
		 */
		this.type = type;
		this.expr = expr;
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;

		add(expr);
		add(stmt1);
		if (stmt2 != null)
			add(stmt2);
	}

	public JobStmt(Type type, JobExpr expr) {
		assert (type == Type.EXPR || type == Type.RETURN);

		this.type = type;
		this.expr = expr;

		add(expr);
	}

	public JobStmt(Type type, List<JobStmt> stmts) {
		assert (type == Type.COMPOUND);

		this.type = type;
		this.stmts = stmts;

		addAll(stmts);
	}

	public Type getType() {
		return type;
	}

	public JobStmt getStmt1() {
		return stmt1;
	}

	public JobStmt getStmt2() {
		return stmt2;
	}

	public List<JobStmt> getStmts() {
		return stmts;
	}

	public JobExpr getExpr() {
		return expr;
	}
}
