/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundEye.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Built-in Unbound Matrix Indentity Matrix
 */
package edu.columbia.mipl.builtin.matrix;

import java.util.*;
import java.lang.reflect.*;

public class UnboundEye extends UnboundMatrix {
	public  String getName() {
		return "eye";
	}

	public Double getValue(int row, int col) {
		if (rowOffset + row == colOffset + col)
			return 1.0;
		return 0.0;
	}
}
