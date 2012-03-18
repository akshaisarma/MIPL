package edu.columbia.mipl.syntax;

import java.io.*;
import java.util.*;

import junit.framework.TestCase;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.execute.*;

public class ParserTest extends TestCase {

	static final String testInputPath = "test/input";
	public static void main(String args[]) {
		junit.textui.TestRunner.run(ParserTest.class);
	}
	@Override
	protected void setUp() {
	}
	public void testInputs() throws java.io.IOException {
		File dir = new File(testInputPath);
		String[] children = dir.list();
		boolean success = true;

		for (int i = children.length - 1; i >= 0; i--) {
			if (children[i].startsWith("."))
				continue;
			Parser parser = new Parser(testInputPath + "/" + children[i]);
			success &= (parser.getNumError() == 0);
		}

		assertTrue(success);
	}

	public void testInputsSemantic() throws java.io.IOException {
		File dir = new File(testInputPath);
		String[] children = dir.list();
		boolean success = true;

		for (int i = children.length - 1; i >= 0; i--) {
			if (children[i].startsWith("."))
				continue;
			Program program = new Program(new SemanticChecker());
			Parser parser = new Parser(testInputPath + "/" + children[i], program);
			success &= (parser.getNumError() == 0);
		}

		assertTrue(success);
	}
}
