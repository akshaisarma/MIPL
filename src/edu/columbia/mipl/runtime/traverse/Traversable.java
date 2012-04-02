/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Traversable.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Traversable
 */
package edu.columbia.mipl.runtime.traverse;

import java.io.*;
import java.util.*;

public class Traversable extends ArrayList<Traversable> implements Serializable {
	boolean immediate;

	public boolean traverse(Traverser traverser) {
		return traverse(traverser, false);
	}

	public boolean traverse(Traverser traverser, boolean immediate) {
		return traverse(traverser, traverser.getMethod(), immediate);
	}

	public boolean traverse(Traverser traverser, Traverser.Method method, boolean immediate) {
		this.immediate = immediate;

		switch (method) {
			case PRE:
				return preTraverse(traverser);
			case IN:
				return inTraverse(traverser);
			case POST:
				return postTraverse(traverser);
		}
		// TODO: print error
		return false;
	}

	public boolean preTraverse(Traverser traverser) {
		boolean result = true;
		result &= preTraverseInternal(traverser);
		if (!immediate)
			traverser.finish();
		return result;
	}

	boolean preTraverseInternal(Traverser traverser) {
		int i;
		boolean result = true;
		result &= traverser.reach(this);
		for (i = size() - 1; i >= 0; i--) {
			result &= get(i).preTraverseInternal(traverser);
		}
		return result;
	}


	public boolean inTraverse(Traverser traverser) {
		boolean result = true;
		result &= inTraverseInternal(traverser);
		if (!immediate)
			traverser.finish();
		return result;
	}

	boolean inTraverseInternal(Traverser traverser) {
		int i;
		boolean result = true;
		for (i = size() - 1; i >= 0; i--) {
			result &= get(i).inTraverseInternal(traverser);
			if (i > 0)
				result &= traverser.reach(this);
		}
		return result;
	}

	public boolean postTraverse(Traverser traverser) {
		boolean result = true;
		result &= postTraverseInternal(traverser);
		if (!immediate) {
			traverser.finish();
		}
		return result;
	}

	boolean postTraverseInternal(Traverser traverser) {
		int i;
		boolean result = true;
		for (i = size() - 1; i >= 0; i--) {
			result &= get(i).postTraverseInternal(traverser);
		}
		result &= traverser.reach(this);
		return result;
	}

	public int hashCode() {
		return System.identityHashCode(this);
	}

}
