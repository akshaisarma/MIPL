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
	public PrimitiveMatrix add(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix add(final PrimitiveMatrix arg1, double arg2);
	public PrimitiveMatrix sub(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix sub(final PrimitiveMatrix arg1, double arg2);

	public PrimitiveMatrix cellmult(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix mult(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix mult(final PrimitiveMatrix arg1, final double arg2);

	public PrimitiveMatrix celldiv(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix div(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public PrimitiveMatrix div(final PrimitiveMatrix arg1, final double arg2);

	public void assign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void assign(PrimitiveMatrix arg1, double arg2);
	public void addassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void addassign(PrimitiveMatrix arg1, double arg2);
	public void subassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void subassign(PrimitiveMatrix arg1, double arg2);

	public void cellmultassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void multassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void multassign(PrimitiveMatrix arg1, double arg2);
	public void celldivassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void divassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	public void divassign(PrimitiveMatrix arg1, double arg2);

	public PrimitiveMatrix transpose(final PrimitiveMatrix arg1);
	public PrimitiveMatrix inverse(final PrimitiveMatrix arg1);

	public PrimitiveMatrix mod(final PrimitiveMatrix arg1, double arg2);
	public PrimitiveMatrix mod(final PrimitiveMatrix arg1, PrimitiveMatrix arg2);
	public double sum(final PrimitiveMatrix arg1);
	public double mean(final PrimitiveMatrix arg1);
	public PrimitiveMatrix rowsum(final PrimitiveMatrix arg1);
	public PrimitiveMatrix rowmean(final PrimitiveMatrix arg1);

	public PrimitiveMatrix abs(final PrimitiveMatrix arg1);
}
