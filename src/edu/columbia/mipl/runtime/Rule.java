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
	Term target;
	Term source;

	public Rule(Term target, Term source) {
		Knowledge know;

		if (target.getType() != Term.Type.TERM_TYPE_TERM)
			/* throw new InvalidRuleDefinitionException() */;
		know = KnowledgeTableFactory.getKnowledgeTable().get(target.getName());
		if (know == null) {
			this.target = target;
			this.source = source;
			KnowledgeTableFactory.getKnowledgeTable().put(target.getName(), this);
			return;
		}
		/* throw new RuleRedefineException() */;
	}

	Term getTarget() {
		return target;
	}

	Term getSource() {
		return source;
	}
}
