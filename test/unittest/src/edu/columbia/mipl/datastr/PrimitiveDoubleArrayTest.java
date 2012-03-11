package edu.columbia.mipl.datastr;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.matops.*;

public class PrimitiveDoubleArrayTest extends TestCase {
	double data3x3_1[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
	double data1x3_1[] = {1, 2, 3};
	double data3x1_1[] = {1, 2, 3};
	double data1x1_1[] = {14};

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}
	@Override
	protected void setUp() {
	}

	public void testMatex_3x3() {
		boolean result = true;
		final PrimitiveDoubleArray mat3x3_1 = new PrimitiveDoubleArray(3, 3, data3x3_1);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data1x3_1[i*3 + j] != (Double)mat3x3_1.getValue(i, j))
					result = false;
			}
		}
		assertTrue(result == true);
	}

	public void testMatex_1x3() {
		boolean result = true;
		final PrimitiveDoubleArray mat1x3_1 = new PrimitiveDoubleArray(1, 3, data1x3_1);
		for (int i = 0; i < 3; i++) {
			if (data1x3_1[i] != (Double)mat1x3_1.getValue(0, i))
				result = false;
		}
		assertTrue(result == true);
	}

	public void testMatrix_3x1() {
		boolean result = true;
		final PrimitiveDoubleArray mat3x1_1 = new PrimitiveDoubleArray(3, 1, data3x1_1);
		for (int i = 0; i < 3; i++) {
			if (data3x1_1[i] != (Double)mat3x1_1.getValue(i, 0))
				result = false;
		}
		assertTrue(result == true);
	}

	public void testMatrix_1x1() {
		final PrimitiveDoubleArray mat1x1_1 = new PrimitiveDoubleArray(1, 1, data1x1_1);
		double value = (Double) mat1x1_1.getValue(0,0);
		assertTrue(data1x1_1[0] == value);
	}
	public static Test suite() {
		return new TestSuite(PrimitiveDoubleArrayTest.class);
	}
}
