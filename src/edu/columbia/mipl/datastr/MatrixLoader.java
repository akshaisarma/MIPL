/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixLoader.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Matrix Loader Interface
 *
 */

package edu.columbia.mipl.datastr;

/* If you want to make a factory pattern, do so */
public abstract class MatrixLoader {
	/**
	 * Loads a matrix
	 *
	 * @param file name of the file
	 * @return PrimitiveMatrix loaded matrix
	 */

	public abstract PrimitiveArray loadMatrix(String file);
	public abstract void saveMatrix(String file, PrimitiveMatrix matrix);

	abstract String getLoaderName();
}
