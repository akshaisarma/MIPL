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

	public abstract void createTerm(Term.Type type, double value);

	public abstract void finish();
}
