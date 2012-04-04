/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: BuiltinLoad.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: A Class that implements Built-in Load Job
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;

public class BuiltinLoad implements BuiltinJob {
	public String getName() {
		return "load";
	}

	public List<PrimitiveType> jobImplementation(PrimitiveType args[]) {
		List<PrimitiveType> list = new ArrayList<PrimitiveType>();

		String filename = list.get(0).toString();

		list.add(new PrimitiveMatrix<Double>(filename));
		
		return list;
	}
}
