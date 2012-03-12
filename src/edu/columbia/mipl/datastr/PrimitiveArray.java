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

public abstract class PrimitiveArray {
	/* align the matrix size to 4 in order to benefit from HW acceleration */
	final int paddingAlign = 4;
	int paddedRow;
	int paddedCol;

	int row;
	int col;

	final int increaseRate = 2;

	int getPaddedLength(int length) {
		// This should make 1, 2, 3, or 4 => 4
		//                 // This should make 5, 6, 7, or 8 => 8
		return ((length - 1) & ~(paddingAlign - 1)) + paddingAlign;
	}

	int flattenIndex(int row, int col) {
		return paddedCol * row + col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getPaddedRow() {
		return paddedRow;
	}

	public int getPaddedCol() {
		return paddedCol;
	}

	public void increaseCol() {
		increaseCol(1);
	}

	public void increaseCol(int n) {
		assert (row == 1);
		if (col + n > paddedCol) {
			paddedCol = (col + n) * increaseRate;
			reallocateSize();
		}

		col += n;
	}

	public void increaseRow() {
		increaseRow(1);
	}

	public void increaseRow(int n) {
		if (row + n > paddedRow) {
			paddedRow = (row + n) * increaseRate;
			reallocateSize();
		}

		row += n;
	}

	public void mergeVertically(PrimitiveArray source) {
		assert (col == source.getCol());
		int prevRow = row;
		increaseRow(source.getRow());

		copyRange(source, 0, source.getRow(), 0, source.getCol(), prevRow, col);
	}

	abstract public void copyRange(PrimitiveArray source, int srcRow, int srcCol,
			int dstRow, int dstCol, int nRows, int nCols);

	abstract void reallocateSize();

	abstract void setValue(int row, int col, Object value);
	abstract Object getValue(int row, int col);

	public abstract boolean equalsSemantically(final PrimitiveArray arg);
	public abstract boolean equalsDimensionally(final PrimitiveArray arg);
	public abstract void printMatrix();
}
