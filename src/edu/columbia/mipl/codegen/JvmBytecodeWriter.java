/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: JvmBytecodeWriter.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: JvmBytecodeWriter
 */
package edu.columbia.mipl.codegen;

import java.util.*;

import org.apache.bcel.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.datastr.*;

public class JvmBytecodeWriter extends InstructionWriter {

	InstructionList il;

	/* read http://commons.apache.org/bcel/manual.html */
	public JvmBytecodeWriter() {
		String output = "MiplProgram"; /* Should be read from Configuration */

		ClassGen cg = new ClassGen(output, "java.lang.Object",
				"<generated>", Constants.ACC_PUBLIC | Constants.ACC_SUPER,
				null);
		ConstantPoolGen cp = cg.getConstantPool();
		il = new InstructionList();

		MethodGen mg = new MethodGen(Constants.ACC_STATIC | Constants.ACC_PUBLIC,
				Type.VOID,
				new Type[] {new ArrayType(Type.STRING, 1)},
				new String[] {"argv"},
				"main", output,
				il, cp);

		InstructionFactory factory = new InstructionFactory(cg);
	}

	public String getName() {
		return "JVM";
	}

	public void createTerm(Term.Type type, Term term1, Expression expr1) {
	// Term.Type.IS
	}

	public void createTerm(Term.Type type, Expression expr1, Expression expr2) {
		switch (type) {
			case EQ:
				break;
			case LT:
				break;
			case LE:
				break;
			case GT:
				break;
			case GE:
				break;
			case NE:
				break;
		}

	}

	public void createTerm(Term.Type type, String name,
										PrimitiveMatrix<Double> matrix) {
	// Term.Type.MATRIX
	}

	public void createTerm(Term.Type type, Term term1, Term term2) {
		switch (type) {
			case ANDTERMS:
				break;
			case ORTERMS:
				break;
		}
	}

	public void createTerm(Term.Type type, Term term1) {
	// Term.Type.NOTTERM
	}

	public void createTerm(Term.Type type, String name, List<Term> arguments) {
		switch (type) {
			case REGEXTERM:
				break;
			case TERM:
				break;
		}
	}

	public void createTerm(Term.Type type, double value) {
	// Term.Type.NUMBER

	}

	public void createTerm(Term.Type type, String name) {
		switch (type) {
			case VARIABLE:
				break;
			case QUERYALL:
				break;
			case REGEXQUERYALL:
				break;
			case STRING:
				break;
		}
	}

	public void createTerm(Term.Type type, Expression expr1) {
	// Term.Type.EXPRESSION

	}

	public void createExpression(Expression.Type type, Term term1) {
	// Term.Type.Term

	}

	public void createExpression(Expression.Type type, Expression expr1,
									Expression expr2) {
		switch (type) {
			case MINUS:
				break;
			case PLUS:
				break;
			case MULTI:
				break;
			case DIVIDE:
				break;
		}
	}

// ObjectType i_stream = new ObjectType("java.io.InputStream");
	public void finish() {
	}
}
