	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: JobTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Job 
 */

package edu.columbia.mipl.runtime;

import java.util.*;
import edu.columbia.mipl.runtime.traverse.*;
import junit.framework.TestCase;

public class JobTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(JobTest.class);
	}

	@Override
	protected void setUp() {
	}

	public void testJob() {
		Term t1 = new Term(Term.Type.VARIABLE, "X");
		Term t2 = new Term(Term.Type.VARIABLE, "X");

		ArrayList<Term> listOfArgs = new ArrayList<Term>();
		listOfArgs.add(t1);
		listOfArgs.add(t2);

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
		
		Job job = new Job("sample", listOfArgs, listOfStmts);
		
		assertTrue(job.getName().equals("sample"));
		assertTrue(job.getArgs() == listOfArgs);
		assertTrue(job.getStmts() == listOfStmts);

		for (JobStmt js : listOfStmts)
			assertTrue( ((ArrayList<Traversable>) job).contains(js));
	}
}
