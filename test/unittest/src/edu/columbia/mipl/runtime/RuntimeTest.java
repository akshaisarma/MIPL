package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class RuntimeTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(RuntimeTest.class);
	}
	@Override
	protected void setUp() {
	}
	public void testSimpleTerm() {
		Term simpleTerm = new Term(Term.Type.TERM, "simpleterm", (List<Term>) null);
		assertEquals(simpleTerm.getName(), "simpleterm");
	}
}
