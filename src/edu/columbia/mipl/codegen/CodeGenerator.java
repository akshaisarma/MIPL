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
				writer.createTerm(Term.Type.NOTTERM, term.getTerm1());
				break;
			case REGEXTERM:
			case TERM:
				writer.createTerm(term.getType(), term.getName(),
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
		switch (expr.getType()) {
			case TERM:
				writer.createExpression(Expression.Type.TERM, expr.getTerm());
				break;
			case MINUS:
			case PLUS:
			case MULTI:
			case DIVIDE:
				writer.createExpression(expr.getType(), expr.getExpr1(),
											expr.getExpr2());
				break;
		}
	}

	public void reachFact(Fact fact) {
		switch (fact.getType()) {
			case FACT:
				writer.createFact(Fact.Type.FACT, fact.getTerm());
				break;
			case MATRIXASFACTS:
				writer.createFact(Fact.Type.MATRIXASFACTS, fact.getName(),
									fact.getNames(), fact.getTerms());
				break;
		}
	}

	public void reachRule(Rule rule) {
		writer.createRule(rule.getTerm(), rule.getSource());
	}

	public void reachQuery(Query query) {
		writer.createQuery(query.getTerm());
	}

	public void reachJob(Job job) {
		writer.createJob(job.getName(), job.getArgs(), job.getStmts());
	}

	public void reachJobStmt(JobStmt jstmt) {
		switch (jstmt.getType()) {
			case IF:
			case WHILE:
			case DOWHILE:
				writer.createJobStmt(jstmt.getType(), jstmt.getExpr(),
										jstmt.getStmt1(), jstmt.getStmt2());
				break;
			case COMPOUND:
				writer.createJobStmt(JobStmt.Type.COMPOUND, jstmt.getStmts());
				break;
			case EXPR:
			case RETURN:
				writer.createJobStmt(jstmt.getType(), jstmt.getExpr());
				break;
			case NULL:
				writer.createJobStmt(JobStmt.Type.NULL);
				break;
		}
	}

	public void reachJobExpr(JobExpr jexpr) {
		switch (jexpr.getType()) {
			case COMPOUND:
			case ASSIGN:
			case MULASSIGN:
			case DIVASSIGN:
			case MODASSIGN:
			case ADDASSIGN:
			case SUBASSIGN:
			case OR:
			case AND:
			case EQ:
			case NE:
			case LT:
			case GT:
			case LE:
			case GE:
			case ADD:
			case SUB:
			case MULT:
			case DIV:
			case MOD:
				writer.createJobExpr(jexpr.getType(), jexpr.getExpr1(),
										jexpr.getExpr2());
				break;
			case NEGATE:
				writer.createJobExpr(JobExpr.Type.NEGATE, jexpr.getExpr1());
				break;
			case ARRAY:
				writer.createJobExpr(JobExpr.Type.ARRAY, jexpr.getTerm(),
										jexpr.getIndices1(),
										jexpr.getIndices2());
				break;
			case JOBCALL:
				writer.createJobExpr(JobExpr.Type.JOBCALL, jexpr.getName(),
										jexpr.getExprs());
				break;
			case TERM:
				writer.createJobExpr(JobExpr.Type.TERM, jexpr.getTerm());
				break;
		}
	}

	public void finish() {
		writer.finish();
	}
}
