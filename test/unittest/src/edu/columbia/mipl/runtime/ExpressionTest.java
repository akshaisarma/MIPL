	/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: ExpressionTest.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Test for Expression 
 */

package edu.columbia.mipl.runtime;

import java.util.*;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(ExpressionTest.class);
	}

	@Override
	protected void setUp() {
	}
	
	public void testUnaryExpression() {
		Term term = new Term(Term.Type.NUMBER, new Double(42));
		Expression unaryExpr = new Expression(Expression.Type.TERM, term);
		assertTrue(unaryExpr.getTerm() == term);
	}

	public void testBinaryExpression() {
		Expression expr1 = new Expression(Expression.Type.TERM, new Term(Term.Type.VARIABLE, "X"));
		Expression expr2 = new Expression(Expression.Type.TERM, new Term(Term.Type.NUMBER, new Double(42)));
		Expression binaryExpr = new Expression(Expression.Type.DIVIDE, expr1, expr2);
		assertTrue(binaryExpr.getExpr1() == expr1);
		assertTrue(binaryExpr.getExpr2() == expr2);
		assertTrue(binaryExpr.getType() == Expression.Type.DIVIDE);
	}
}
