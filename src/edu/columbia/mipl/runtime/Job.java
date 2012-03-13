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

public class Job {
	List<Term> args;
	List<JobStmt> stmts;

	public Job(List<Term> args, List<JobStmt> stmts) {
		this.args = args;
		this.stmts = stmts;
	}
}
