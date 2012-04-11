/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveIntArrayTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Primitive Int Array Test
 *				Some code snippets taken from
 *				PrimitiveDoubleArrayTest.java				
 */

package edu.columbia.mipl.datastr;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.matops.*;

public class PrimitiveIntArrayTest extends TestCase {
	int data[] = {1, 2, 3, 1, 2, 3, 1, 2, 3};
	static int maximumDimension = 1000;
	static int maximumValue = 0xFFFF;

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}

	@Override
	protected void setUp() {
	}

	public void testRandomMatrix() {
		boolean result = true;
		Random randomGenerator = new Random();
		int rows = randomGenerator.nextInt(maximumDimension) + 1;
		int cols = randomGenerator.nextInt(maximumDimension) + 1;
		int length = rows*cols;
		int randomMat[] = new int[length];
		for (int i = 0; i < length; i++) {
			randomMat[i] = randomGenerator.nextInt(maximumValue);
		}

		PrimitiveIntArray matUxU = new PrimitiveIntArray(rows, cols, randomMat);

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				result &= data[i*cols + j] == (Integer) matUxU.getValue(i, j);
		assertTrue(result == true);
	}

	public void testMatrix_3x3() {
		boolean result = true;
		PrimitiveIntArray mat3x3 = new PrimitiveIntArray(3, 3, data);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i*3 + j] != (Integer) mat3x3.getValue(i, j))
					result = false;
			}
		}
		assertTrue(result == true);
	}

	public void testMatrix_9x1() {
		boolean result = true;
		PrimitiveIntArray mat9x1 = new PrimitiveIntArray(9, 1, data);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 1; j++) {
				if (data[i*3 + j] != (Integer) mat9x1.getValue(i, j))
					result = false;
			}
		}
		assertTrue(result == true);
	}

	public void testMatrix_1x9() {
		boolean result = true;
		PrimitiveIntArray mat1x9 = new PrimitiveIntArray(1, 9, data);
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				if (data[i*3 + j] != (Integer) mat1x9.getValue(i, j))
					result = false;
			}
		}
		assertTrue(result == true);
	}

	public static Test suite() {
		return new TestSuite(PrimitiveDoubleArrayTest.class);
	}
}
