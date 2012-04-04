/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: UnboundRand.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Built-in Unbound Matrix Rand
 */
package edu.columbia.mipl.builtin;

import java.util.*;
import java.lang.reflect.*;

public class UnboundRand extends UnboundMatrix {
	static Random randInstance = new Random();

	public  String getName() {
		return "rand";
	}

	public Double getValue(int row, int col) {
		return randInstance.nextDouble();
	}
}
