/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: SemanticChecker.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: SemanticChecker
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

public class SemanticChecker extends RuntimeTraverser {
	public Method getMethod() {
		return Method.POST;
	}

	public SemanticChecker() {
	}

	public boolean reachTerm(Term term) {
		return true;
	}

	public boolean reachExpression(Expression expr) {
		return true;
	}

	public boolean reachFact(Fact fact) {
		return true;
	}

	public boolean reachRule(Rule rule) {
		return true;
	}

	public boolean reachQuery(Query query) {
		return true;
	}

	public boolean reachJob(Job job) {
		return true;
	}

	public boolean reachJobStmt(JobStmt jstmt) {
		return true;
	}

	public boolean reachJobExpr(JobExpr jexpr) {
		return true;
	}

	public void finish() {
	}
}
