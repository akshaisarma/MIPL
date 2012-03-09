/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixOperations.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Matrix Operations Interface
 *
 */
package edu.columbia.mipl.matops;

import edu.columbia.mipl.datastr.*;

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
	public void addassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void addassign(PrimitiveArray arg1, double arg2);
	public void subassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void subassign(PrimitiveArray arg1, double arg2);

	public void cellmultassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void multassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void multassign(PrimitiveArray arg1, double arg2);
	public void celldivassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void divassign(PrimitiveArray arg1, final PrimitiveArray arg2);
	public void divassign(PrimitiveArray arg1, double arg2);

	public PrimitiveArray transpose(final PrimitiveArray arg1);
	public PrimitiveArray inverse(final PrimitiveArray arg1);

	public double sum(final PrimitiveArray arg1);
	public double mean(final PrimitiveArray arg1);
	public PrimitiveArray rowsum(final PrimitiveArray arg1);
	public PrimitiveArray rowmean(final PrimitiveArray arg1);
}
