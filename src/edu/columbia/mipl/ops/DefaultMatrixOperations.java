/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: DefaultMatrixOperations.java
 * Author: 
 * Reviewer: 
 * Description: Matrix Operations Default Implementations
 *
 */

import edu.columbia.mipl.ds.*;

public class DefaultMatrixOperations implements MatrixOperations {
	boolean checkDimensionSame(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return (arg1.getRow() == arg2.getRow() && arg1.getCol() == arg2.getCol());
	}

	public PrimitiveArray add(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		if (!checkDimensionSame(arg1, arg2))
			/* throw new UncompatiableMatrixDimensionException() */;

		PrimitiveDoubleArray a1 = (PrimitiveDoubleArray) arg1;
		PrimitiveDoubleArray a2 = (PrimitiveDoubleArray) arg2;
		PrimitiveDoubleArray result = new PrimitiveDoubleArray(arg1.getRow(), arg1.getCol());
		double data1[] = a1.getData();
		double data2[] = a2.getData();
		double data[] = result.getData();

		int i;
		int j;
		int offset = 0;
		int pos;

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
		double data1[] = a1.getData();
		double data[] = result.getData();

		int i;
		int j;
		int offset = 0;
		int pos;

		for (i = 0; i < arg1.getRow(); i++) {
			pos = offset;
			for (j = 0; j < arg1.getCol(); j++) {
				data[pos] = data1[pos] + arg2;
				pos++;
			}
			offset += arg1.getPaddedRow();
		}

		return result;
	}

	public PrimitiveArray sub(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}

	public PrimitiveArray sub(final PrimitiveArray arg1, double arg2) {
		return null;
	}

	public PrimitiveArray cellmult(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray mult(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray mult(final PrimitiveArray arg1, final double arg2) {
		return null;
	}

	public PrimitiveArray celldiv(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray div(final PrimitiveArray arg1, final PrimitiveArray arg2) {
		return null;
	}
	public PrimitiveArray div(final PrimitiveArray arg1, final double arg2) {
		return null;
	}

	public void assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void add_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void add_and_assign(PrimitiveArray arg1, double arg2) {
	}
	public void sub_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void sub_and_assign(PrimitiveArray arg1, double arg2) {
	}

	public void cellmult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void mult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void mult_and_assign(PrimitiveArray arg1, double arg2) {
	}
	public void celldiv_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void div_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2) {
	}
	public void div_and_assign(PrimitiveArray arg1, double arg2) {
	}

	public PrimitiveArray transpose(final PrimitiveArray arg1) {
		return null;
	}
	public PrimitiveArray inverse(final PrimitiveArray arg1) {
		return null;
	}

	public double sum(final PrimitiveArray arg1) {
		return 0;
	}
	public double mean(final PrimitiveArray arg1) {
		return 0;
	}
	public PrimitiveArray row_sum(final PrimitiveArray arg1) {
		return null;
	}
	public PrimitiveArray row_mean(final PrimitiveArray arg1) {
		return null;
	}
}
