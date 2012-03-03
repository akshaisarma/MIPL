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

public class PrimitiveMatrix<T> extends PrimitiveType {
	int row;
	int col;

	PrimitiveArray data;

	public enum Status {
		PM_STATUS_INVALID,
		PM_STATUS_URI_LOCAL,
		PM_STATUS_URI_REMOTE,
		PM_STATUS_LOADED_FULL,
		PM_STATUS_LOADED_SPARSE,
	};
	Status status;
	String uri;

	public static void main(String args[]) {
		/* Unit Tests */
		PrimitiveMatrix<Double> pm = new PrimitiveMatrix<Double>(1, 1);
		PrimitiveMatrix<Integer> pm_int = new PrimitiveMatrix<Integer>(100, 1);
		pm_int.setValue(10, 0, 300);

		double newdata[] = new double[100];
		PrimitiveMatrix<Double> pm_with_array = new PrimitiveMatrix<Double>(new PrimitiveDoubleArray(10, 10, newdata));
		pm_with_array.setValue(0, 0, 3.244); // without this line, pda will be null
		PrimitiveDoubleArray pda = (PrimitiveDoubleArray) pm_with_array.getData();
		double arr[] = pda.getData();
		System.out.println(arr[0]);
	}

	Map<Integer, T> sparseList;

	/* SparseMatrix */
	PrimitiveMatrix(int row, int col) {
		this.row = row;
		this.col = col;
		sparseList = new HashMap<Integer, T>();
		status = Status.PM_STATUS_LOADED_SPARSE;
	}

	/* FullMatrix */
	PrimitiveMatrix(PrimitiveArray data) {
		setData(data.getRow(), data.getCol(), data);
	}

	PrimitiveMatrix(String uri) {
		this(uri, true);
	}

	PrimitiveMatrix(String uri, boolean isLocal) {
		this.uri = uri;
		if (isLocal) {
			status = Status.PM_STATUS_URI_LOCAL;
		}
		else {
			status = Status.PM_STATUS_URI_REMOTE;
		}
	}

	int makeHashKey(int row, int col) {
		return this.col * row + col;
	}

	boolean checkOutOfBound(int row, int col)
	{
		if (row >= this.row || col >= this.col)
			return true;
		return false;
	}

	void setData(int row, int col, PrimitiveArray data) {
		this.data = data;
		status = Status.PM_STATUS_LOADED_FULL;
	}

	PrimitiveArray getData() {
		if (data == null) {
			// throw new DataRequestedToSparseMatrix();
			// or, transform into a full matrix
		}
		return data;
	}

	void loadMatrix() {
		if (status == Status.PM_STATUS_URI_LOCAL) {
			// matrixLoader = MatrixLoaderFactory.getInstance().getMatrixLoader(MatrixType);
			// data = matrixLoader.loadMatrix(uri);
			// status = Status.PM_STATUS_LOADED_FULL
			// or
			// sparseList = matrixLoader.loadMatrix(uri);
			// status = Status.PM_STATUS_LOADED_SPARSE
		}
		// status == REMOTE : similar to LOCAL or MatrixFactory returns a remote matrix loader;
	}

	void setValue(int row, int col, T value) /* throws OutOfBoundExcpetion */ {
		if (checkOutOfBound(row, col)) {
			// throw new OutOfBoundException();
		}

		loadMatrix();

		if (status == Status.PM_STATUS_LOADED_SPARSE) {
			sparseList.put(makeHashKey(row, col), value);
		}
		else if (status == Status.PM_STATUS_LOADED_FULL) {
			data.setValue(row, col, (Object) value);
		}
	}

	public T getValue(int row, int col) /* throws OutOfBoundExcpetion */ {
		if (checkOutOfBound(row, col)) {
			// throw new OutOfBoundException();
		}

		loadMatrix();

		if (status == Status.PM_STATUS_LOADED_SPARSE) {
			return sparseList.get(makeHashKey(row, col));
		}
		else if (status == Status.PM_STATUS_LOADED_FULL) {
			if (data == null)
				return (T) (Object) 0;

			return (T) data.getValue(row, col);
		}
		// throw new InvalidStatusException();
		return null;
	}
}
