/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveMatrix.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive Matrix
 */
package edu.columbia.mipl.datastr;

import java.util.*;
import java.lang.reflect.*;

public class PrimitiveIntArray extends PrimitiveArray {
	int data[];

	void setData(int row, int col, int data[]) {
		if (row % PADDING_ALIGN != 0 || col % PADDING_ALIGN != 0)
			/* throw new UnalignedMatrixSizeException() */;
		this.row = row;
		this.col = col;
		paddedRow = getPaddedLength(row);
		paddedCol = getPaddedLength(col);

		this.data = data;
	}

	int[] getData() {
		return data;
	}

	public PrimitiveIntArray(int row, int col) {
		this(row, col, null);
		data = new int[paddedRow * paddedCol];
	}

	public PrimitiveIntArray(int row, int col, int[] data) /* throws UnalignedMatrixSizeException */ {
		setData(row, col, data);
System.out.println("Int Array");
	}

	public void setValue(int row, int col, Object value) {
		data[flattenIndex(row, col)] = (int) (Integer) value;
	}

	public Object getValue(int row, int col) {
		return (Object) (Integer) data[flattenIndex(row, col)];
	}
}
