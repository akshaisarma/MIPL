/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: InstructionWriter.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: InstructionWriter
 */
package edu.columbia.mipl.codegen;

import java.util.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.datastr.*;

public abstract class InstructionWriter {
	public enum Method {
		PRE,
		IN,
		POST,
	};

	InstructionWriter() {
		InstructionWriterFactory.registerInstructionWriter(this);
	}

	public abstract String getName();

	public abstract void createTerm(Term.Type type, Term term1,
										Expression expr1);
	public abstract void createTerm(Term.Type type, Expression expr1,
										Expression expr2);
	public abstract void createTerm(Term.Type type, String name,
										PrimitiveMatrix<Double> matrix);
	public abstract void createTerm(Term.Type type, Term term1, Term term2);
	public abstract void createTerm(Term.Type type, Term term1);
	public abstract void createTerm(Term.Type type, String name,
										List<Term> arguments);
	public abstract void createTerm(Term.Type type, double value);
	public abstract void createTerm(Term.Type type, String name);
	public abstract void createTerm(Term.Type type, Expression expr1);

	public abstract void createExpression(Expression.Type type, Term term);
	public abstract void createExpression(Expression.Type type, Expression expr1,
											Expression expr2);

	public abstract void finish();
}
