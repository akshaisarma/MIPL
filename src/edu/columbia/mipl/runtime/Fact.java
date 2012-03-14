/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Fact.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Fact
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.datastr.*;

public class Fact extends Knowledge  {
	public enum Type {
		FACT,
		MATRIXASFACTS,
	};
	Type type;

	Term term;
	String name;
	List<String> names;
	List<Term> terms;

	public Fact(Term term) {
		assert (term.getType() == Term.Type.TERM);

		this.term = term;
		name = term.getName();
		type = Type.FACT;

		add(term);
	}

	public Fact(String name, List<String> names, List<Term> terms) {
		this.name = name;
		this.names = names;
		this.terms = terms;

		type = Type.MATRIXASFACTS;

		addAll(terms);
	}

	public Term getTerm() {
		return term;
	}

	public String getName() {
		return name;
	}
}
