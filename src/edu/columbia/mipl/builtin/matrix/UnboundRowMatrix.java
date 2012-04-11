/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundRowMatrix.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Built-in Unbound Rows Matrix 
 */
package edu.columbia.mipl.builtin.matrix;

import java.util.*;
import java.lang.reflect.*;

import edu.columbia.mipl.datastr.*;

public class UnboundRowMatrix extends UnboundMatrix {

	public UnboundRowMatrix(PrimitiveMatrix<Double> m) {
		if (m.getRow() != 1)
			assert (false);

		setData(m.getData());
	}

	public int getCol() {
		return getData().getCol();
	}

	public  String getName() {
		return "urow";
	}

	public Double getValue(int row, int col) {
		return (Double) getData().getValue(0, col);
	}
}
