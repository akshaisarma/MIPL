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

	public void reallocateSize() {
		data = Arrays.copyOf(data, paddedCol * paddedRow);
	}

	public void setValue(int row, int col, Object value) {
		data[flattenIndex(row, col)] = (double) (Double) value;
	}

	public Object getValue(int row, int col) {
		return (Object) (Double) data[flattenIndex(row, col)];
	}

	public void copyRange(PrimitiveArray src, int srcRow, int srcCol, int dstRow, int dstCol, int nRows, int nCols) {
		PrimitiveDoubleArray source = (PrimitiveDoubleArray) src;

		assert (nRows + dstRow <= row);
		assert (nCols + dstCol <= col);
		assert (nRows + srcRow <= source.getRow());
		assert (nCols + srcCol <= source.getCol());

		int i;
		int j;

		double[] srcData = source.getData();
		int srcPaddedCol = source.getPaddedCol();

		if (paddedCol == srcPaddedCol && srcCol == 0 && dstRow == 0 &&
				nCols == source.getCol() && nCols == col) {
			System.arraycopy(srcData, srcRow * srcPaddedCol, data, dstRow * paddedCol, paddedCol * nRows);
			return;
		}

		for (i = 0; i < nRows; i++) {
			for (j = 0; j < nCols; j++) {
				data[(dstRow + i) * paddedCol + dstCol + j] = srcData[(srcRow + i) * srcPaddedCol + srcCol + j];
			}
		}
	}
}
