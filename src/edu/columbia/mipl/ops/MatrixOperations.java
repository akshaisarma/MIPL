/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixOperations.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Matrix Operations Interface
 *
 */

import edu.columbia.mipl.ds.*;

public interface MatrixOperations {
	public PrimitiveArray add(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray add(final PrimitiveArray arg1, double arg2);
	public PrimitiveArray sub(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray sub(final PrimitiveArray arg1, double arg2);

	public PrimitiveArray cellmult(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray mult(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray mult(final PrimitiveArray arg1, final double arg2);

	public PrimitiveArray celldiv(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray div(final PrimitiveArray arg1, final PrimitiveArray arg2);
	public PrimitiveArray div(final PrimitiveArray arg1, final double arg2);

	public void assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void add_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void add_and_assign(PrimitiveArray arg1, double arg2);
	public void sub_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void sub_and_assign(PrimitiveArray arg1, double arg2);

	public void cellmult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void mult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void mult_and_assign(PrimitiveArray arg1, double arg2);
	public void celldiv_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void div_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void div_and_assign(PrimitiveArray arg1, double arg2);

	public PrimitiveArray transpose(final PrimitiveArray arg1);
	public PrimitiveArray inverse(final PrimitiveArray arg1);

	public double sum(final PrimitiveArray arg1);
	public double mean(final PrimitiveArray arg1);
	public PrimitiveArray row_sum(final PrimitiveArray arg1);
	public PrimitiveArray row_mean(final PrimitiveArray arg1);
}
