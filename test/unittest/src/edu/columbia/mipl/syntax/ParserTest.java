package edu.columbia.mipl.syntax;

import java.io.*;
import java.util.*;

import junit.framework.TestCase;

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
			try {
				success &= Parser.parse(testInputPath + "/" + children[i]);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new IOException("Error on parsing an input file: " + children[i]);
			}
		}

		assertTrue(success);
	}
}
