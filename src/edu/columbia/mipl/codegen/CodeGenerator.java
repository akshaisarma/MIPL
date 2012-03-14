/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: CodeGenerator.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: CodeGenerator
 */
package edu.columbia.mipl.codegen;

import java.util.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;

public class CodeGenerator implements Traverser {
	public CodeGenerator() {
	}

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

	public void reachTerm(Term term) {
	}

	public void reachExpression(Expression expr) {
	}

	public void reachFact(Fact fact) {
	}

	public void reachRule(Rule rule) {
	}

	public void reachQuery(Query query) {
	}

	public void reachJob(Job job) {
	}

	public void reachJobStmt(JobStmt jstmt) {
	}

	public void reachJobExpr(JobExpr jexpr) {
	}
}
