/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: InstructionWriterFactory.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: InstructionWriterFactory
 */
package edu.columbia.mipl.codegen;

import java.util.*;

import edu.columbia.mipl.runtime.*;

public class InstructionWriterFactory {
	HashMap<String, InstructionWriter> hash;

	static InstructionWriterFactory instance;

	static {
		instance = new InstructionWriterFactory();
		instance.hash = new HashMap<String, InstructionWriter>();
		new JavaSourceWriter(); //TODO: should be read dynamically from Configuration
	}

	public static InstructionWriter getInstructionWriter(String name) {
		return instance.hash.get(name.toLowerCase());
	}

	public static void registerInstructionWriter(InstructionWriter writer) {
		instance.hash.put(writer.getName().toLowerCase(), writer);
	}
}
