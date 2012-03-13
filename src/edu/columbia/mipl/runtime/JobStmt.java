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

public class JobStmt {
	public enum Type {
		IF,
		WHILE,
		DOWHILE,
		COMPOUND,
		RETURN,
		EXPR,
		ITERATION,
		NULL,
	};
	Type type;

	JobStmt stmt1;
	JobStmt stmt2;
	JobExpr expr;
	List<JobStmt> stmts;

	public JobStmt(Type type) {
		assert (type == Type.NULL);
	}

	public JobStmt(Type type, JobExpr expr, JobStmt stmt) {
		this(type, expr, stmt, null);
	}

	public JobStmt(Type type, JobExpr expr, JobStmt stmt1, JobStmt stmt2) {
		assert (type == Type.IF || type == Type.WHILE ||
			type == Type.DOWHILE);

		assert (type != Type.IF || stmt2 != null);

		this.type = type;
		this.expr = expr;
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;
	}

	public JobStmt(Type type, JobExpr expr) {
		assert (type == Type.EXPR || type == Type.RETURN);

		this.type = type;
		this.expr = expr;
	}

	public JobStmt(Type type, List<JobStmt> stmts) {
		assert (type == Type.COMPOUND);

		this.type = type;
		this.stmts = stmts;
	}
}
