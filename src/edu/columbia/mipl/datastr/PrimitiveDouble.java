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

public class PrimitiveDouble /*extends Double*/ implements PrimitiveType {
	private Double value;

	public PrimitiveDouble(Double value) {
		this.value = value;
	}

	public Double getData() {
		return value;
	}
	
	public void setData(Double value) {
		this.value = value;
	}
}
