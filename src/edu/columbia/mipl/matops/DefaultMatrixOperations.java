/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: DefaultMatrixOperations.java
 * Author: Jin Hyung Park <jp2105@columbia.edu>
 * Reviewer: Young Hoon Jung <yj2244@columbia.edu>
 * Description: Matrix Operations Default Implementations
 *
 */
package edu.columbia.mipl.matops;

import edu.columbia.mipl.datastr.*;

public class DefaultMatrixOperations implements MatrixOperations {
	boolean checkDimensionMutipliable(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return (arg1.getCol() == arg2.getRow());
	}

	public PrimitiveArray add(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		if (!arg1.equalsDimensionally(arg2))
			/* throw new UncompatiableMatrixDimensionException() */;

		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data2 = a2.getData();
		double[] data = result.getData();

		int i;
		int j;
		int pos;
		int offset = 0;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] + data2[pos];
				pos++;
			}
			offset += arg1.getPaddedRow();
		}

		return result;
	}

	public PrimitiveArray add(final PrimitiveArray arg1, double arg2) {
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data = result.getData();

		int i;
		int j;
		int pos = 0;

		for (i = 0; i < arg1.getRow(); i++) {
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] + arg2;
				pos++;
			}
		}

		return result;
	}

	public PrimitiveArray sub(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		if (!arg1.equalsDimensionally(arg2))
			/* throw new UncompatiableMatrixDimensionException() */;

		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data2 = a2.getData();
		double[] data = result.getData();

		int i;
		int j;
		int pos;
		int offset = 0;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] - data2[pos];
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return result;
	}

	public PrimitiveArray sub(final PrimitiveArray arg1, double arg2) {
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data = result.getData();

		int i;
		int j;
		int pos;
		int offset = 0;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] - arg2;
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return result;
	}

	public PrimitiveArray cellmult(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray mult(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		if (!checkDimensionMutipliable(arg1, arg2))
			/* throw new UncompatiableMatrixDimensionException() */;

		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg2.getCol());
		double[] data = result.getData();

		int i;
		int j;
		int offset = 0;
		int pos;

		for (i = 0; i < result.getRow(); i++) {
			pos = offset;
			for (j = 0; j < result.getCol(); j++) {
				double res = 0;
				for (int k = 0; k < arg1.getCol(); k++) {
					double a1val = (Double) a1.getValue(i, k);
					double a2val = (Double) a2.getValue(k, j);
					res += a1val * a2val;
				}
				data[pos] = res;
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return result;
	}
	public PrimitiveArray mult(final PrimitiveArray arg1, final double arg2) {
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data = result.getData();

		int i;
		int j;
		int offset = 0;
		int pos;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] * arg2;
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return result;
	}

	public PrimitiveArray celldiv(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray div(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray div(final PrimitiveArray arg1, final double arg2) {
		if (arg2 == 0)
			/* throw new divideByZeroException() */;

		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double[] data1 = a1.getData();
		double[] data = result.getData();

		int i;
		int j;
		int offset = 0;
		int pos;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] / arg2;
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return result;
	}

	public void assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
		if (!arg1.equalsDimensionally(arg2))
			/* throw new UncompatiableMatrixDimensionException() */;
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		for (int i = 0; i < arg1.getRow(); ++i)
			for (int j = 0; j < arg1.getCol(); ++j)
				a1.setValue(i, j, a2.getValue(i, j));
	}
	public void assign(PrimitiveArray arg1, double arg2) {
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		for (int i = 0; i < arg1.getRow(); ++i)
			for (int j = 0; j < arg1.getCol(); ++j)
				a1.setValue(i, j, arg2);
	}
	public void addassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void addassign(PrimitiveArray arg1, double arg2) {
	}
	public void subassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void subassign(PrimitiveArray arg1, double arg2) {
	}

	public void cellmultassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void multassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void multassign(PrimitiveArray arg1, double arg2) {
	}
	public void celldivassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void divassign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void divassign(PrimitiveArray arg1, double arg2) {
	}

	public PrimitiveArray transpose(final PrimitiveArray arg1) {
		int pos, offset = 0;
		int row = arg1.getRow();
		int col = arg1.getCol();
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		double data[] = a1.getData();
		double dataT[] = new double[data.length];
		PrimitiveDoubleArray matT = new PrimitiveDoubleArray(col, row, dataT);
		for (int i = 0; i < row; ++i) {
			pos = offset;
			for (int j = 0; j < col; ++j) {
				matT.setValue(j, i, (Double) data[pos]);
				pos++;
			}
			offset += arg1.getPaddedRow();
		}
		return matT;
	}
	public PrimitiveArray inverse(final PrimitiveArray arg1) {
		int row = arg1.getRow();
		int col = arg1.getCol();
		int n = row;
		if (row != col)
			/* throw new UncompatiableMatrixDimensionException() */;
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		double data[] = a1.getData();
		double dataI[] = new double[data.length];
		PrimitiveDoubleArray matI = new PrimitiveDoubleArray(row, col, dataI);

		this.assign((PrimitiveArray) matI, arg1);

		for (int k = 0; k < n - 1; ++k) {
			for (int j = k + 1; j < n; ++j) {
				double tmp = (Double) matI.getValue(k, j) / (Double) matI.getValue(k, k);
				matI.setValue(k, j, (Double) tmp);
				for (int i = k + 1; i < n; ++i) {
					double tmp2 = (Double) matI.getValue(i, j) - 
					(Double) matI.getValue(i, k) * (Double) matI.getValue(k, j);
					matI.setValue(i, j, (Double) tmp2);
				}
			}
		}

		return matI;
	}

	public PrimitiveArray mod(final PrimitiveArray arg1, PrimitiveArray arg2) {
		if (!arg1.equalsDimensionally(arg2))
			/* throw new UncompatiableMatrixDimensionException() */;
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		PrimitiveDoubleArray r = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		for (int i = 0; i < arg1.getRow(); ++i)
			for (int j = 0; j < arg1.getCol(); ++j) {
				int v = (int) ((Double) a1.getValue(i, j) / (Double) a2.getValue(i, j));
				r.setValue(i, j, (Double) a1.getValue(i, j) - (v * (Double) a2.getValue(i, j)));
			}
		return r;
	}
	public PrimitiveArray mod(final PrimitiveArray arg1, double arg2) {
		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray r = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		for (int i = 0; i < arg1.getRow(); ++i)
			for (int j = 0; j < arg1.getCol(); ++j) {
				int v = (int) ((Double) a1.getValue(i, j) / arg2);
				r.setValue(i, j, (Double) a1.getValue(i, j) - (v * arg2));
			}
		return r;
	}
	public double sum(final PrimitiveArray arg1) {
		return 0;
	}
	public double mean(final PrimitiveArray arg1) {
		return 0;
	}
	public PrimitiveArray rowsum(final PrimitiveArray arg1) {
		return null;
	}
	public PrimitiveArray rowmean(final PrimitiveArray arg1) {
		return null;
	}
}
