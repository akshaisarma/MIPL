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

public class PrimitiveDoubleArray extends PrimitiveArray {
	double data[];

	public double[] getData() {
		return data;
	}

	public PrimitiveDoubleArray(int row, int col) {
		this(row, col, null);
	}

	public PrimitiveDoubleArray(int row, int col, double data[]) /* throws UnalignedMatrixSizeException */ {
		this.row = row;
		this.col = col;
		paddedRow = getPaddedLength(row);
		paddedCol = getPaddedLength(col);

		if (data == null)
			data = new double[paddedRow * paddedCol];

		this.data = data;
	}

	public void setValue(int row, int col, Object value) {
		data[flattenIndex(row, col)] = (double) (Double) value;
	}

	public Object getValue(int row, int col) {
		return (Object) (Double) data[flattenIndex(row, col)];
	}
}
