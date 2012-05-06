/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: JobExprTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Job Expressions
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class JobExprTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(JobExprTest.class);
	}
	@Override
	protected void setUp() {
	}

	public void testSimpleJobExpr() {
		Term term = new Term(Term.Type.VARIABLE, "X");
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, term);
		assertTrue(e1.getType() == JobExpr.Type.TERM);
		assertTrue(e1.getTerm() == term);
		assertTrue(e1.getTerm().getName().equals("X"));
	}
	
	public void testRelationalJobExpr() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LE, e1, e2);
		assertTrue(e3.getType() == JobExpr.Type.LE);
		assertTrue(e3.getExpr1() == e1);
		assertTrue(e3.getExpr2() == e2);
	}

	public void testOperationalExpr() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.ADD, e1, e2);

		assertTrue(e3.getType() == JobExpr.Type.ADD);
		assertTrue(e3.getExpr1() == e1);
		assertTrue(e3.getExpr2() == e2);
	}

	public void testAssignmentJobExpr() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.ADD, e1, e2);
		
		JobExpr e4 = new JobExpr(JobExpr.Type.ASSIGN, "X", e3);
		assertTrue(e4.getType() == JobExpr.Type.ASSIGN);
		assertTrue(e4.getExpr1() == e3);
		assertTrue(e4.getName().equals("X"));
	}
	
	public void testJobCallExpr() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.DIV, e1, e2);

		ArrayList<JobExpr> args = new ArrayList<JobExpr>();
		args.add(e3);

		JobExpr e4 = new JobExpr(JobExpr.Type.JOBCALL, "pagerank", args);
		assertTrue(e4.getType() == JobExpr.Type.JOBCALL);
		assertTrue(e4.getName().equals("pagerank"));
		assertTrue(e4.getExprs() == args);	
	}
}
