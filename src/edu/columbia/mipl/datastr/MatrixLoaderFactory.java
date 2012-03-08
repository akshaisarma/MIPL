/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: MatrixLoaderFactory.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Matrix LoaderFactory Interface
 *
 */

package edu.columbia.mipl.datastr;

import java.util.*;

/* Singleton MatrixLoaderFactory Class */
public class MatrixLoaderFactory {
	Map<String, MatrixLoader> loaderMap;

	static MatrixLoaderFactory instance;

	static {
		instance = new MatrixLoaderFactory();
	}

	public static MatrixLoaderFactory getInstance() {
		return instance;
	}

	private MatrixLoaderFactory() {
		loaderMap = new HashMap();
	}

	void installMatrixLoader(String loader, MatrixLoader instance) {
		loaderMap.put(loader.toLowerCase(), instance);
	}

	MatrixLoader getMatrixLoader(String loader) {
		return loaderMap.get(loader.toLowerCase());
	}
}
