/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: SemanticChecker.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Implements one rule (others are in Program Executor)
 *				- job usage before definition
 */

package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.builtin.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

public class SemanticChecker extends RuntimeTraverser {
	private HashMap<String, Knowledge> definedJobs;

	public Method getMethod() {
		return Method.POST;
	}

	public SemanticChecker() {
		this.definedJobs = new HashMap<String, Knowledge>();
	}

	public boolean reachTerm(Term term) {
		return true;
	}

	public boolean reachExpression(Expression expr) {
		return true;
	}

	public boolean reachFact(Fact fact) {
		if (fact.getType() != Fact.Type.MATRIXASFACTS)
			return true;

		if (BuiltinTable.existJob(fact.getName()))
			return true;

		Job definedJob = (Job) definedJobs.get(fact.getName());

		if (definedJob == null) {
			new Exception(fact.getName() + " has not been defined!").printStackTrace();
			return false;
		}
		return true;
	}

	public boolean reachRule(Rule rule) {
		return true;
	}

	public boolean reachQuery(Query query) {
		return true;
	}

	public boolean reachJob(Job job) {
		String name = job.getName();
		boolean builtinExists = BuiltinTable.existJob(name);

		Job definedJob = (Job) definedJobs.get(name);
		if (definedJob == null && !builtinExists) {
			definedJobs.put(job.getName(), job);
			return true;
		}

		if (builtinExists) {
			new Exception(job.getName() + " is a built in job! Cannot redefine it").printStackTrace();
			return false;
		}

		new Exception(job.getName() + " has already been defined!").printStackTrace();
		return false;
	}

	public boolean reachJobStmt(JobStmt jstmt) {
		return true;
	}

	public boolean reachJobExpr(JobExpr jexpr) {
		if (jexpr.getType() != JobExpr.Type.JOBCALL)
			return true;

		if (BuiltinTable.existJob(jexpr.getName()))
			return true;

		Job definedJob = (Job) definedJobs.get(jexpr.getName());
		
		if (definedJob == null || definedJob.getArgs().size() != jexpr.getExprs().size()) {
			new Exception(jexpr.getName() + " has not been defined!").printStackTrace();
			return false;
		}
		return true;
	}

	public void finish() {
	}
}
