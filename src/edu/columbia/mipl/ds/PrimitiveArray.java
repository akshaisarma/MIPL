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

public abstract class PrimitiveArray {
	/* align the matrix size to 4 in order to benefit from HW acceleration */
	final int PADDING_ALIGN = 4;
	int paddedRow;
	int paddedCol;

	int row;
	int col;

	int getPaddedLength(int length) {
		// This should make 1, 2, 3, or 4 => 4
		//                 // This should make 5, 6, 7, or 8 => 8
		return ((length - 1) & ~(PADDING_ALIGN - 1)) + PADDING_ALIGN;
	}

	int flattenIndex(int row, int col) {
		return paddedCol * row + col;
	}

	int getRow() {
		return row;
	}

	int getCol() {
		return col;
	}

	int getPaddedRow() {
		return paddedRow;
	}

	int getPaddedCol() {
		return paddedCol;
	}

	abstract void setValue(int row, int col, Object value);
	abstract Object getValue(int row, int col);
}
