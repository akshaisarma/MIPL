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

	public Fact(List<String> names, Term jobcall) {
		this.names = names;
		this.name = jobcall.getName();
		this.terms = jobcall.getArguments();

		type = Type.MATRIXASFACTS;

		addAll(this.terms);
	}

	public Term getTerm() {
		return term;
	}

	public String getName() {
		return name;
	}

	public List<String> getNames() {
		return names;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public Type getType() {
		return type;
	}
}
