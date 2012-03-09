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

public class PrimitiveMatrix<T> extends PrimitiveType {
	PrimitiveArray data;

	public enum Status {
		PM_STATUS_INVALID,
		PM_STATUS_URI_LOCAL,
		PM_STATUS_URI_REMOTE,
		PM_STATUS_LOADED_FULL,
		PM_STATUS_LOADED_SPARSE,
		/* Reference or SubMatrix Type may be added for performance */
	};
	Status status;
	String uri;

	int sparseRow;
	int sparseCol;

	public static void main(String[] args) {
		/* Unit Tests */
		PrimitiveMatrix<Double> pm = new PrimitiveMatrix<Double>();
		PrimitiveMatrix<Integer> pmint = new PrimitiveMatrix<Integer>();
		pmint.setValue(10, 0, 300);

		double[] newdata = new double[100];
		PrimitiveMatrix<Double> pmarray = new PrimitiveMatrix<Double>(new PrimitiveDoubleArray(10, 10, newdata));
		pmarray.setValue(0, 0, 3.244); // without this line, pda will be null
		PrimitiveDoubleArray pda = (PrimitiveDoubleArray) pmarray.getData();
		double[] arr = pda.getData();
		System.out.println(arr[0]);
	}

	Map<String, T> sparseList;

	/* SparseMatrix */
	public PrimitiveMatrix() {
		sparseList = new HashMap<String, T>();
		status = Status.PM_STATUS_LOADED_SPARSE;
	}

	/* FullMatrix */
	public PrimitiveMatrix(PrimitiveArray data) {
		setData(data);
	}

	public PrimitiveMatrix(String uri) {
		this(uri, true);
	}

	public PrimitiveMatrix(String uri, boolean isLocal) {
		this.uri = uri;
		if (isLocal) {
			status = Status.PM_STATUS_URI_LOCAL;
		}
		else {
			status = Status.PM_STATUS_URI_REMOTE;
		}
	}

	public int getRow() {
		if (status == Status.PM_STATUS_LOADED_FULL)
			return data.getCol();
		return sparseRow;
	}

	public int getCol() {
		if (status == Status.PM_STATUS_LOADED_FULL)
			return data.getCol();
		return sparseCol;
	}

	void increaseRow() {
		data.increaseRow();
	}

	void increaseRow(int n) {
		assert (status == Status.PM_STATUS_LOADED_FULL);
		data.increaseRow(n);
	}

	String makeHashKey(int row, int col) {
		return row + "," + col;
	}

	void setData(PrimitiveArray data) {
		this.data = data;
		status = Status.PM_STATUS_LOADED_FULL;
	}

	PrimitiveArray getData() {
		if (data == null) {
			// throw new DataRequestedToSparseMatrix();
			// or, transform into a full matrix
			;
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

	public void setValue(int row, int col, T value) /* throws OutOfBoundExcpetion */ {
		loadMatrix();

		if (status == Status.PM_STATUS_LOADED_SPARSE) {
			sparseList.put(makeHashKey(row, col), value);
		}
		else if (status == Status.PM_STATUS_LOADED_FULL) {
			data.setValue(row, col, (Object) value);
		}
	}

	public T getValue(int row, int col) /* throws OutOfBoundExcpetion */ {
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
