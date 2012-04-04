/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveBool.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive Bool
 */
package edu.columbia.mipl.datastr;

import java.util.*;

public class PrimitiveBool /*extends Boolean*/ implements PrimitiveType {
	boolean value;

	public PrimitiveBool(boolean value) {
		this.value = value;
	}

	public boolean getData() {
		return value;
	}
}
