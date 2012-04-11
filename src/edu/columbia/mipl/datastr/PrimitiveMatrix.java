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

public class PrimitiveMatrix<T> implements PrimitiveType {
	PrimitiveArray data;

	public enum Status {
		PM_STATUS_INVALID,
		PM_STATUS_URI_LOCAL,
		PM_STATUS_URI_REMOTE,
		PM_STATUS_LOADED_FULL,
		PM_STATUS_LOADED_SPARSE,
		PM_STATUS_UNBOUND_MATRIX,
		/* Reference or SubMatrix Type may be added for performance */
	};
	protected Status status;
	String uri;

	int sparseRow;
	int sparseCol;

	Map<String, T> sparseList;

	protected PrimitiveMatrix() {
		status = Status.PM_STATUS_INVALID;
	}

	/* SparseMatrix */
	public PrimitiveMatrix(int row, int col) {
		sparseList = new HashMap<String, T>();
		status = Status.PM_STATUS_LOADED_SPARSE;
		sparseRow = row;
		sparseCol = col;
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
			return data.getRow();
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

	public PrimitiveArray getData() {
		if (data == null) {
			// TODO:
			// throw new DataRequestedToSparseMatrix();
			// or, transform into a full matrix
			switch (status) {
				case PM_STATUS_INVALID:
					// error
					break;
				case PM_STATUS_URI_LOCAL:
				case PM_STATUS_URI_REMOTE:
					loadMatrix();
					break;
				case PM_STATUS_LOADED_FULL:
					// error
					break;
				case PM_STATUS_LOADED_SPARSE:
					data = new PrimitiveDoubleArray(sparseRow, sparseCol);
					break;
				case PM_STATUS_UNBOUND_MATRIX:
					// error
					break;
			}

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
		else if (status == Status.PM_STATUS_URI_REMOTE) {
		// TODO: similar to LOCAL or MatrixFactory returns a remote matrix loader;
		}
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
	
	public String getURI() {
		return uri;
	}
	
	public Status getStatus() {
		return status;
	}

	public void mergeVertically(PrimitiveMatrix<T> source) {
		data.mergeVertically(source.getData());
	}

	public void print() {
		data.printMatrix();
	}
}
