	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: RuleTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Rule 
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class RuleTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(RuleTest.class);
	}

	@Override
	protected void setUp() {
	}
	
	public void testRule() {
		Term term1 = new Term(Term.Type.TERM, "simpleterm", (List<Term>) null);
		Term term2 = new Term(Term.Type.NOTTERM, term1);
		Term andTerm = new Term(Term.Type.ANDTERMS, term1, term2);
		Term ruleTerm = new Term(Term.Type.TERM, "rule", (List<Term>) null);
		Rule rule = new Rule(ruleTerm, andTerm);
		assertTrue(rule.getTerm() == ruleTerm);
		assertTrue(rule.getName().equals("rule"));
		assertTrue(rule.getSource() == andTerm);
	}
}
