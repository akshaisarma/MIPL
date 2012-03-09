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

public class Fact extends Knowledge  {
	Term term;

	public Fact(Term term) {

		if (term.getType() != Term.Type.TERM_TYPE_TERM)
			/* throw new InvalidFactDefinitionException() */;

		this.term = term;
		KnowledgeTableFactory.getKnowledgeTable().put(term.getName(), this);
	}
}
