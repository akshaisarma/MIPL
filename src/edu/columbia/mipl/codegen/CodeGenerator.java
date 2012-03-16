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

public class CodeGenerator extends RuntimeTraverser {
	InstructionWriter writer;

	public CodeGenerator() {
		String target = "JVM"; /* read this from Configuration */
		writer = InstructionWriterFactory.getInstructionWriter(target);
	}

	public void reachTerm(Term term) {
		switch (term.getType()) {
			case IS:
				writer.createTerm(Term.Type.IS, term.getTerm1(),
									term.getExpr1());
				break;
			case EQ:
			case LT:
			case LE:
			case GT:
			case GE:
			case NE:
				writer.createTerm(term.getType(), term.getExpr1(),
									term.getExpr2());
				break;
			case MATRIX:
				writer.createTerm(Term.Type.MATRIX, term.getName(),
									term.getMatrix());
				break;
			case ANDTERMS:
			case ORTERMS:
				writer.createTerm(term.getType(), term.getTerm1(),
									term.getTerm2());
				break;
			case NOTTERM:
				writer.createTerm(term.getType(), term.getTerm1());
				break;
			case REGEXTERM:
			case TERM:
				writer.createTerm(Term.Type.TERM, term.getName(),
									term.getArguments());
				break;
			case NUMBER:
				writer.createTerm(Term.Type.NUMBER, term.getValue());
				break;
			case VARIABLE:
			case QUERYALL:
			case REGEXQUERYALL:
			case STRING:
				writer.createTerm(term.getType(), term.getName());
			case EXPRESSION:
				writer.createTerm(Term.Type.EXPRESSION, term.getExpr1());
				break;
		}
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

	public void finish() {
		writer.finish();
	}
}
