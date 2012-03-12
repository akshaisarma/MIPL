package edu.columbia.mipl.datastr;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.matops.*;

public class MatrixLoadTest extends TestCase {
	double data3x3_1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	final String TEST_DIR = "test/matrix/";

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}
	@Override
	protected void setUp() {
	}

	public void testMatrixLoad() {
		boolean result = true;
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		MatrixLoaderFactory.getMatrixLoader("csv").loadMatrix(TEST_DIR + "test_matrix.csv");
		final PrimitiveArray array = MatrixLoaderFactory.getMatrixLoader("csv").loadMatrix(TEST_DIR + "test_matrix.csv").getData();

		assert (array instanceof PrimitiveDoubleArray);

		final PrimitiveDoubleArray mat3x3_2 = (PrimitiveDoubleArray) array;

		mat3x3_1.printMatrix();
		mat3x3_2.printMatrix();

		assertTrue(mat3x3_1.equalsSemantically(mat3x3_2));
	}

	public static Test suite() {
		return new TestSuite(MatrixLoadTest.class);
	}
}
