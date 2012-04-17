/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: VariableStack.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: VariableStack
 */
package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.runtime.*;

class VariableGroup extends ArrayList<Term> {
	Term value;

	VariableGroup() {
	}

	VariableGroup(Term value) {
		this.value = value;
	}

	Term getValue() {
		return value;
	}

	void setValue(Term value) {
		this.value = value;
	}
}

public class VariableStack extends HashMap<Term, VariableGroup> {
	VariableStack() {
	}

	VariableStack(VariableStack vs) {
		super(vs);
	}

	Term put(Term key, Term value) {
		if (key.getName().equals("_"))
			return value;

		Term oldValue = null;
		VariableGroup group = super.get(key);
		if (group == null)
			group = new VariableGroup();

		oldValue = group.getValue();
		group.setValue(value);
		group.add(key);
		super.put(key, group);

		return oldValue;
	}

	Term get(Term key) {
		VariableGroup group = super.get(key);
		if (group == null)
			return null;

		return group.getValue();
	}

	boolean group(Term key1, Term key2) {
		if (key1.getName().equals("_"))
			return true;
		if (key2.getName().equals("_"))
			return true;

		VariableGroup group1 = super.get(key1);
		VariableGroup group2 = super.get(key2);
		if (group1 != null && group2 != null) {
			if (group1.getValue() != null && group2.getValue() != null)
				return false;

			group1.addAll(group2);
			if (group2.getValue() != null)
				group1.setValue(group2.getValue());
			for (Term t : group2)
				super.put(t, group1);
		}
		else if (group1 == null && group2 == null) {
			group1 = new VariableGroup();
			group1.add(key1);
			group1.add(key2);
			super.put(key1, group1);
			super.put(key2, group1);
		}
		else if (group1 == null) {
			group2.add(key1);
			super.put(key1, group2);
		}
		else { /* group2 == null */
			group1.add(key2);
			super.put(key2, group1);
		}

		return true;
	}
}
