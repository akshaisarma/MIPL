/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundMatrix.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: An Abstract Class for Built-in Unbound Matrices
 */
package edu.columbia.mipl.builtin.matrix;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;

/**
 * An abstract class that supports matrices with no row or column size.
 */
public abstract class UnboundMatrix extends PrimitiveMatrix<Double> {
	int rowOffset;
	int colOffset;

	/**
	 * Returns the name of the UnboundMatrix.
	 * @return the name of the UnboundMatrix
	 */
	public abstract String getName();

	UnboundMatrix() {
		this(0, 0);
	}

	UnboundMatrix(int rowOffset, int colOffset) {
		this.rowOffset = rowOffset;
		this.colOffset = colOffset;

		status = Status.PM_STATUS_UNBOUND_MATRIX;
	}

	/**
	 * Returns the number of rows.
	 * @return the number of rows, -1 unless the matrix is row-bound.
	 */
	public int getRow() {
		return -1;
	}

	/**
	 * Returns the number of columns.
	 * @return the number of columns, -1 unless the matrix is column-bound.
	 */
	public int getCol() {
		return -1;
	}

	/**
	 * Overriden method. This method has no effects.
	 */
	public final void setValue(int row, int col, Double value) {
		// throw new OutOfBoundException
	}

	/**
	 * Returns the value of the specified position
	 * @param row the row
	 * @param col the column
	 * @return the value of the designated position
	 */
	public abstract Double getValue(int row, int col) /* throws OutOfBoundExcpetion */;
}
