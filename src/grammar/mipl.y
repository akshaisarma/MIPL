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
	: lines
	;

lines
	: lines line		{ /* ProgramList.add(line); */ }
	| line		{ /* ProgramList.add(line); */ }
	;

line
	: fact
	| query
	| rule
	| job
	;

fact
	: term '.'
	| '[' maf_list ']' LARROW_OP IDENTIFIER '(' arg_list ')' '.'
	;

maf_list
	: maf_list ',' IDENTIFIER
	| IDENTIFIER
	;

query
	: or_terms '?'
	;

rule
	: term LARROW_OP or_terms '.'
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
        | VARIABLE IS term		{ $$ = new Term(Term.Type.IS, new Term(Term.Type.VARIABLE, (String) $1), (Term) $3); } /* TODO: Should check VariableMatcher for the same line */
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
	: VARIABLE			{ $$ = new Expression(Expression.Type.VARIABLE, new Term(Term.Type.VARIABLE, (String) $1)); } /* TODO: Should check VariableMatcher for the same line */
	| NUMBER			{ $$ = new Expression(Expression.Type.DOUBLE, new Term(Term.Type.NUMBER, (Double) $1)); }
	| '(' term_expr ')'		{ $$ = $2; }
	;

arg_cand
	: IDENTIFIER			{ $$ = new Term(Term.Type.TERM, (String) $1, new ArrayList<Term>()); }
	| IDENTIFIER '(' arg_list ')'	{ $$ = new Term(Term.Type.TERM, (String) $1, (List<Term>) $3); }
	| VARIABLE			{ $$ = new Term(Term.Type.VARIABLE, (String) $1); } /* TODO: Should check VariableMatcher for the same line */
	| '_'				{ $$ = new Term(Term.Type.VARIABLE, "_"); }
	| NUMBER			{ $$ = new Term(Term.Type.NUMBER, (Double) $1); }
        | STRING_LITERAL		{ $$ = new Term(Term.Type.STRING, (String) $1); }
	;

arg_list
	: arg_cand			{ if (!($$ instanceof List)) $$ = new ArrayList<Term>(); ((List<Term>) $$).add((Term) $1); }
	| arg_list ',' arg_cand		{ if (!($$ instanceof List)) $$ = new ArrayList<Term>(); ((List<Term>) $$).add((Term) $3); }
	;

job
	: IDENTIFIER '(' arg_list ')' '{' stmt_list '}'
	;

stmt
	: selection_stmt
	| compound_stmt
	| return_stmt
	| expr_stmt
	| iteration_stmt
	;

stmt_list
	: stmt
	| stmt_list stmt
	;

compound_stmt
	: '{' '}'
	| '{' stmt_list '}'
	;

return_stmt
	: '@' expr '.'
	;

expr_stmt
	: expr '.'
	;

selection_stmt
	: IF '(' expr ')' stmt
	| IF '(' expr ')' stmt ELSE stmt
	;

iteration_stmt
	: WHILE '(' expr ')' stmt
	| DO stmt WHILE '(' expr ')' '.'
	;

expr
	: assignment_expr
	| expr ',' assignment_expr
	;

assignment_expr
	: logical_or_expr
	| unary_expr assignment_operator assignment_expr
	;

assignment_operator
	: '='
	| LARROW_OP
	| MUL_ASSIGN
	| DIV_ASSIGN
	| MOD_ASSIGN
	| ADD_ASSIGN
	| SUB_ASSIGN
	;


logical_or_expr
	: logical_and_expr
	| logical_or_expr OR_OP logical_and_expr
	;

logical_and_expr
	: equality_expr
	| logical_and_expr AND_OP equality_expr
	;

equality_expr
	: relational_expr
	| equality_expr EQ_OP relational_expr
	| equality_expr NE_OP relational_expr
	;

relational_expr
	: additive_expr
	| relational_expr '<' additive_expr
	| relational_expr '>' additive_expr
	| relational_expr LE_OP additive_expr
	| relational_expr GE_OP additive_expr
	;

additive_expr
	: multiplicative_expr
	| additive_expr '+' multiplicative_expr
	| additive_expr '-' multiplicative_expr
	;

multiplicative_expr
	: unary_expr
	| multiplicative_expr '*' unary_expr
	| multiplicative_expr '/' unary_expr
	| multiplicative_expr '%' unary_expr
	;

unary_expr
	: postfix_expr
	| unary_operator unary_expr
	;

argument_expr_list
	: assignment_expr
	| argument_expr_list ',' assignment_expr
	;


primary_expr
	: IDENTIFIER
	| VARIABLE
	| NUMBER
	| STRING_LITERAL
	| '(' expr ')'
	;

array_idx_list
	: array_idx_element ',' array_idx_element
	;

array_idx_element
	: 
	| assignment_expr
	| assignment_expr ':'
	| ':' assignment_expr
	| assignment_expr ':' assignment_expr
	;

postfix_expr
	: primary_expr
	| postfix_expr '[' array_idx_list ']'
	| postfix_expr '(' ')'
	| postfix_expr '(' argument_expr_list ')'
	;

unary_operator
	: '+'
	| '-'
	;
%%

private Yylex lexer;

private String filename = null;

private boolean parseSuccess = true;

private int yylex () {
	int yyl_return = -1;
	try {
		yylval = new Term(Term.Type.NUMBER, 0.0);
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
