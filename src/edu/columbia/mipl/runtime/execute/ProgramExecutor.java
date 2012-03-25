/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: ProgramExecutor.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: ProgramExecutor
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

public class ProgramExecutor extends RuntimeTraverser {

	public Method getMethod() {
		return Method.POST;
	}

	public ProgramExecutor() {
	}

	public void reachTerm(Term term) {
	}

	public void reachExpression(Expression expr) {
	}

	public void reachFact(Fact fact) {
		// registerKnowledge
		// or
		// execute FactsAsMatrix and resgisterKnowledges
	}

	public void reachRule(Rule rule) {
		// registerKnowledge
	}

	public void reachQuery(Query query) {
	}

	public void reachJob(Job job) {
	}

	public void reachJobStmt(JobStmt jstmt) {
	}

	public void reachJobExpr(JobExpr jexpr) {
	}

	public void finish() {
	}
}
