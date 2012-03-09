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
	Term term;

	public Fact(Term term) {
		Knowledge know;
		Term prev;
		Term matrix;
		int i;

		if (term.getType() != Term.Type.TERM)
			/* throw new InvalidFactDefinitionException() */;

		know = KnowledgeTableFactory.getKnowledgeTable().get(term.getName());
		if (know == null) {
			this.term = term;
			KnowledgeTableFactory.getKnowledgeTable().put(term.getName(), this);
			return;
		}

		if (know instanceof Rule) {
			/* throw new TermRedefineExistingRuleException() */;
		}

		prev = ((Fact) know).getTerm();
		if (prev.containVariables() || prev.getArguments().size() != term.getArguments().size()) {
			/* throw new UnmergeableFactsException() */;
		}

		matrix = new Term(Term.Type.MATRIX, term.getName(), new PrimitiveDoubleArray(2, prev.getArguments().size()));
		i = 0;
		for (Term t : prev.getArguments()) {
			if (t.getType() != Term.Type.NUMBER)
				/* throw new UnmergeableFactsException() */;
			matrix.getMatrix().setValue(0, i, t.getValue());
		}
		i = 0;
		for (Term t : term.getArguments()) {
			if (t.getType() != Term.Type.NUMBER)
				/* throw new UnmergeableFactsException() */;
			matrix.getMatrix().setValue(1, i, t.getValue());
		}
		this.term = matrix;
		KnowledgeTableFactory.getKnowledgeTable().put(term.getName(), this);
	}

	Term getTerm() {
		return term;
	}
}
