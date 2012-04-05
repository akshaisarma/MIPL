/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: BuiltinTable.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: A Class for Managing Builtin Jobs and Matrices
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.builtin.job.*;
import edu.columbia.mipl.builtin.matrix.*;
import edu.columbia.mipl.runtime.execute.*;

public class BuiltinTable {
	static Map<String, BuiltinJob> jobTable;
	static Map<String, UnboundMatrix> matrixTable;

	static {
		// TODO: read directory name from Configuration
		jobTable = new HashMap<String, BuiltinJob>();
		matrixTable = new HashMap<String, UnboundMatrix>();

		jobTable.put("load", new BuiltinLoad());
		jobTable.put("save", new BuiltinSave());
	}

	public static boolean existJob(String name) {
		return (jobTable.get(name) != null);
	}

	public static List<PrimitiveType> job(String name, PrimitiveType ... args) throws MiplRuntimeException {
		if (!existJob(name)) {
			new Exception("No such job!").printStackTrace();
			return null;
		}

		return jobTable.get(name).jobImplementation(args);
	}
}
