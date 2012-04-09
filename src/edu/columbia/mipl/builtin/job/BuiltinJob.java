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

public interface BuiltinJob {
	public String getName();
	public List<PrimitiveType> jobImplementation(PrimitiveType ... args)  throws MiplRuntimeException;
}
