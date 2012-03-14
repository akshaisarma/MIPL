/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: RuntimeTraverser.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: RuntimeTraverser
 */
package edu.columbia.mipl.runtime.traverse;

import java.util.*;

import edu.columbia.mipl.runtime.*;

public abstract class RuntimeTraverser implements Traverser {
	public void reach(Traversable t) {
		if (t instanceof Term) {
			reachTerm((Term) t);
		}
		else if (t instanceof Expression) {
			reachExpression((Expression) t);
		}
		else if (t instanceof Fact) {
			reachFact((Fact) t);
		}
		else if (t instanceof Rule) {
			reachRule((Rule) t);
		}
		else if (t instanceof Query) {
			reachQuery((Query) t);
		}
		else if (t instanceof Job) {
			reachJob((Job) t);
		}
		else if (t instanceof JobStmt) {
			reachJobStmt((JobStmt) t);
		}
		else if (t instanceof JobExpr) {
			reachJobExpr((JobExpr) t);
		}
	}

	public abstract void reachTerm(Term term);
	public abstract void reachExpression(Expression expr);
	public abstract void reachFact(Fact fact);
	public abstract void reachRule(Rule rule);
	public abstract void reachQuery(Query query);
	public abstract void reachJob(Job job);
	public abstract void reachJobStmt(JobStmt jstmt);
	public abstract void reachJobExpr(JobExpr jexpr);
}
