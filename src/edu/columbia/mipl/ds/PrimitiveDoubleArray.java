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

public class PrimitiveDoubleArray extends PrimitiveArray {
	double data[];

	void setData(int row, int col, double data[]) /* throws UnalignedMatrixSizeException */ {
		if (row % PADDING_ALIGN != 0 || col % PADDING_ALIGN != 0)
			/* throw new UnalignedMatrixSizeException() */;
		this.row = row;
		this.col = col;
		paddedRow = getPaddedLength(row);
		paddedCol = getPaddedLength(col);
		this.data = data;
	}

	double[] getData() {
		return data;
	}

	public PrimitiveDoubleArray(int row, int col) {
		this(row, col, null);
		data = new double[paddedRow * paddedCol];
	}

	public PrimitiveDoubleArray(int row, int col, double data[]) /* throws UnalignedMatrixSizeException */ {
		setData(row, col, data);
	}

	public void setValue(int row, int col, Object value) {
		data[flattenIndex(row, col)] = (double) (Double) value;
	}

	public Object getValue(int row, int col) {
		return (Object) (Double) data[flattenIndex(row, col)];
	}
}
