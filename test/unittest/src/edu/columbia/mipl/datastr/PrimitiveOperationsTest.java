/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveOperationsTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Tests for Primitive Operations
 */

package edu.columbia.mipl.datastr;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.columbia.mipl.datastr.*;
import edu.columbia.mipl.matops.*;

public class PrimitiveOperationsTest extends TestCase {

	protected PrimitiveOperations primOp;
	static int maximumDimension = 100;
	private int doubleMatrixRows;
	private int doubleMatrixCols;

	private double[] doubleMatrix;

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}

	@Override
	protected void setUp() {
		primOp = new PrimitiveOperations();
		int length;
/*
		Random randomGenerator = new Random();

		this.doubleMatrixRows = randomGenerator.nextInt(maximumDimension) + 1;
		this.doubleMatrixCols = randomGenerator.nextInt(maximumDimension) + 1;
		length = doubleMatrixRows*doubleMatrixCols;
		this.doubleMatrix = new double[length];
		for (int i = 0; i < length; i++)
			doubleMatrix[i] = randomGenerator.nextDouble();
*/
	}

	public static Test suite() {
		return new TestSuite(PrimitiveOperationsTest.class);
	}
/*
	public void testAssign() {
		
		PrimitiveDoubleArray d1 = new PrimitiveDoubleArray(doubleMatrixRows, doubleMatrixCols, doubleMatrix);
        PrimitiveMatrix m1 = new PrimitiveMatrix(d1);

		PrimitiveMatrix res = (PrimitiveMatrix) primOp.assign(null, m1);
		assertTrue(res.getData().equalsSemantically(m1.getData()));
		
	}
*/
	public void testOr() {
		PrimitiveBool e1 = new PrimitiveBool(true);
		PrimitiveBool e2 = new PrimitiveBool(false);
		PrimitiveBool res = primOp.or(e1, e2);
		assertTrue(res.getData());
	}

	public void testAnd() {
		PrimitiveBool e1 = new PrimitiveBool(true);
		PrimitiveBool e2 = new PrimitiveBool(false);
		PrimitiveBool res = primOp.and(e1, e2);
		assertFalse(res.getData());
	}

	public void testEqual() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(42));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(0));
		assertFalse(primOp.eq(p1, p2).getData());
		assertTrue(primOp.eq(p1,p1).getData());
	}

	public void testNotEqual() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(42));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(0));
		assertTrue(primOp.ne(p1, p2).getData());
		assertFalse(primOp.ne(p1, p1).getData());
	}

	public void testLessThan() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(0));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(1));
		assertTrue(primOp.lt(p1, p2).getData());
		assertFalse(primOp.lt(p2, p1).getData());
	}

	public void testGreaterThan() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(42));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(0));
		assertTrue(primOp.gt(p1, p2).getData());
		assertFalse(primOp.gt(p2, p1).getData());
		assertFalse(primOp.gt(p2, p2).getData());
	}

	public void testLessThanEqual() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(10));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(23));
		assertTrue(primOp.le(p1, p2).getData());
		assertTrue(primOp.le(p1, p1).getData());
	}

	public void testGreaterThanEqual() {
		PrimitiveDouble p1 = new PrimitiveDouble(new Double(10));
		PrimitiveDouble p2 = new PrimitiveDouble(new Double(23));
		assertTrue(primOp.ge(p2, p1).getData());
		assertTrue(primOp.ge(p1, p1).getData());
	}
}
