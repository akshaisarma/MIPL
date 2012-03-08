/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: ClMatrixOperations.java
 * Author: 
 * Reviewer: 
 * Description: Matrix Operations Implementations with OpenCL
 *
 */

import edu.columbia.mipl.ds.*;

public class ClMatrixOperations extends DefaultMatrixOperations {

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
//	public void add_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void add_and_assign(PrimitiveArray arg1, double arg2);
//	public void sub_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void sub_and_assign(PrimitiveArray arg1, double arg2);

//	public void cellmult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void mult_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void mult_and_assign(PrimitiveArray arg1, double arg2);
//	public void celldiv_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void div_and_assign(PrimitiveArray arg1, final PrimitiveArray arg2);
//	public void div_and_assign(PrimitiveArray arg1, double arg2);

//	public PrimitiveArray transpose(final PrimitiveArray arg1);
//	public PrimitiveArray inverse(final PrimitiveArray arg1);

//	public double sum(final PrimitiveArray arg1);
//	public double mean(final PrimitiveArray arg1);
//	public PrimitiveArray row_sum(final PrimitiveArray arg1);
//	public PrimitiveArray row_mean(final PrimitiveArray arg1);
}
