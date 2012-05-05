/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: TermTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Term 
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class TermTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(TermTest.class);
	}
	@Override
	protected void setUp() {
	}

	public void testSimpleTerm() {
		Term simpleTerm = new Term(Term.Type.TERM, "simpleterm", (List<Term>) null);
		assertEquals(simpleTerm.getName(), "simpleterm");
	}
	
	public void testComplexTerm() {
		Term term1 = new Term(Term.Type.TERM, "simpleterm", (List<Term>) null);
		Term term2 = new Term(Term.Type.NOTTERM, term1);
		Term complexTerm = new Term(Term.Type.ANDTERMS, term1, term2);
		assertTrue(complexTerm.getTerm1() == term1 && complexTerm.getTerm2() == term2);
	}
}
