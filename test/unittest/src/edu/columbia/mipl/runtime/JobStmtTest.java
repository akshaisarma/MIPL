	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: JobStmtTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Job Statements 
 */

package edu.columbia.mipl.runtime;

import java.util.*;
import edu.columbia.mipl.runtime.traverse.*;
import junit.framework.TestCase;

public class JobStmtTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(JobStmtTest.class);
	}

	@Override
	protected void setUp() {
	}
	
	public void testReturnStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.ADD, e1, e2);
		JobStmt returnStmt = new JobStmt(JobStmt.Type.RETURN, e3);
		assertTrue(returnStmt.getExpr() == e3);
		assertTrue(returnStmt.getType() == JobStmt.Type.RETURN);
	}

	public void testIfStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LT, e1, e2);
		JobStmt stmt = new JobStmt(JobStmt.Type.RETURN, e3);

		JobStmt ifStatement = new JobStmt(JobStmt.Type.IF, e3, stmt);
		
		assertTrue(ifStatement.getType() == JobStmt.Type.IF);
		assertTrue(ifStatement.getExpr() == e3);
		assertTrue(ifStatement.getStmt1() == stmt);
		assertTrue(ifStatement.getStmt2() == null);		
	}

	public void testIfElseStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LT, e1, e2);
		JobStmt stmt1 = new JobStmt(JobStmt.Type.RETURN, e3);
		JobStmt stmt2 = new JobStmt(JobStmt.Type.RETURN, e1);

		JobStmt ifStatement = new JobStmt(JobStmt.Type.IF, e3, stmt1, stmt2);
		
		assertTrue(ifStatement.getType() == JobStmt.Type.IF);
		assertTrue(ifStatement.getExpr() == e3);
		assertTrue(ifStatement.getStmt1() == stmt1);
		assertTrue(ifStatement.getStmt2() == stmt2);		
	}

	public void testWhileStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LT, e1, e2);
		JobStmt stmt1 = new JobStmt(JobStmt.Type.EXPR, e1);

		JobStmt whileStatement = new JobStmt(JobStmt.Type.WHILE, e3, stmt1);
		
		assertTrue(whileStatement.getType() == JobStmt.Type.WHILE);
		assertTrue(whileStatement.getExpr() == e3);
		assertTrue(whileStatement.getStmt1() == stmt1);
		assertTrue(whileStatement.getStmt2() == null);		
	}

	public void testDoWhileStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LT, e1, e2);
		JobStmt stmt1 = new JobStmt(JobStmt.Type.EXPR, e1);

		JobStmt dowhileStatement = new JobStmt(JobStmt.Type.DOWHILE, e3, stmt1);
		
		assertTrue(dowhileStatement.getType() == JobStmt.Type.DOWHILE);
		assertTrue(dowhileStatement.getExpr() == e3);
		assertTrue(dowhileStatement.getStmt1() == stmt1);
		assertTrue(dowhileStatement.getStmt2() == null);		
	}

	public void testCompoundStatement() {
		JobExpr e1 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		JobExpr e2 = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, "Y"));
		JobExpr e3 = new JobExpr(JobExpr.Type.LT, e1, e2);

		ArrayList<JobStmt> listOfStmts = new ArrayList<JobStmt>();

		JobStmt stmt1 = new JobStmt(JobStmt.Type.EXPR, e1);
		JobStmt stmt2 = new JobStmt(JobStmt.Type.RETURN, e1);
		JobStmt dowhileStatement = new JobStmt(JobStmt.Type.DOWHILE, e3, stmt1);
		JobStmt ifStatement = new JobStmt(JobStmt.Type.IF, e3, stmt1, stmt2);
		
		listOfStmts.add(stmt1);
		listOfStmts.add(stmt2);
		listOfStmts.add(dowhileStatement);
		listOfStmts.add(ifStatement);

		JobStmt compoundStatement = new JobStmt(JobStmt.Type.COMPOUND, listOfStmts);
		assertTrue(compoundStatement.getType() == JobStmt.Type.COMPOUND);
		List<JobStmt> stmts = compoundStatement.getStmts();
		for (JobStmt js : stmts) {
			assertTrue(listOfStmts.contains(js));
			assertTrue( ((ArrayList<Traversable>) compoundStatement).contains(js) );
		}
	}
}
