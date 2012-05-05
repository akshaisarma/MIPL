/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: SemanticChecker.java
 * Author: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Wonjoon Song <dws2127@columbia.edu>
 * Description: Implements some rules (others are in Program Executor)
 *				- job usage before definition
 *				- no redefinition of built in jobs
 * 				- invalid query types
 *				- no regexes in facts and rules
 *				- variable defined before use
 */

package edu.columbia.mipl.runtime.execute;

import java.util.*;

import edu.columbia.mipl.builtin.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;


/* 
 * Terms can only appear in facts, rules, queries or jobs.
 * Setting haveRegex will cause it to be unset at the
 * postTraverse reaching of the parent fact, query or rule
 * Parser will complain if Regexes inside Jobs so no need 
 * for it here. I needed to haveRegex because rule can be
 * arbitrarily deep nested with or_terms unlike fact.
 */
public class SemanticChecker extends RuntimeTraverser {
	private HashMap<String, Knowledge> definedJobs;
	private HashSet<String> knownVariables;
	private HashSet<String> unknownVariables;
	private boolean haveRegex;

	public Method getMethod() {
		return Method.POST;
	}

	public SemanticChecker() {
		this.definedJobs = new HashMap<String, Knowledge>();
		this.haveRegex = false;
		this.knownVariables = new HashSet<String>();
		this.unknownVariables = new HashSet<String>();
	}

	public boolean reachTerm(Term term) {
		if (term.getType() == Term.Type.REGEXTERM)
			haveRegex = true;
		return true;
	}

	public boolean reachExpression(Expression expr) {
		return true;
	}

	public boolean reachFact(Fact fact) {
		if (haveRegex) {
			new Exception(fact.getName() + " should not have regular expressions!").printStackTrace();
			return false;
		}
		haveRegex = false;

		if (fact.getType() != Fact.Type.MATRIXASFACTS)
			return true;

		if (BuiltinTable.existJob(fact.getName()))
			return true;

		Job definedJob = (Job) definedJobs.get(fact.getName());

		if (definedJob == null) {
			new Exception(fact.getName() + " has not been defined!").printStackTrace();
			return false;
		}
		return true;
	}

	public boolean reachRule(Rule rule) {
		if (haveRegex) {
			new Exception(rule.getName() + " should not have regular expressions!").printStackTrace();
			return false;
		}
		haveRegex = false;
		return true;
	}

	public boolean reachQuery(Query query) {
		Term term = query.getTerm();
		if (term.getType() != Term.Type.TERM &&
			term.getType() != Term.Type.REGEXTERM &&
			term.getType() != Term.Type.QUERYALL &&
			term.getType() != Term.Type.REGEXQUERYALL) {
			new Exception(query.getName() + " does not have proper terms. No expressions or IS statements allowed!").printStackTrace();
			return false;
		}
		haveRegex = false;
		return true;
	}

	public boolean reachJob(Job job) {
		List<String> undefinedVariables = hasUnknownVariables(job);
		if (!undefinedVariables.isEmpty()) {
			String undefList = "";
			for (String s : undefinedVariables)
				undefList += s + " ";	
			new Exception(job.getName() + " has these undefined variables: " + undefList).printStackTrace();
			return false;
		}
		knownVariables.clear();
		unknownVariables.clear();

		String name = job.getName();
		boolean builtinExists = BuiltinTable.existJob(name);

		Job definedJob = (Job) definedJobs.get(name);
		if (definedJob == null && !builtinExists) {
			definedJobs.put(job.getName(), job);
			return true;
		}

		if (builtinExists) {
			new Exception(job.getName() + " is a built in job! Cannot redefine it").printStackTrace();
			return false;
		}

		new Exception(job.getName() + " has already been defined!").printStackTrace();
		return false;
	}

	public boolean reachJobStmt(JobStmt jstmt) {
		return true;
	}

	public boolean reachJobExpr(JobExpr jexpr) {
		switch (jexpr.getType()) {
			case ASSIGN:
			case MULASSIGN:
			case DIVASSIGN:
			case MODASSIGN:
			case ADDASSIGN:
			case SUBASSIGN:
				knownVariables.add(jexpr.getName());
				return true;
			case TERM:
				Term term = jexpr.getTerm();
				if (term.getType() == Term.Type.VARIABLE)
					if (!knownVariables.contains(term.getName()))
						unknownVariables.add(term.getName());
				return true;
			case JOBCALL:
				if (BuiltinTable.existJob(jexpr.getName()))
					return true;
				Job definedJob = (Job) definedJobs.get(jexpr.getName());	
				if (definedJob == null || definedJob.getArgs().size() != jexpr.getExprs().size()) {
					new Exception(jexpr.getName() + " has not been defined!").printStackTrace();
					return false;
				}
				return true;
			default:
				return true;
		}
	}

	public void finish() {
	}
	
	private List<String> hasUnknownVariables(Job job) {
		List<Term> args = job.getArgs();
		List<String> undefList = new ArrayList<String>();
		Iterator<String> i = unknownVariables.iterator();
	out:
		while (i.hasNext()) {
			String var = i.next();
			for (Term t : args)
				if (t.getName().equals(var))
					break out;
			if (!knownVariables.contains(var))
				undefList.add(var);
		}
		return undefList;
	}
	
}
