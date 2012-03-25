/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixOperationTest.java
 * Author: Jin Hyung Park <jp2105@columbia.edu>
 * Reviewer: Young Hoon Jung <yj2244@columbia.edu>
 * Description: Matrix Operations Test Unit
 *
 */
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
	double data3x3_3[] = {2.0, 2.0, 0.0, -2.0, 1.0, 1.0, 3.0, 0.0, 1.0};
	double data3x3_add_1_2[] = {3, 6, 9, 3, 6, 9, 3, 6, 9};
	double data3x3_sub_1_2[] = {-1, -2, -3, -1, -2, -3, -1, -2, -3};
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

	public void testMatrixSame() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);

		assertTrue(mat3x3_1.equalsSemantically(mat3x3_1));
	}

	public void testMatrixAdd() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		final PrimitiveDoubleArray mat3x3_3_add_1_2 = new PrimitiveDoubleArray(3, 3, data3x3_add_1_2);
		PrimitiveArray mat = matOpObj.add((PrimitiveArray)mat3x3_1, 
						  (PrimitiveArray)mat3x3_2);

		assertTrue(mat.equalsSemantically(mat3x3_3_add_1_2));
	}

	public void testMatrixSub() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		final PrimitiveDoubleArray mat3x3_3_sub_1_2 = new PrimitiveDoubleArray(3, 3, data3x3_sub_1_2);
		PrimitiveArray mat = matOpObj.sub((PrimitiveArray)mat3x3_1, 
						  (PrimitiveArray)mat3x3_2);

		assertTrue(mat.equalsSemantically(mat3x3_3_sub_1_2));
	}

	public void testMatrixMult_3x3() {
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		final PrimitiveDoubleArray mat3x3_3_mult_1_2 = new PrimitiveDoubleArray(3, 3, data3x3_mult_1_2);

		PrimitiveArray mat = matOpObj.mult((PrimitiveArray)mat3x3_1, 
						   (PrimitiveArray)mat3x3_2);

		assertTrue(mat.equalsSemantically(mat3x3_3_mult_1_2));
	}

	public void testMatrixMult_1x3_3x1_1x1() {
		final PrimitiveDoubleArray mat1x3_1 = new PrimitiveDoubleArray(1, 3, data1x3_1);
		final PrimitiveDoubleArray mat3x1_2 = new PrimitiveDoubleArray(3, 1, data3x1_2);
		final PrimitiveDoubleArray mat1x1_3_mult_1_2 = new PrimitiveDoubleArray(1, 1, data1x1_mult_1_2);

		PrimitiveArray mat = matOpObj.mult((PrimitiveArray)mat1x3_1, 
						   (PrimitiveArray)mat3x1_2);

		assertTrue(mat.equalsSemantically(mat1x1_3_mult_1_2));
	}

	public void testMatrixMult_3x3_by_scalar() {
		final double scalar = 2;
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_2);
		PrimitiveArray mat = matOpObj.mult((PrimitiveArray)mat3x3_1, scalar);

		assertTrue(mat.equalsSemantically(mat3x3_2));
	}

	public void testMatrix_Assign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, data3x3_1);

		matOpObj.assign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_1.equalsSemantically(mat3x3_2));
	}

	public void testMatrix_addAssign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_2[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_3[] = {2, 4, 6, 2, 4, 6, 2, 4, 6};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_2);
		final PrimitiveDoubleArray mat3x3_3 = new PrimitiveDoubleArray(3, 3, copy_data3x3_3);

		matOpObj.addassign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_3.equalsSemantically(mat3x3_1));
	}

	public void testMatrix_subAssign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_2[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_3[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_2);
		final PrimitiveDoubleArray mat3x3_3 = new PrimitiveDoubleArray(3, 3, copy_data3x3_3);

		matOpObj.subassign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_3.equalsSemantically(mat3x3_1));
	}

	public void testMatrix_multAssign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_2[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_3[] = {6, 12, 18, 6, 12, 18, 6, 12, 18};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_2);
		final PrimitiveDoubleArray mat3x3_3 = new PrimitiveDoubleArray(3, 3, copy_data3x3_3);

		matOpObj.multassign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_3.equalsSemantically(mat3x3_1));
	}

	public void testMatrix_cellMultAssign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_2[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_3[] = {1, 4, 9, 1, 4, 9, 1, 4, 9};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_2);
		final PrimitiveDoubleArray mat3x3_3 = new PrimitiveDoubleArray(3, 3, copy_data3x3_3);

		matOpObj.cellmultassign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_3.equalsSemantically(mat3x3_1));
	}

	public void testMatrix_cellDivAssign() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_2[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data3x3_3[] = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_2);
		final PrimitiveDoubleArray mat3x3_3 = new PrimitiveDoubleArray(3, 3, copy_data3x3_3);

		matOpObj.celldivassign((PrimitiveArray)mat3x3_1, (PrimitiveArray)mat3x3_2);

		assertTrue(mat3x3_3.equalsSemantically(mat3x3_1));
	}

	public void testMatrix_Transpose() {
		final PrimitiveDoubleArray mat1x3_1 = new PrimitiveDoubleArray(1, 3, data1x3_1);
		final PrimitiveDoubleArray mat3x1_1 = new PrimitiveDoubleArray(3, 1, data3x1_2);
		PrimitiveArray mat = matOpObj.transpose((PrimitiveArray)mat1x3_1);

		assertTrue(mat.equalsSemantically(mat3x1_1));
	}

	public void testMatrix_Determinant() {
		double data2x2_1[] = {2, 2, 1, 3};
		final PrimitiveDoubleArray mat2x2_1 = new PrimitiveDoubleArray(2, 2, data2x2_1);

		double det = matOpObj.determinant(mat2x2_1);

		assertTrue(det == 4);
	}

	public void testMatrix_Minor() {
		double copy_data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
		double copy_data2x2_1[] = {1, 3, 1, 3};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat2x2_1 = new PrimitiveDoubleArray(2, 2, copy_data2x2_1);

		/* remove 2nd row and 2nd col, so input row and col will be 1, 1 */
		PrimitiveArray matI = matOpObj.minor((PrimitiveArray) mat3x3_1, 1, 1);

		assertTrue(matI.equalsSemantically(mat2x2_1));
	}

	public void testMatrix_Inverse() {
		double copy_data3x3_1[] = {1, -1, 1, 0, 2, -1, 2, 3, 0};
		double i_data3x3_1[] = {1, 0, 0, 0, 1, 0, 0, 0, 1};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray i_mat3x3 = new PrimitiveDoubleArray(3, 3, i_data3x3_1);

		PrimitiveArray matI = matOpObj.inverse((PrimitiveArray) mat3x3_1);
		PrimitiveArray matII = matOpObj.mult(matI, mat3x3_1);

		assertTrue(matII.equalsSemantically(i_mat3x3));
	}
	
	public void testMatrix_div() {
		double copy_data3x3_1[] = {1, -1, 1, 0, 2, -1, 2, 3, 0};
		double i_data3x3_1[] = {1, 0, 0, 0, 1, 0, 0, 0, 1};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray mat3x3_2 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray i_mat3x3 = new PrimitiveDoubleArray(3, 3, i_data3x3_1);

		/* mat3x3_1 and mat3x3_2 are same matrix,
		 * so, the division result will be the identity matrix i_mat3x3 */
		PrimitiveArray matI = matOpObj.div(mat3x3_1, mat3x3_2);

		assertTrue(matI.equalsSemantically(i_mat3x3));
	}

	public void testMatrix_sum() {
		double copy_data3x3_1[] = {1, -1, 1, 
					   0, 2, -1, 
					   2, 3, 0};
		double result = 7;
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);

		double compare = matOpObj.sum(mat3x3_1);

		assertTrue(compare == result);
	}

	public void testMatrix_rowsum() {
		double copy_data3x3_1[] = {1, -1, 1, 
					   0, 2, -1, 
					   2, 3, 0};
		double result_rowsum[] = {3, 4, 0};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray r_rowsum = new PrimitiveDoubleArray(1, 3, result_rowsum);

		PrimitiveArray matI = matOpObj.rowsum(mat3x3_1);

		assertTrue(r_rowsum.equalsSemantically(matI));
	}

	public void testMatrix_mean() {
		double copy_data3x3_1[] = {1, -1, 1, 
					   0, 2, -1, 
					   2, 3, 0};
		double result = 7.0/9;
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);

		double compare = matOpObj.mean(mat3x3_1);

		assertTrue(compare == result);
	}

	public void testMatrix_rowmean() {
		double copy_data3x3_1[] = {1, -1, 1, 
					   0, 2, -1, 
					   2, 3, 0};
		double result_rowmean[] = {1, 4.0/3, 0};
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, copy_data3x3_1);
		final PrimitiveDoubleArray r_rowmean = new PrimitiveDoubleArray(1, 3, result_rowmean);

		PrimitiveArray matI = matOpObj.rowmean(mat3x3_1);

		assertTrue(r_rowmean.equalsSemantically(matI));
	}

	public static Test suite() {
		return new TestSuite(MatrixOperationTest.class);
	}
}
