package edu.columbia.mipl.runtime.execute;

import java.util.*;

import junit.framework.TestCase;

import edu.columbia.mipl.runtime.*;

public class BindingTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(BindingTest.class);
	}
	@Override
	protected void setUp() {
	}

	public void testBindSimpleTerm() {
		Program program = new Program(new ProgramExecutor());
		Term simpleTerm = new Term(Term.Type.TERM, "simpleterm", (List<Term>) null);
		program.add(new Fact(new Term(Term.Type.TERM, "simpleterm", (List<Term>) null)));
		SolvableBinder sb = new SolvableBinder(simpleTerm);
		assertEquals(sb.bind(), true);
		sb = new SolvableBinder(new Term(Term.Type.TERM, "simpleterm2", (List<Term>) null));
		assertEquals(sb.bind(), false);
	}

	public void testBindVariables() {
		Program program = new Program(new ProgramExecutor());
		Term arg1 = new Term(Term.Type.TERM, "arg1", (List<Term>) null);
		Term arg2 = new Term(Term.Type.VARIABLE, "X");
		List<Term> argList1 = new ArrayList<Term>();
		argList1.add(arg1);
		argList1.add(arg2);
		Term simpleTerm = new Term(Term.Type.TERM, "simpleterm", argList1);

		arg1 = new Term(Term.Type.VARIABLE, "Y");
		arg2 = new Term(Term.Type.TERM, "arg2", (List<Term>) null);
		argList1 = new ArrayList<Term>();
		argList1.add(arg1);
		argList1.add(arg2);
		Term simpleTerm2 = new Term(Term.Type.TERM, "simpleterm", argList1);


		program.add(new Fact(simpleTerm));
		SolvableBinder sb = new SolvableBinder(simpleTerm2);
		assertEquals(sb.bind(), true);

		argList1.add(arg2);
		sb = new SolvableBinder(simpleTerm2);
		assertEquals(sb.bind(), false);
	}
}
