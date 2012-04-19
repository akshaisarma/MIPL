/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: CompilerTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Tests from top-bottom of full compilation
 *				and execution of the test input .mipl files
 */

package edu.columbia.mipl;

import java.io.*;
import java.util.*;
import java.lang.Runtime;

import junit.framework.TestCase;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.execute.*;

public class CompilerTest extends TestCase {

	static String testInputPath = "test/input";
	static String miplMainCommand = "java -cp build edu.columbia.mipl.Main ";
	static String compiledInputCommand = "javac -cp build build/MiplProgram.java";
	static Runtime runtime;

	public static void main(String args[]) {
		junit.textui.TestRunner.run(CompilerTest.class);
	}

	@Override
	protected void setUp() {
		this.runtime = Runtime.getRuntime();
	}

	public void testExecutionSuccess() throws java.io.IOException, java.lang.InterruptedException {
		String[] inputFiles = new File(testInputPath).list();
		BufferedReader reader = new BufferedReader(new FileReader("cp_hadoop"));
		String runCompiledInputCommand = reader.readLine();

		boolean success = true;
		for (int i = 0; i < inputFiles.length ; i++) {
			if (inputFiles[i].startsWith("."))
				continue;

			Process mainOfMIPL = runtime.exec(miplMainCommand + "/" + testInputPath + inputFiles[i]);
			success &= (mainOfMIPL.waitFor() == 0);

			Process compileTarget = runtime.exec(compiledInputCommand);
			success &= (compileTarget.waitFor() == 0);

			Process runTarget = runtime.exec(runCompiledInputCommand);
			success &= (runTarget.waitFor() == 0);
		}
		assertTrue(success);
	}
}
