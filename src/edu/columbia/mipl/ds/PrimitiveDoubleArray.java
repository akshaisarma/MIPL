/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveMatrix.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive Matrix
 */
package edu.columbia.mipl.ds;

import java.util.*;
import java.lang.reflect.*;

public class PrimitiveDoubleArray extends PrimitiveType implements PrimitiveArray {
	double data[];

	void setData(double data[]) {
		this.data = data;
	}

	double[] getData() {
		return data;
	}

	public PrimitiveDoubleArray(int size) {
		data = new double[size];
	}

	public void setValue(int index, Object value) {
		data[index] = (double) (Double) value;
	}

	public Object getValue(int index) {
		return (Object) (Double) data[index];
	}
}
