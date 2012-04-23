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
	static int maximumDimension = 1000;
	static int maximumValue = 0xFFFF;
	private int intMatrixRows;
	private int intMatrixCols;
	private int doubleMatrixRows;
	private int doubleMatrixCols;

	private int[] intMatrix;
	private double[] doubleMatrix;

	public static void main(String args[]) {
		junit.textui.TestRunner.run (suite());
	}

	@Override
	protected void setUp() {
		primOp = new PrimitiveOperations();
		int length;
		Random randomGenerator = new Random();

		this.intMatrixRows = randomGenerator.nextInt(maximumDimension) + 1;
		this.intMatrixCols = randomGenerator.nextInt(maximumDimension) + 1;
		length = intMatrixRows*intMatrixCols;
		this.intMatrix = new int[length];
		for (int i = 0; i < length; i++)
			intMatrix[i] = randomGenerator.nextInt(maximumValue);

		this.doubleMatrixRows = randomGenerator.nextInt(maximumDimension) + 1;
		this.doubleMatrixCols = randomGenerator.nextInt(maximumDimension) + 1;
		length = doubleMatrixRows*doubleMatrixCols;
		this.doubleMatrix = new double[length];
		for (int i = 0; i < length; i++)
			doubleMatrix[i] = randomGenerator.nextDouble();
	}

	public static Test suite() {
		return new TestSuite(PrimitiveOperationsTest.class);
	}

	public void testAssign() {

	}

	public void testOr() {

	}

	public void testAnd() {

	}

	public void testEqual() {

	}

	public void testNotEqual() {

	}

	public void testLessThan() {

	}

	public void testGreaterThan() {

	}

	public void testLessThanEqual() {

	}

	public void testGreaterThanEqual() {

	}

	public void testAddition() {

	}

	public void testSubtraction() {

	}

	public void testMultiplication() {

	}

	public void testDivisoin() {

	}

	public void testModulus() {

	}

	public void absoluteValue() {

	}

}
