	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: QueryTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Query 
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(QueryTest.class);
	}

	@Override
	protected void setUp() {
	}
	
	public void testQuery() {
		Term term = new Term(Term.Type.TERM, "Variable", (List<Term>) null);
		Query query = new Query(term);
		assertTrue(query.getName().equals("Variable"));
	}
}
