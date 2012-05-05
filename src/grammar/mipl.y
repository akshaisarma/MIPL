%{
/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: Parser*.java / mipl.y
 * Author A: YoungHoon Jung <yj2244@columbia.edu>
 * Author B: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Akshai Sarma <as4107@columbia.edu>
 * Description: Automatically generated parser from mipl.y
 *              PLEASE DO NOT MODIFY THIS Parser*.java FILE,
 *              INSTEAD, MODIFY mipl.y
 */

import java.io.*;
import java.util.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.runtime.traverse.*;
%}

%token IDENTIFIER STRING_LITERAL 
%token LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP MUL_ASSIGN DIV_ASSIGN MOD_ASSIGN ADD_ASSIGN SUB_ASSIGN

%token MUL_CELL_OP DIV_CELL_OP EXP_CELL_OP

%token IF ELSE DO WHILE

%token REGEX VARIABLE NOT LARROW_OP IS JOB
%token NUMBER TRUE FALSE INCLUDE

%start program

%%
program
	: commands
	;

commands
	: commands command	{ program.add((Command) $2); }
	| command			{ program.add((Command) $1); }
	;

command
	: fact				/* Default Action $$ = $1 */
	| query				/* Default Action $$ = $1 */
	| rule				/* Default Action $$ = $1 */
	| job				/* Default Action $$ = $1 */
	| INCLUDE STRING_LITERAL	{ new Parser((String) $2, program, false); }
	;

fact
	: term '.'								{ $$ = new Fact((Term) $1); }
	| '[' id_list ']' LARROW_OP jobcall '.'	{ $$ = new Fact((List<String>) $2, (Term) $5); }
	| '[' ']' LARROW_OP jobcall '.'			{ $$ = new Fact((List<String>) null, (Term) $4); }
	;

jobcall
	: IDENTIFIER '(' ')'				{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) null); }
	| IDENTIFIER '(' jobcall_args ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	;

jobcall_args
	: jobcall_args ',' jobcall_args_cand	{ $$ = $1; ((List<Term>) $$).add((Term) $3);}
	| jobcall_args_cand						{ $$ = new ArrayList<Term>(); ((List<Term>) $$).add((Term) $1); }
	;

jobcall_args_cand
	: IDENTIFIER		{ $$ = new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>()); }
	| VARIABLE			{ $$ = new Term(Term.Type.VARIABLE, (String) $1); }
	| numerical_value	{ $$ = new Term(Term.Type.NUMBER, (Double) $1); }
	| STRING_LITERAL	{ $$ = new Term(Term.Type.STRING, (String) $1); }
//	| jobcall			/* Default Action $$ = $1 */
	;

id_list
	: id_list ',' IDENTIFIER	{ $$ = $1; ((List<String>) $$).add((String) $3); }
	| IDENTIFIER			{ $$ = new ArrayList<String>(); ((List<String>) $$).add((String) $1); }
	;

query
	: or_terms '?'			{ $$ = new Query((Term) $1); }
	;

rule
	: term LARROW_OP or_terms '.'	{ $$ = new Rule((Term) $1, (Term) $3); }
	;

or_terms
	: and_terms			/* Default Action $$ = $1 */
	| and_terms ';' or_terms	{ $$ = new Term(Term.Type.ORTERMS, (Term) $1, (Term) $3); }
	;

and_terms
	: term				/* Default Action $$ = $1 */
	| term ',' and_terms		{ $$ = new Term(Term.Type.ANDTERMS, (Term) $1, (Term) $3); }
	;

term
	: IDENTIFIER			{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) null); }
	| IDENTIFIER '(' term_args ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	| IDENTIFIER '(' '*' ')'	{ $$ = new Term(Term.Type.QUERYALL, (String) $1); }
	| REGEX				{ $$ = new Term(Term.Type.REGEXTERM, (String) $1, new ArrayList<Term>()); }
	| REGEX '(' term_args ')'	{ $$ = new Term(Term.Type.REGEXTERM, (String) $1, (List<Term>) $3); }
	| REGEX '(' '*' ')'		{ $$ = new Term(Term.Type.REGEXQUERYALL, (String) $1); }
	| NOT term			{ $$ = new Term(Term.Type.NOTTERM, (Term) $2); }
	| term_expr			{ $$ = ((Expression) $1).getTerm(); }
	| VARIABLE IS term_expr		{ $$ = new Term(Term.Type.IS, new Term(Term.Type.VARIABLE, (String) $1), (Expression) $3); }
	| term_expr '<' term_expr	{ $$ = new Term(Term.Type.LT, (Expression) $1, (Expression) $3); }
	| term_expr '>' term_expr	{ $$ = new Term(Term.Type.GT, (Expression) $1, (Expression) $3); }
	| term_expr LE_OP term_expr	{ $$ = new Term(Term.Type.LE, (Expression) $1, (Expression) $3); }
	| term_expr GE_OP term_expr	{ $$ = new Term(Term.Type.GE, (Expression) $1, (Expression) $3); }
	| term_expr EQ_OP term_expr	{ $$ = new Term(Term.Type.EQ, (Expression) $1, (Expression) $3); }
	| term_expr NE_OP term_expr	{ $$ = new Term(Term.Type.NE, (Expression) $1, (Expression) $3); }
	;

term_expr
	: term_expr '+' term_fact	{ $$ = new Expression(Expression.Type.PLUS, (Expression) $1, (Expression) $3); }
	| term_expr '-' term_fact	{ $$ = new Expression(Expression.Type.MINUS, (Expression) $1, (Expression) $3); }
	| term_fact			/* Default Action $$ = $1 */
	;

term_fact
	: term_fact '*' term_term	{ $$ = new Expression(Expression.Type.MULTI, (Expression) $1, (Expression) $3); }
	| term_fact '/' term_term	{ $$ = new Expression(Expression.Type.DIVIDE, (Expression) $1, (Expression) $3); }
	| term_term			/* Default Action $$ = $1 */
	;

term_term
	: VARIABLE			{ $$ = new Expression(Expression.Type.TERM, new Term(Term.Type.VARIABLE, (String) $1)); }
	| numerical_value	{ $$ = new Expression(Expression.Type.TERM, new Term(Term.Type.NUMBER, (Double) $1)); }
	| '(' term_expr ')'	{ $$ = $2; }
	;

term_args_cand
	: IDENTIFIER			{ $$ = new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>()); }
	| IDENTIFIER '(' term_args ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	| VARIABLE			{ $$ = new Term(Term.Type.VARIABLE, (String) $1); }
	| '_'				{ $$ = new Term(Term.Type.VARIABLE, "_"); }
	| numerical_value	{ $$ = new Term(Term.Type.NUMBER, (Double) $1); }
	| STRING_LITERAL	{ $$ = new Term(Term.Type.STRING, (String) $1); }
	;

term_args
	: term_args_cand			{ $$ = new ArrayList<Term>(); ((List<Term>) $$).add((Term) $1); }
	| term_args ',' term_args_cand		{ $$ = $1; ((List<Term>) $$).add((Term) $3);}
	;

job
	: JOB IDENTIFIER '(' job_args ')' '{' stmt_list '}' 	{ $$ = new Job((String) $2, (List<Term>) $4, (List<JobStmt>) $7); }
	;

job_args
	: job_args ',' VARIABLE		{ $$ = $1; ((List<Term>) $$).add(new Term(Term.Type.VARIABLE, (String) $3)); }
	| VARIABLE					{ $$ = new ArrayList<Term>(); ((List<Term>) $$).add(new Term(Term.Type.VARIABLE, (String) $1)); }
	;

stmt
	: selection_stmt		/* Default Action $$ = $1 */
	| compound_stmt			/* Default Action $$ = $1 */
	| return_stmt			/* Default Action $$ = $1 */
	| expr_stmt				/* Default Action $$ = $1 */
	| iteration_stmt		/* Default Action $$ = $1 */
	;

stmt_list
	: stmt				{ $$ = new ArrayList<JobStmt>(); ((List<JobStmt>) $$).add((JobStmt) $1); }
	| stmt_list stmt	{ $$ = $1; ((List<JobStmt>) $$).add((JobStmt) $2); }
	;

compound_stmt
	: '{' '}'			{ $$ = new JobStmt(JobStmt.Type.COMPOUND, new ArrayList<JobStmt>()); }
	| '{' stmt_list '}'	{ $$ = new JobStmt(JobStmt.Type.COMPOUND, (List<JobStmt>) $2); }
	;

return_stmt
	: '@' expr '.'			{ $$ = new JobStmt(JobStmt.Type.RETURN, (JobExpr) $2); }
	;

expr_stmt
	: expr '.'			{ $$ = new JobStmt(JobStmt.Type.EXPR, (JobExpr) $1); }
	;

selection_stmt
	: IF '(' bool_expr ')' stmt		{ $$ = new JobStmt(JobStmt.Type.IF, (JobExpr) $3, (JobStmt) $5); }
	| IF '(' bool_expr ')' stmt ELSE stmt	{ $$ = new JobStmt(JobStmt.Type.IF, (JobExpr) $3, (JobStmt) $5, (JobStmt) $7); }
	;

iteration_stmt
	: WHILE '(' bool_expr ')' stmt		{ $$ = new JobStmt(JobStmt.Type.WHILE, (JobExpr) $3, (JobStmt) $5); }
	| DO stmt WHILE '(' bool_expr ')' '.'	{ $$ = new JobStmt(JobStmt.Type.DOWHILE, (JobExpr) $5, (JobStmt) $2); }
	;

numerical_value
	: NUMBER 				{ $$ = $1; }
	| '-' numerical_value 	{ $$ = -1 * (Double) $2; }
	;

expr
	: additive_expr			/* Default Action $$ = $1 */
	| VARIABLE assign_op expr	{ $$ = new JobExpr((JobExpr.Type) $2, (String) $1, (JobExpr) $3); }
	;

assign_op
	: '='				{ $$ = JobExpr.Type.ASSIGN; }
	| LARROW_OP			{ $$ = JobExpr.Type.ASSIGN; }
	| MUL_ASSIGN		{ $$ = JobExpr.Type.MULASSIGN; }
	| DIV_ASSIGN		{ $$ = JobExpr.Type.DIVASSIGN; }
	| MOD_ASSIGN		{ $$ = JobExpr.Type.MODASSIGN; }
	| ADD_ASSIGN		{ $$ = JobExpr.Type.ADDASSIGN; }
	| SUB_ASSIGN		{ $$ = JobExpr.Type.SUBASSIGN; }
	;


bool_expr
	: logical_and_expr				/* Default Action $$ = $1 */
	| bool_expr OR_OP logical_and_expr		{ $$ = new JobExpr(JobExpr.Type.OR, (JobExpr) $1, (JobExpr) $3); }
	;

logical_and_expr
	: equality_expr					/* Default Action $$ = $1 */
	| logical_and_expr AND_OP equality_expr		{ $$ = new JobExpr(JobExpr.Type.AND, (JobExpr) $1, (JobExpr) $3); }
	;

equality_expr
	: relational_expr				/* Default Action $$ = $1 */
	| expr EQ_OP expr				{ $$ = new JobExpr(JobExpr.Type.EQ, (JobExpr) $1, (JobExpr) $3); }
	| expr NE_OP expr				{ $$ = new JobExpr(JobExpr.Type.NE, (JobExpr) $1, (JobExpr) $3); }
	| equality_expr EQ_OP relational_expr		{ $$ = new JobExpr(JobExpr.Type.EQ, (JobExpr) $1, (JobExpr) $3); }
	| equality_expr NE_OP relational_expr		{ $$ = new JobExpr(JobExpr.Type.NE, (JobExpr) $1, (JobExpr) $3); }
	;

relational_expr
	: boolvalue_expr					/* Default Action $$ = $1 */
	| expr '<' expr			{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	| expr '>' expr			{ $$ = new JobExpr(JobExpr.Type.GT, (JobExpr) $1, (JobExpr) $3); }
	| expr LE_OP expr		{ $$ = new JobExpr(JobExpr.Type.LE, (JobExpr) $1, (JobExpr) $3); }
	| expr GE_OP expr		{ $$ = new JobExpr(JobExpr.Type.GE, (JobExpr) $1, (JobExpr) $3); }
	;

boolvalue_expr
	: TRUE
	| FALSE
	| '(' bool_expr ')'		{ $$ = $2; }

additive_expr
	: multiplicative_expr				/* Default Action $$ = $1 */
	| additive_expr '+' multiplicative_expr		{ $$ = new JobExpr(JobExpr.Type.ADD, (JobExpr) $1, (JobExpr) $3); }
	| additive_expr '-' multiplicative_expr		{ $$ = new JobExpr(JobExpr.Type.SUB, (JobExpr) $1, (JobExpr) $3); }
	;

multiplicative_expr
	: unary_expr					/* Default Action $$ = $1 */
	| multiplicative_expr '*' unary_expr		{ $$ = new JobExpr(JobExpr.Type.MULT, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr '/' unary_expr		{ $$ = new JobExpr(JobExpr.Type.DIV, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr '%' unary_expr		{ $$ = new JobExpr(JobExpr.Type.MOD, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr MUL_CELL_OP unary_expr	{ $$ = new JobExpr(JobExpr.Type.MULT_CELL, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr DIV_CELL_OP unary_expr	{ $$ = new JobExpr(JobExpr.Type.DIV_CELL, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr EXP_CELL_OP unary_expr	{ $$ = new JobExpr(JobExpr.Type.EXP_CELL, (JobExpr) $1, (JobExpr) $3); }
	;

unary_expr
	: postfix_expr				/* Default Action $$ = $1 */
	| '+' unary_expr			{ $$ = $2; }
	| '-' unary_expr			{ $$ = new JobExpr(JobExpr.Type.NEGATE, (JobExpr) $2); }
	;

primary_expr
	: IDENTIFIER			{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>())); }
	| VARIABLE				{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, (String) $1)); }
	| NUMBER				{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.NUMBER, (Double) $1)); }
	| '(' expr ')'			{ $$ = $2; }
	;
/*
array_idx_elmt
	: '~'					{ $$ = new ArrayIndex(0, true); }
	| '~' NUMBER			{ $$ = new ArrayIndex(0, (long) (double) (Double) $2); }
	| NUMBER				{ $$ = new ArrayIndex((long) (double) (Double) $1); } 
	| NUMBER '~'			{ $$ = new ArrayIndex((long) (double) (Double) $1, true); }
	| NUMBER '~' NUMBER		{ $$ = new ArrayIndex((long) (double) (Double) $1, (long) (double) (Double) $3); }
	;

array_idx_list
	: array_idx_elmt			{ $$ = new ArrayList<ArrayIndex>(); ((List<ArrayIndex>) $$).add((ArrayIndex) $1); }
	| array_idx_list ',' array_idx_elmt	{ $$ = $1; ((List<ArrayIndex>) $$).add((ArrayIndex) $3); }
	;
*/
postfix_expr
	: primary_expr							/* Default Action $$ = $1 */
//	| VARIABLE '[' array_idx_list ']' '[' array_idx_list ']'	{ $$ = new JobExpr(JobExpr.Type.ARRAY, new Term(Term.Type.VARIABLE, (String) $1), 		(List<ArrayIndex>) $3, (List<ArrayIndex>) $6); }
	| IDENTIFIER '(' ')'						{ $$ = new JobExpr(JobExpr.Type.JOBCALL, (String) $1, (List<JobExpr>) null); }
	| IDENTIFIER '(' nested_jobcall_args ')'				{ $$ = new JobExpr(JobExpr.Type.JOBCALL, (String) $1, (List<JobExpr>) $3); }
	;

nested_jobcall_args
	: expr					{ $$ = new ArrayList<JobExpr>(); ((List<JobExpr>) $$).add((JobExpr) $1); }
	| nested_jobcall_args ',' expr		{ $$ = $1; ((List<JobExpr>) $$).add((JobExpr) $3); }
	;

%%

private Yylex lexer;

private String filename = null;

private boolean parseSuccess = true;

private Program program = null;

private int nError = 0;

private int yylex () {
	int yyl_return = -1;
	try {
		yylval = null; //new Term(Term.Type.NUMBER, 0.0);
		yyl_return = lexer.yylex();
	}
	catch (IOException e) {
		System.err.println("IO error :"+e);
	}
	return yyl_return;
}

private String getFilename() {
	if (filename == null)
		return "System.in";
	else
		return filename;
}

public void yyerror (String error) {
	System.err.println("Error: " + error);
	System.err.println("before " + yylval + " at " + getFilename() + " Line: " + lexer.getLine() + " Column: " + lexer.getColumn());

	nError++;

	parseSuccess = false;
//	throw new java.io.IOException("Syntax Error: " + lexer.yytext());
}

public Parser(Traverser traverser) {
	this(new InputStreamReader(System.in), new Program(traverser));
}

public Parser(Program program) {
	this(new InputStreamReader(System.in), program);
}

public Parser(String file, Traverser traverser) {
	this(file, new Program(traverser));
}

public Parser(String file) {
	this(file, new Program());
}

public Parser(String file, Program program) {
	this(file, program, true);
}

public Parser(String file, Program program, boolean finish) {
	FileReader r;
	try {
		r = new FileReader(file);
	} catch (IOException ioe) {
		nError++;
		ioe.printStackTrace();
		return;
	}
	lexer = new Yylex(r, this);
	parseSuccess = true;

	filename = file;

	this.program = program;

	yyparse();

	if (finish)
		program.finish();
}

public Parser(Reader r, Program program) {
	lexer = new Yylex(r, this);
	parseSuccess = true;

	this.program = program;

	yyparse();

	program.finish();
}

public int getNumError() {
	return nError;
}

public Program getProgram() {
	return program;
}

