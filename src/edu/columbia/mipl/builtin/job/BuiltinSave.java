/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: BuiltinSave.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: A Class that implements Built-in Save Job
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.runtime.execute.*;

public class BuiltinSave implements BuiltinJob {
	public String getName() {
		return "save";
	}

	public List<PrimitiveType> jobImplementation(PrimitiveType ... args) throws MiplRuntimeException {
		if (args.length != 2)
			throw new MiplRuntimeException();

		if (!(args[0] instanceof PrimitiveMatrix))
			throw new MiplRuntimeException();

		if (!(args[1] instanceof PrimitiveString))
			throw new MiplRuntimeException();

		MatrixLoader loader = MatrixLoaderFactory.getMatrixLoader("table");
		loader.saveMatrix(((PrimitiveString) args[1]).getData(), (PrimitiveMatrix) args[0]);
		
		return null;
	}
}
