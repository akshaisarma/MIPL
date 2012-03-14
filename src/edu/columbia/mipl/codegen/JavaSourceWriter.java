/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: JavaSourceWriter.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: JavaSourceWriter
 */
package edu.columbia.mipl.codegen;

import java.io.*;
import java.util.*;

import edu.columbia.mipl.runtime.*;

public class JavaSourceWriter extends InstructionWriter {

	Stack<String> stack;
	Writer out;
	
	public JavaSourceWriter() {
		String output = "MiplProgram"; /* Should be read from Configuration */

		stack = new Stack<String>();

		File file = new File(output + ".java");
		try {
			out = new BufferedWriter(new FileWriter(file));

			out.write("import edu.columbia.mipl.runtime.*;\n");
			out.write("\n");
			out.write("public class " + output + " {\n");
			out.write("	public static void main(String[] args) {\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getName() {
		return "JavaSrc";
	}

	public void createTerm(Term.Type type, double value) {
		stack.push("new Term(Term.Type.NUMBER, " + value + ")");
	}

	public void createTerm(Term.Type type, String name, List<Term> args) {
	}

	public void finish() {
		try {
			out.write("	}\n");
			out.write("}\n");
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
