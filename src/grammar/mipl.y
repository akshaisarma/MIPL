%{
/**
 * MIPL: Mining Integrated Programming Language
 *
 * File: Parser*.java / mipl.y
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Akshai Sarma <as4107@columbia.edu>
 * Description: Automatically generated parser from mipl.y
 *              PLEASE DO NOT MODIFY THIS Parser*.java FILE,
 *              INSTEAD, MODIFY mipl.y
 */

import java.io.*;
import java.util.*;

import edu.columbia.mipl.runtime.*;
%}

%token IDENTIFIER STRING_LITERAL 
%token LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP MUL_ASSIGN DIV_ASSIGN MOD_ASSIGN ADD_ASSIGN SUB_ASSIGN

%token IF ELSE DO WHILE

%token REGEX VARIABLE NOT LARROW_OP IS
%token NUMBER

%token NULL

%start program
%%


program
	: commands
	;

commands
	: commands command		{ /* ProgramList.add(command); */ }
	| command		{ /* ProgramList.add(command); */ }
	;

command
	: fact				/* Default Action $$ = $1 */
	| query				/* Default Action $$ = $1 */
	| rule				/* Default Action $$ = $1 */
	| job				/* Default Action $$ = $1 */
	;

fact
	: term '.'							{ $$ = new Fact((Term) $1); }
	| '[' maf_list ']' LARROW_OP IDENTIFIER '(' arg_list ')' '.'	{ $$ = new Fact((String) $5, (List<String>) $2, (List<Term>) $7); }
	;

maf_list
	: maf_list ',' IDENTIFIER	{ $$ = $1; ((List<String>) $$).add((String) $3); }
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
	| or_terms ';' and_terms	{ $$ = new Term(Term.Type.ORTERMS, (Term) $1, (Term) $3); }
	;

and_terms
	: term				/* Default Action $$ = $1 */
	| and_terms ',' term		{ $$ = new Term(Term.Type.ANDTERMS, (Term) $1, (Term) $3); }
	;

term /*% load into edu.columbia.mipl.runtime.Term */
	: IDENTIFIER			{ $$ = new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>()); }
	| IDENTIFIER '(' arg_list ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	| IDENTIFIER '(' '*' ')'	{ $$ = new Term(Term.Type.QUERYALL, (String) $1); }
        | REGEX				{ $$ = new Term(Term.Type.REGEXTERM, (String) $1, new ArrayList<Term>()); }
        | REGEX '(' arg_list ')'	{ $$ = new Term(Term.Type.REGEXTERM, (String) $1, (List<Term>) $3); }
        | REGEX '(' '*' ')'		{ $$ = new Term(Term.Type.REGEXQUERYALL, (String) $1); }
        | NOT term			{ $$ = new Term(Term.Type.NOTTERM, (Term) $2); }
        | term_expr			{ $$ = ((Expression) $1).getTerm(); }
        | VARIABLE IS term		{ $$ = new Term(Term.Type.IS, new Term(Term.Type.VARIABLE, (String) $1), (Term) $3); } /* TODO: Should check VariableMatcher for the same command */
        | term_expr '<' term_expr	{ $$ = new Term(Term.Type.LT, (Expression) $1, (Expression) $3); }
        | term_expr '>' term_expr	{ $$ = new Term(Term.Type.GT, (Expression) $1, (Expression) $3); }
        | term_expr LE_OP term_expr	{ $$ = new Term(Term.Type.LE, (Expression) $1, (Expression) $3); }
        | term_expr GE_OP term_expr	{ $$ = new Term(Term.Type.GE, (Expression) $1, (Expression) $3); }
        | term_expr EQ_OP term_expr	{ $$ = new Term(Term.Type.EQ, (Expression) $1, (Expression) $3); }
        | term_expr NE_OP term_expr	{ $$ = new Term(Term.Type.NE, (Expression) $1, (Expression) $3); }
        ;

term_expr /*% load into edu.columbia.mipl.runtime.Expression */
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
	: VARIABLE			{ $$ = new Expression(Expression.Type.TERM, new Term(Term.Type.VARIABLE, (String) $1)); } /* TODO: Should check VariableMatcher for the same command */
	| NUMBER			{ $$ = new Expression(Expression.Type.TERM, new Term(Term.Type.NUMBER, (Double) $1)); }
	| '(' term_expr ')'		{ $$ = $2; }
	;

arg_cand
	: IDENTIFIER			{ $$ = new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>()); }
	| IDENTIFIER '(' arg_list ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	| VARIABLE			{ $$ = new Term(Term.Type.VARIABLE, (String) $1); } /* TODO: Should check VariableMatcher for the same command */
	| '_'				{ $$ = new Term(Term.Type.VARIABLE, "_"); }
	| NUMBER			{ $$ = new Term(Term.Type.NUMBER, (Double) $1); }
        | STRING_LITERAL		{ $$ = new Term(Term.Type.STRING, (String) $1); }
	;

arg_list
	: arg_cand			{ $$ = new ArrayList<Term>(); ((List<Term>) $$).add((Term) $1); }
	| arg_list ',' arg_cand		{ $$ = $1; ((List<Term>) $$).add((Term) $3);}
	;

job
	: IDENTIFIER '(' arg_list ')' '{' stmt_list '}' 	{ $$ = new Job((String) $1, (List<Term>) $3, (List<JobStmt>) $6); }
	;

stmt
	: selection_stmt		/* Default Action $$ = $1 */
	| compound_stmt			/* Default Action $$ = $1 */
	| return_stmt			/* Default Action $$ = $1 */
	| expr_stmt			/* Default Action $$ = $1 */
	| iteration_stmt		/* Default Action $$ = $1 */
	;

stmt_list
	: stmt				{ $$ = new ArrayList<JobStmt>(); ((List<JobStmt>) $$).add((JobStmt) $1); }
	| stmt_list stmt		{ $$ = $1; ((List<JobStmt>) $$).add((JobStmt) $2); }
	;

compound_stmt
	: '{' '}'			{ $$ = new JobStmt(JobStmt.Type.NULL); }
	| '{' stmt_list '}'		{ $$ = new JobStmt(JobStmt.Type.COMPOUND, (List<JobStmt>) $2); }
	;

return_stmt
	: '@' expr '.'			{ $$ = new JobStmt(JobStmt.Type.RETURN, (JobExpr) $2); }
	;

expr_stmt
	: expr '.'			{ $$ = new JobStmt(JobStmt.Type.EXPR, (JobExpr) $1); }
	;

selection_stmt
	: IF '(' expr ')' stmt			{ $$ = new JobStmt(JobStmt.Type.IF, (JobExpr) $3, (JobStmt) $5); }
	| IF '(' expr ')' stmt ELSE stmt	{ $$ = new JobStmt(JobStmt.Type.IF, (JobExpr) $3, (JobStmt) $5, (JobStmt) $7); }
	;

iteration_stmt
	: WHILE '(' expr ')' stmt		{ $$ = new JobStmt(JobStmt.Type.WHILE, (JobExpr) $3, (JobStmt) $5); }
	| DO stmt WHILE '(' expr ')' '.'	{ $$ = new JobStmt(JobStmt.Type.DOWHILE, (JobExpr) $5, (JobStmt) $2); }
	;

expr
	: assign_expr		/* Default Action $$ = $1 */
	| expr ',' assign_expr	{ $$ = new JobExpr(JobExpr.Type.COMPOUND, (JobExpr) $1, (JobExpr) $3); }
	;

assign_expr
	: logical_or_expr			/* Default Action $$ = $1 */
	| unary_expr assign_op assign_expr	{ $$ = new JobExpr((JobExpr.Type) $2, (JobExpr) $1, (JobExpr) $3); }
	;

assign_op
	: '='			{ $$ = JobExpr.Type.ASSIGN; }
	| LARROW_OP		{ $$ = JobExpr.Type.ASSIGN; }
	| MUL_ASSIGN		{ $$ = JobExpr.Type.MULASSIGN; }
	| DIV_ASSIGN		{ $$ = JobExpr.Type.DIVASSIGN; }
	| MOD_ASSIGN		{ $$ = JobExpr.Type.MODASSIGN; }
	| ADD_ASSIGN		{ $$ = JobExpr.Type.ADDASSIGN; }
	| SUB_ASSIGN		{ $$ = JobExpr.Type.SUBASSIGN; }
	;


logical_or_expr
	: logical_and_expr				/* Default Action $$ = $1 */
	| logical_or_expr OR_OP logical_and_expr	{ $$ = new JobExpr(JobExpr.Type.OR, (JobExpr) $1, (JobExpr) $3); }
	;

logical_and_expr
	: equality_expr					/* Default Action $$ = $1 */
	| logical_and_expr AND_OP equality_expr		{ $$ = new JobExpr(JobExpr.Type.AND, (JobExpr) $1, (JobExpr) $3); }
	;

equality_expr
	: relational_expr				/* Default Action $$ = $1 */
	| equality_expr EQ_OP relational_expr		{ $$ = new JobExpr(JobExpr.Type.EQ, (JobExpr) $1, (JobExpr) $3); }
	| equality_expr NE_OP relational_expr		{ $$ = new JobExpr(JobExpr.Type.NE, (JobExpr) $1, (JobExpr) $3); }
	;

relational_expr
	: additive_expr					/* Default Action $$ = $1 */
	| relational_expr '<' additive_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	| relational_expr '>' additive_expr		{ $$ = new JobExpr(JobExpr.Type.GT, (JobExpr) $1, (JobExpr) $3); }
	| relational_expr LE_OP additive_expr		{ $$ = new JobExpr(JobExpr.Type.LE, (JobExpr) $1, (JobExpr) $3); }
	| relational_expr GE_OP additive_expr		{ $$ = new JobExpr(JobExpr.Type.GE, (JobExpr) $1, (JobExpr) $3); }
	;

additive_expr
	: multiplicative_expr				/* Default Action $$ = $1 */
	| additive_expr '+' multiplicative_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	| additive_expr '-' multiplicative_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	;

multiplicative_expr
	: unary_expr					/* Default Action $$ = $1 */
	| multiplicative_expr '*' unary_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr '/' unary_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	| multiplicative_expr '%' unary_expr		{ $$ = new JobExpr(JobExpr.Type.LT, (JobExpr) $1, (JobExpr) $3); }
	;

unary_expr
	: postfix_expr				/* Default Action $$ = $1 */
	| '+' unary_expr			{ $$ = $2; }
	| '-' unary_expr			{ $$ = new JobExpr(JobExpr.Type.NEGATE, (JobExpr) $2); }
	;

primary_expr
	: IDENTIFIER				{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>())); }
	| VARIABLE				{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.VARIABLE, (String) $1)); }
	| NUMBER				{ $$ = new JobExpr(JobExpr.Type.TERM, new Term(Term.Type.NUMBER, (Double) $1)); }
//	| STRING_LITERAL
	| '(' expr ')'				{ $$ = $2; }
	;

array_idx_elmt
	: '~'					{ $$ = new ArrayIndex(0, true); }
	| '~' NUMBER				{ $$ = new ArrayIndex(0, (long) (double) (Double) $2); }
	| NUMBER				{ $$ = new ArrayIndex((long) (double) (Double) $1); } 
	| NUMBER '~'				{ $$ = new ArrayIndex((long) (double) (Double) $1, true); }
	| NUMBER '~' NUMBER			{ $$ = new ArrayIndex((long) (double) (Double) $1, (long) (double) (Double) $3); }
	;

array_idx_list
	: array_idx_elmt			{ $$ = new ArrayList<ArrayIndex>(); ((List<ArrayIndex>) $$).add((ArrayIndex) $1); };
	| array_idx_list ',' array_idx_elmt	{ $$ = $1; ((List<ArrayIndex>) $$).add((ArrayIndex) $3); }
	;

postfix_expr
	: primary_expr							/* Default Action $$ = $1 */
	| VARIABLE '[' array_idx_list ']' '[' array_idx_list ']'	{ $$ = new JobExpr(JobExpr.Type.ARRAY, new Term(Term.Type.VARIABLE, (String) $1), (List<ArrayIndex>) $3, (List<ArrayIndex>) $6); }
	| IDENTIFIER '(' argument_expr_list ')'				{ $$ = new JobExpr(JobExpr.Type.JOBCALL, (String) $1, (List<JobExpr>) $3); }
	;

argument_expr_list
	:					{ $$ = new ArrayList<JobExpr>(); }
	| assign_expr				{ $$ = new ArrayList<JobExpr>(); ((List<JobExpr>) $$).add((JobExpr) $1); }
	| argument_expr_list ',' assign_expr	{ $$ = $1; ((List<JobExpr>) $$).add((JobExpr) $3); }
	;

%%

private Yylex lexer;

private String filename = null;

private boolean parseSuccess = true;

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
	System.err.println ("Error: " + error);
	System.err.println (getFilename() + ":" + lexer.getLine() + ":" + lexer.getColumn());

	parseSuccess = false;
//	throw new java.io.IOException("Syntax Error: " + lexer.yytext());
}

public Parser(Reader r) {
	lexer = new Yylex(r, this);
	parseSuccess = true;
}

public static boolean parse(InputStream in) throws IOException {
	Parser parser = new Parser(new InputStreamReader(System.in));
	parser.yyparse();

	return parser.parseSuccess;
}

public static boolean parse(String file) throws IOException {
	Parser parser = new Parser(new FileReader(file));
	parser.filename = file;
	parser.yyparse();

	return parser.parseSuccess;
}
