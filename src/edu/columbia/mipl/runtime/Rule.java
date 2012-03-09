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
		Knowledge know;

		if (term.getType() != Term.Type.TERM)
			/* throw new InvalidRuleDefinitionException() */;
		know = KnowledgeTableFactory.getKnowledgeTable().get(term.getName());
		if (know == null) {
			this.term = term;
			this.source = source;
			KnowledgeTableFactory.getKnowledgeTable().put(term.getName(), this);
			return;
		}
		/* throw new RuleRedefineException() */;
	}

	Term getTerm() {
		return term;
	}

	Term getSource() {
		return source;
	}
}
