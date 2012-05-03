/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: BuiltinJob.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: An Abstract Class for Built-in Jobs
 */
package edu.columbia.mipl.builtin.job;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.runtime.execute.*;

/**
 * An interface for the Built-in Jobs
 */
public interface BuiltinJob {
	/**
	 * Returns the name of the Built-in Job
	 * @return the name of the Built-in Job
	 */
	public String getName();

	/**
	 * The Job implementation.
	 * @param args a variable number of arguments that take PrimitiveTypes, i.e. PrimitiveMatrix, PrimitiveDouble, or PrimitiveString
	 * @return a list of the result PrimitiveTypes
	 */
	public List<PrimitiveType> jobImplementation(PrimitiveType ... args)  throws MiplRuntimeException;
}
