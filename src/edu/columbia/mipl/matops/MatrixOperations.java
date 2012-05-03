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

/**
 * Abstracts the matrix primitive operations used in MIPL.
 * To provide a specific implementations, inherit this class.
 */
public interface MatrixOperations {
	/**
	 * Adds two matrices
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix add(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Adds a value to a matrix
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 * @return a result matrix
	 */
	public PrimitiveMatrix add(final PrimitiveMatrix arg1, double arg2);
	/**
	 * Subtracts two matrices
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix sub(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Subtracts a value from a matrix
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 * @return a result matrix
	 */
	public PrimitiveMatrix sub(final PrimitiveMatrix arg1, double arg2);

	/**
	 * Multiplies a matrix with another matrix cell-wisely
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix cellmult(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Multiplies a matrix with another matrix
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix mult(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Multiplies a matrix with a value
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 * @return a result matrix
	 */
	public PrimitiveMatrix mult(final PrimitiveMatrix arg1, final double arg2);

	/**
	 * Divides a matrix with another matrix cell-wisely
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix celldiv(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Divides a matrix with another matrix
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 * @return a result matrix
	 */
	public PrimitiveMatrix div(final PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Divides a matrix with a value
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 * @return a result matrix
	 */
	public PrimitiveMatrix div(final PrimitiveMatrix arg1, final double arg2);

	/**
	 * Copies the values of a matrix to another one
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void assign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Fills a matrix with a value
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public void assign(PrimitiveMatrix arg1, double arg2);
	/**
	 * Adds the matrix arg2 to the matrix arg1
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void addassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Adds a value to a matrix
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public void addassign(PrimitiveMatrix arg1, double arg2);
	/**
	 * Subtracts the matrix arg2 to the matrix arg1
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void subassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Subtracts a value from a matrix
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public void subassign(PrimitiveMatrix arg1, double arg2);

	/**
	 * Multiplies the matrix arg1 with the matrix arg2 cell-wisely
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void cellmultassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Multiplies the matrix arg1 with the matrix arg2
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void multassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Multiplies the matrix arg1 with a value arg2
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public void multassign(PrimitiveMatrix arg1, double arg2);
	/**
	 * Divides the matrix arg1 with the matrix arg2 cell-wisely
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void celldivassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Divides the matrix arg1 with the matrix arg2
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public void divassign(PrimitiveMatrix arg1, final PrimitiveMatrix arg2);
	/**
	 * Divides the matrix arg1 with a value arg2
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public void divassign(PrimitiveMatrix arg1, double arg2);

	/**
	 * Transposes a matrix
	 * @param arg1 a matrix
	 * @return the transposed matrix
	 */
	public PrimitiveMatrix transpose(final PrimitiveMatrix arg1);
	/**
	 * Inverses a matrix
	 * @param arg1 a matrix
	 * @return the inversed matrix
	 */
	public PrimitiveMatrix inverse(final PrimitiveMatrix arg1);

	/**
	 * Returns the matrix which has the modular values from the division of arg1 by arg2
	 * @param arg1 a matrix
	 * @param arg2 a numeric value
	 */
	public PrimitiveMatrix mod(final PrimitiveMatrix arg1, double arg2);
	/**
	 * Returns the matrix which has the modular values from the division of arg1 by arg2
	 * @param arg1 a matrix
	 * @param arg2 a matrix
	 */
	public PrimitiveMatrix mod(final PrimitiveMatrix arg1, PrimitiveMatrix arg2);
	/**
	 * Returns the summation value of the matrix
	 * @param arg1 a matrix
	 * @return the sum
	 */
	public double sum(final PrimitiveMatrix arg1);
	/**
	 * Returns the mean value of the matrix
	 * @param arg1 a matrix
	 * @return the mean
	 */
	public double mean(final PrimitiveMatrix arg1);
	/**
	 * Returns a nx1 matrix each row of which contains the sum of each row of the matrix arg1
	 * @param arg1 a matrix
	 * @return the rowsum matrix
	 */
	public PrimitiveMatrix rowsum(final PrimitiveMatrix arg1);
	/**
	 * Returns a nx1 matrix each row of which contains the mean of each row of the matrix arg1
	 * @param arg1 a matrix
	 * @return the rowmean matrix
	 */
	public PrimitiveMatrix rowmean(final PrimitiveMatrix arg1);
	/**
	 * Returns a nx1 matrix each column of which contains the sum of each column of the matrix arg1
	 * @param arg1 a matrix
	 * @return the colsum matrix
	 */
	public PrimitiveMatrix colsum(final PrimitiveMatrix arg1);
	/**
	 * Returns a nx1 matrix each column of which contains the mean of each column of the matrix arg1
	 * @param arg1 a matrix
	 * @return the colmean matrix
	 */
	public PrimitiveMatrix colmean(final PrimitiveMatrix arg1);

	/**
	 * Returns a matrix which contains absolute values of the matrix arg1
	 * @param arg1 a matrix
	 * @return the absolute matrix
	 */
	public PrimitiveMatrix abs(final PrimitiveMatrix arg1);
}
