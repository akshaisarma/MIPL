/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveDouble.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive Double
 */
package edu.columbia.mipl.datastr;

import java.util.*;

public class PrimitiveDouble extends PrimitiveType {
	Double value;

	public PrimitiveDouble(Double value) {
		this.value = value;
	}

	public Double getData() {
		return value;
	}
}
