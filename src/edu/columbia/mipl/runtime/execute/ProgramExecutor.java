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

	KnowledgeTable kt = KnowledgeTableFactory.getKnowledgeTable();

	public Method getMethod() {
		return Method.POST;
	}

	public ProgramExecutor() {
	}

	public boolean reachTerm(Term term) {
		return true;
	}

	public boolean reachExpression(Expression expr) {
		return true;
	}

	public boolean reachFact(Fact fact) {
		switch(fact.getType()) {
			case FACT:
				kt.put(fact.getName(), fact);
				break;
			case MATRIXASFACTS:
//				List<PrimitiveType> results = 
//				List<String> names = fact.getNames();
//				for (PrimitiveType pt : result) {
//					Fact f = new Fact(names.get(i), new Term(Term.Type.MATRIX, names.get(i), results.get(i));
//					kt.put(f.getName(), f);
//				}
				break;
		}
		return true;
	}

	public boolean reachRule(Rule rule) {
		kt.put(rule.getName(), rule);
		return true;
	}

	public boolean reachQuery(Query query) {
		SolvableBinder sb = new SolvableBinder(query.getTerm());
		if (!sb.bind()) {
			System.out.println("Fail: No solution can be reached, after binding.");
		}
		return true;
	}

	public boolean reachJob(Job job) {
		kt.put(job.getName(), job);
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
