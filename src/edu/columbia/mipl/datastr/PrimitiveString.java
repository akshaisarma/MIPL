/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveString.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive String
 */
package edu.columbia.mipl.datastr;

import java.util.*;

public class PrimitiveString /*extends String*/ implements PrimitiveType {
	String value;

	public PrimitiveString(String value) {
		this.value = value;
	}

	public String getData() {
		return value;
	}
}
