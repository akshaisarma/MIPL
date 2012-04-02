/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Job.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Job
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class Job extends Knowledge {
	String name;
	List<Term> args;
	List<JobStmt> stmts;

	public Job(String name, List<Term> args, List<JobStmt> stmts) {
		this.name = name;
		this.args = args;
		this.stmts = stmts;

		addAll(args);
		addAll(stmts);
	}

	public String getName() {
		return name;
	}

	public List<Term> getArgs() {
		return args;
	}

	public List<JobStmt> getStmts() {
		return stmts;
	}

	public Term getTerm() {
		return null;
	}
}
