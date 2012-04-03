/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: KnowledgeTable.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: KnowledgeTable
 */
package edu.columbia.mipl.runtime;

import java.util.*;

import edu.columbia.mipl.datastr.*;

public class KnowledgeTable extends HashMap<String, List<Knowledge>> {
	public void put(String key, Knowledge value) {
		List<Knowledge> prev = super.get(key);
		if (prev == null)
			prev = new ArrayList<Knowledge>();
		prev.add(value);
		super.put(key, prev);
	}

	public PrimitiveType getFactMatrix(String key) {
		List<Knowledge> list = get(key);

		for (Knowledge knowledge : list) {
			if (!(knowledge instanceof Fact))
				continue;

			Fact fact = (Fact) knowledge;
			if (fact.getType() != Fact.Type.FACT)
				continue;

			Term term = fact.getTerm();
			if (term.getType() != Term.Type.MATRIX)
				continue;

			return term.getMatrix();
		}
		return null;
	}
}
