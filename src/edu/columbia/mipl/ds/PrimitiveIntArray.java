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

public class PrimitiveIntArray extends PrimitiveType implements PrimitiveArray {
	int data[];

	void setData(int data[]) {
		this.data = data;
	}

	int[] getData() {
		return data;
	}

	public PrimitiveIntArray(int size) {
		data = new int[size];
	}

	public void setValue(int index, Object value) {
		data[index] = (int) (Integer) value;
	}

	public Object getValue(int index) {
		return (Object) (Integer) data[index];
	}
}
