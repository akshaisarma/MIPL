package edu.columbia.mipl.syntax;

import java.util.*;

import junit.framework.TestCase;

public class ParserTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(ParserTest.class);
	}
	@Override
	protected void setUp() {
	}
	public void testInputs() throws java.io.IOException {
		Parser.parse("test/input/pagerank.mipl");
		assertTrue(true);
	}
}
