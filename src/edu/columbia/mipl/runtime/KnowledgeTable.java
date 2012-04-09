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

		if (!mergeMatrix(prev, value))
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

	private static PrimitiveMatrix<Double> getMatrix(Knowledge knowledge) {
		if (!(knowledge instanceof Fact))
			return null;

		Fact fact = (Fact) knowledge;
		Term term = fact.getTerm();
		if (term.getType() != Term.Type.MATRIX)
			return null;

		return term.getMatrix();
	}
	
	private static boolean mergeMatrix(List<Knowledge> list, Knowledge knowledge) {
		PrimitiveMatrix<Double> matrix = getMatrix(knowledge);
		if (matrix == null)
			return false;

		for (Knowledge k : list) {
			PrimitiveMatrix<Double> m = getMatrix(k);
			if (m == null)
				continue;

			if (matrix.getCol() != m.getCol())
				continue;

			m.mergeVertically(matrix);
			return true;
		}

		return false;
	}
}
