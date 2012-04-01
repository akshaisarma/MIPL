/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Program.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Program
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.runtime.traverse.*;

public final class Program extends Traversable {
	List<Traverser> traversers;

	public Program() {
		traversers = new ArrayList<Traverser>();
	}

	public Program(Traverser ... traversers) {
		this.traversers = new ArrayList<Traverser>();
		for (Traverser t : traversers)
			this.traversers.add(t);
	}

	public boolean add(Command c) {
		if (c == null)
			return false;

		for (Traverser traverser : traversers) {
			if (!c.traverse(traverser, true))
				return false;
		}
		super.add(0, c);
		return true;
	}

	public void finish() {
		for (Traverser traverser : traversers)
			traverser.finish();
	}
}
