package edu.columbia.mipl.ds;

class SparseData {
	int row;
	int col;
	double data;

	SparseData(int row, int col, double data) {
		this.row = row;
		this.col = col;
		this.data = data;
	}
}

public class PrimitiveMatrix extends PrimitiveType {
	int row;
	int col;

	boolean initialized;
	boolean sparse;

	double data[];

	List<SparseData> sparseList;

	PrimitiveMatrix(int row, int col) {
	}

	PrimitiveMatrix() {
	}

	PrimitiveMatrix(int row, int col, double data[]) {
		setData(row, col, data);
	}

	void setData(int row, int col, double data[]) {
	}
}
