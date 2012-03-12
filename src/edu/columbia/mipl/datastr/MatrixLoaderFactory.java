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
public final class MatrixLoaderFactory {
	Map<String, MatrixLoader> loaderMap;

	static MatrixLoaderFactory instance;

	static {
		instance = new MatrixLoaderFactory();

		for (String name : getLoaderClassNames()) {
			installLoader(name);
		}
	}

	static List<String> getLoaderClassNames() {
		// TODO: should read from config files or directory
		List<String> names = new ArrayList();
		names.add("edu.columbia.mipl.datastr.CSVMatrixLoader");
		names.add("edu.columbia.mipl.datastr.TableMatrixLoader");

		return names;
	}

	static void installLoader(String name) {
		Class<MatrixLoader> matrixLoaderClass;
		MatrixLoader loader;
		try {
			matrixLoaderClass = (Class<MatrixLoader>) Class.forName(name);
			//matrixLoaderClass = (Class<MatrixLoader>) ClassLoader.getSystemClassLoader().loadClass(name);
			loader = matrixLoaderClass.newInstance();
			installMatrixLoader(loader.getLoaderName(), loader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MatrixLoaderFactory() {
		loaderMap = new HashMap<String, MatrixLoader>();
	}

	static void installMatrixLoader(String name, MatrixLoader loader) {
		MatrixLoaderFactory.instance.loaderMap.put(name.toLowerCase(), loader);
	}

	static MatrixLoader getMatrixLoader(String name) {
		return MatrixLoaderFactory.instance.loaderMap.get(name.toLowerCase());
	}
}
