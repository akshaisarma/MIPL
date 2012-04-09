/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundZeros.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Built-in Unbound Matrix Zeros
 */
package edu.columbia.mipl.builtin.matrix;

import java.util.*;
import java.lang.reflect.*;

public class UnboundZeros extends UnboundMatrix {
	public  String getName() {
		return "zeros";
	}

	public Double getValue(int row, int col) {
		return 0.0;
	}
}
