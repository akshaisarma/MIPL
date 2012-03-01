/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixLoader.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Matrix Loader Interface
 *
 */

package edu.columbia.mipl.ds;

import PrimitiveMatrix;

public interface MatrixLoader {
	/**
	 * Loads a matrix sized row by col.
	 *
	 * If the file contains 100x200 matrix and the given row is 30 and col is 50, this function will return 12 matrices sized 30x50 and 4 matrices sized 10x50.
	 * @param file name of the file
	 * @param row the row size of the matrix to be returned
	 * @param col the col size of the matrix to be returned
	 */
	PrimitiveMatrix loadMatrix(String file);
	void saveMatrix(String file, PrimitiveMatrix matrix);
}
