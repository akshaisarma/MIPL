/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MapReduceMatrixOperations.java
 * Author: 
 * Reviewer: 
 * Description: Matrix Operations Implementations with OpenCL
 *
 */
package edu.columbia.mipl.matops;

import edu.columbia.mipl.datastr.*;

public class MapReduceMatrixOperations extends ClMatrixOperations {

	static {
		/* Initializations */
	}

	/* http://www.opengl.org/discussion_boards/ubbthreads.php?ubb=showflat&Number=50403 */
	/* http://gpgpu.org/developer#programming */
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

		/* Use GPGPU */

		return result;
	}

//	public PrimitiveArray add(final PrimitiveArray arg1, double arg2);

//	public PrimitiveArray sub(final PrimitiveArray arg1, final PrimitiveArray arg2);

//	public PrimitiveArray sub(final PrimitiveArray arg1, double arg2);

//	public PrimitiveArray cellmult(final PrimitiveArray arg1, final PrimitiveArray arg2);
//	public PrimitiveArray mult(final PrimitiveArray arg1, final PrimitiveArray arg2);
//	public PrimitiveArray mult(final PrimitiveArray arg1, final double arg2);

//	public PrimitiveArray celldiv(final PrimitiveArray arg1, final PrimitiveArray arg2);
//	public PrimitiveArray div(final PrimitiveArray arg1, final PrimitiveArray arg2);
//	public PrimitiveArray div(final PrimitiveArray arg1, final double arg2);

//	public void assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void addassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void addassign(PrimitiveArray arg1, double arg2);
//	public void subassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void subassign(PrimitiveArray arg1, double arg2);

//	public void cellmultassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void multassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void multassign(PrimitiveArray arg1, double arg2);
//	public void celldivassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void divassign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void divassign(PrimitiveArray arg1, double arg2);

//	public PrimitiveArray transpose(final PrimitiveArray arg1);
//	public PrimitiveArray inverse(final PrimitiveArray arg1);

//	public double sum(final PrimitiveArray arg1);
//	public double mean(final PrimitiveArray arg1);
//	public PrimitiveArray rowsum(final PrimitiveArray arg1);
//	public PrimitiveArray rowmean(final PrimitiveArray arg1);
}
