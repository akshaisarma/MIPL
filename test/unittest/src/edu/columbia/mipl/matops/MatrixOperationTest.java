package edu.columbia.mipl.matops;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.matops.*;

public class MatrixOperationTest extends TestCase {
	double data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
	double data3x3_2[] = {2, 4, 6, 2, 4, 6, 2, 4, 6};
	double data3x3_add_1_2[] = {3, 6, 9, 3, 6, 9, 3, 6, 9};
	double data3x3_mult_1_2[] = {12, 24, 36, 12, 24, 36, 12, 24, 36};

	double data1x3_1[] = {1, 2, 3};
	double data3x1_2[] = {1, 2, 3};
	double data1x1_mult_1_2[] = {14};
	protected DefaultMatrixOperations matOpObj;

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}
	@Override
	protected void setUp() {
		matOpObj = new DefaultMatrixOperations();
	}

	public void testMaxtirSame() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		boolean result = matOpObj.checkMatrixSame((PrimitiveArray)mat3x3_1, 
							  (PrimitiveArray)mat3x3_1);
		assertTrue(result == true);
	}

	public void testMatrixAdd() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		final PrimitiveDoubleArray mat3x3_3_add_1_2 = new PrimitiveDoubleArray(3, 3, data3x3_add_1_2);
		PrimitiveArray mat = matOpObj.add((PrimitiveArray)mat3x3_1, 
						  (PrimitiveArray)mat3x3_2);
		boolean result = matOpObj.checkMatrixSame((PrimitiveArray)mat, 
							  (PrimitiveArray)mat3x3_3_add_1_2);
		assertTrue(result == true);
	}

	public void testMatrixMult_3x3() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		final PrimitiveDoubleArray mat3x3_3_mult_1_2 = new PrimitiveDoubleArray(3, 3, data3x3_mult_1_2);

		PrimitiveArray mat = matOpObj.mult((PrimitiveArray)mat3x3_1, 
						   (PrimitiveArray)mat3x3_2);
		boolean result = matOpObj.checkMatrixSame((PrimitiveArray)mat, 
							  (PrimitiveArray)mat3x3_3_mult_1_2);
		assertTrue(result == true);
	}

	public void testMatrixMult_1x3_3x1_1x1() {
		final PrimitiveDoubleArray mat1x3_1 = new PrimitiveDoubleArray(1, 3, data1x3_1);
		final PrimitiveDoubleArray mat3x1_2 = new PrimitiveDoubleArray(3, 1, data3x1_2);
		final PrimitiveDoubleArray mat1x1_3_mult_1_2 = new PrimitiveDoubleArray(3, 3, data1x1_mult_1_2);

		PrimitiveArray mat = matOpObj.mult((PrimitiveArray)mat1x3_1, 
						   (PrimitiveArray)mat3x1_2);
		boolean result = matOpObj.checkMatrixSame((PrimitiveArray)mat, 
							  (PrimitiveArray)mat1x1_3_mult_1_2);
		assertTrue(result == true);
	}

	public static Test suite() {
		return new TestSuite(MatrixOperationTest.class);
	}
}
