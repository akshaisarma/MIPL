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

import edu.columbia.mipl.ds.PrimitiveMatrix;

/* If you want to make a factory pattern, do so */
public abstract class MatrixLoader {
	/**
	 * Loads a matrix
	 *
	 * @param file name of the file
	 * @return PrimitiveMatrix loaded matrix
	 */

	MatrixLoader() {
		MatrixLoaderFactory.getInstance().installMatrixLoader(getLoaderName(), this);
	}

	abstract PrimitiveMatrix loadMatrix(String file);
	abstract void saveMatrix(String file, PrimitiveMatrix matrix);

	abstract String getLoaderName();
}
