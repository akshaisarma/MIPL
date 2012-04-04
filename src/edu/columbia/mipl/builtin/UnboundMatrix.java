/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundMatrix.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: An Abstract Class for Built-in Unbound Matrices
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;

public abstract class UnboundMatrix extends PrimitiveMatrix<Double> {
	int rowOffset;
	int colOffset;

	public abstract String getName();

	UnboundMatrix() {
		this(0, 0);
	}

	UnboundMatrix(int rowOffset, int colOffset) {
		this.rowOffset = rowOffset;
		this.colOffset = colOffset;

		status = Status.PM_STATUS_UNBOUND_MATRIX;
	}

	public final int getRow() {
		return -1;
	}

	public final int getCol() {
		return -1;
	}

	public final void setValue(int row, int col, Double value) {
		// throw new OutOfBoundException
	}

	public abstract Double getValue(int row, int col) /* throws OutOfBoundExcpetion */;
}
