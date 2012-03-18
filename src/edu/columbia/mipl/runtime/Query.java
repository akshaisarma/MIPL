/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Query.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Query
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.datastr.*;

public class Query extends Command {
	Term term;

	public Query(Term term) {
		assert (term.getType() == Term.Type.TERM);

		this.term = term;

		add(term);
	}

	public Term getTerm() {
		return term;
	}

	public String getName() {
		return term.getName();
	}
}
