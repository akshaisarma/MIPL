	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: FactTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Fact 
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class FactTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(FactTest.class);
	}

	@Override
	protected void setUp() {
	}
	
	public void testSimpleFact() {
		Term term = new Term(Term.Type.TERM, "Variable", (List<Term>) null);
		Fact simpleFact = new Fact(term);
		assertTrue(simpleFact.getName().equals("Variable"));
		assertEquals(simpleFact.getType(), Fact.Type.FACT);
	}

	public void testDynamicFact() {
		Term jobCall = new Term(Term.Type.TERM, "pagerank", (List<Term>) null);
		ArrayList<String> idList = new ArrayList<String>();
		idList.add("A");
		idList.add("B");
		Fact dynamicFact = new Fact(idList, jobCall);
		assertTrue(dynamicFact.getName().equals("pagerank"));
		assertEquals(dynamicFact.getType(), Fact.Type.MATRIXASFACTS);
		for (String s : idList)
			assertTrue(dynamicFact.getNames().contains(s));
	}
}
