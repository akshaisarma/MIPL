package edu.columbia.mipl.codegen;

import java.io.*;
import java.util.*;

import junit.framework.TestCase;

import edu.columbia.mipl.syntax.*;
import edu.columbia.mipl.codegen.*;
import edu.columbia.mipl.conf.*;
import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.execute.*;
import edu.columbia.mipl.runtime.traverse.*;

public class JVMByteCodeWriterTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(JavaSourceWriterTest.class);
	}
	@Override
	protected void setUp() {
	}
	public void testMultiReturnInput() {
		Configuration conf = Configuration.getInstance();
		conf.setWriter("ByteCode");
		Parser parser = new Parser("test/input/multi_return.mipl", new Program(new CodeGenerator("build", "MiplProgram")));
	}

	public void testBuildGeneratedJavaSrc() throws IOException {
		String command = "javac build/MiplProgram.java -cp build";
		Process child = Runtime.getRuntime().exec(command);
	}
}
