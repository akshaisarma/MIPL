/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: ArrayIndex.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: ArrayIndex
 */
package edu.columbia.mipl.runtime;

public class ArrayIndex {
	boolean singleIndex;
	boolean toEnd;

	long index1;
	long index2;

	public ArrayIndex(long index) {
		singleIndex = true;
		index1 = index;
	}

	public ArrayIndex(long fromIndex, boolean toEnd) {
		assert (toEnd);

		this.toEnd = true;
		index1 = fromIndex;
	}

	public ArrayIndex(long fromIndex, long toIndex) {
		this.toEnd = false;
		index1 = fromIndex;
		index2 = toIndex;
	}

	public long getFromIndex() {
		return index1;
	}

	public void setLastIndex(long lastIndex) {
		if (toEnd)
			index2 = lastIndex;
		toEnd = false;
	}

	public long getToIndex() {
		if (toEnd)
			return -1;
		return index2;
	}
}
