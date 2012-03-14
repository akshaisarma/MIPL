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
	public enum Method {
		PRE,
		IN,
		POST,
	};

	public void traverse(Traverser traverser) {
		traverse(traverser, Method.POST);
	}

	public void traverse(Traverser traverser, Method method) {
		switch (method) {
			case PRE:
				preTraverse(traverser, true);
				return;
			case IN:
				inTraverse(traverser, true);
				return;
			case POST:
				postTraverse(traverser, true);
				return;
		}
	}

	public void preTraverse(Traverser traverser) {
		preTraverse(traverser, true);
	}

	void preTraverse(Traverser traverser, boolean root) {
		traverser.reach(this);
		for (Traversable t : this) {
			t.postTraverse(traverser, false);
		}
		if (root)
			traverser.finish();

	}


	public void inTraverse(Traverser traverser) {
		inTraverse(traverser, true);
	}

	void inTraverse(Traverser traverser, boolean root) {
		int i = size();
		for (Traversable t : this) {
			t.postTraverse(traverser, false);
			if (--i > 0)
				traverser.reach(this);
		}
		if (root)
			traverser.finish();
	}

	public void postTraverse(Traverser traverser) {
		postTraverse(traverser, true);
	}

	void postTraverse(Traverser traverser, boolean root) {
		for (Traversable t : this) {
			t.postTraverse(traverser, false);
		}
		traverser.reach(this);
		if (root)
			traverser.finish();
	}

}
