/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Traversable.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Traversable
 */
package edu.columbia.mipl.runtime.traverse;

import java.util.*;

public class Traversable extends ArrayList<Traversable> {
	enum TraverseType {
		PRE,
		IN,
		POST,
	};
	private final TraverseType defaultTraverse = TraverseType.POST;

	public void traverse(Traverser traverser) {
		switch (defaultTraverse) {
			case PRE:
				preTraverse(traverser);
				return;
			case IN:
				inTraverse(traverser);
				return;
			case POST:
				postTraverse(traverser);
				return;
		}
	}

	public void preTraverse(Traverser traverser) {
		traverser.reach(this);
		for (Traversable t : this) {
			t.postTraverse(traverser);
		}
	}


	public void inTraverse(Traverser traverser) {
		int i = size();
		for (Traversable t : this) {
			t.postTraverse(traverser);
			if (--i > 0)
				traverser.reach(this);
		}
	}

	public void postTraverse(Traverser traverser) {
		for (Traversable t : this) {
			t.postTraverse(traverser);
		}
		traverser.reach(this);
	}

}
