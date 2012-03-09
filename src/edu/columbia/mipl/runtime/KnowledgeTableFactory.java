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

public final class KnowledgeTableFactory {
	static KnowledgeTable kt;
	static {
		kt = new KnowledgeTable();
	}

	public static KnowledgeTable getKnowledgeTable() {
		return kt;
	}

	private KnowledgeTableFactory() {
	}
}
