/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundOnes.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Built-in Unbound Matrix Ones
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

public class UnboundOnes extends UnboundMatrix {
	public  String getName() {
		return "ones";
	}

	public Double getValue(int row, int col) {
		return 1.0;
	}
}
