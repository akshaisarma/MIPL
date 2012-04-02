/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Rule.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Rule
 */
package edu.columbia.mipl.runtime;

import java.util.*;

public class Rule extends Knowledge {
	Term term;
	Term source;

	public Rule(Term term, Term source) {
		assert (term.getType() == Term.Type.TERM);

		this.term = term;
		this.source = source;

		add(term);
		add(source);
	}

	public Term getTerm() {
		return term;
	}

	public Term getSource() {
		return source;
	}

	public String getName() {
		return term.getName();
	}
}
