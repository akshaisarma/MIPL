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
%}
%token IDENTIFIER STRING_LITERAL 
%token LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP MUL_ASSIGN DIV_ASSIGN MOD_ASSIGN ADD_ASSIGN SUB_ASSIGN

%token IF ELSE DO WHILE

%token REGEX VARIABLE NOT LARROW_OP IS
%token <dval> NUMBER

%token NULL

%start program
%%


program
	: lines
	;

lines
	: lines line
	| line
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
	: term '?'
	;

rule
	: term LARROW_OP or_terms '.'
	;

or_terms
	: and_terms
	| or_terms ';' and_terms
	;

and_terms
	: term
	| and_terms ',' term
	;

term /*% load into edu.columbia.mipl.runtime.Term */
	: NUMBER
	| IDENTIFIER
	| IDENTIFIER '(' arg_list ')'
	| IDENTIFIER '(' '*' ')'
	| VARIABLE
        | REGEX
        | REGEX '(' arg_list ')'
        | REGEX '(' '*' ')'
        | NOT term
        | IDENTIFIER IS term_expr
        | term_expr '<' term_expr
        | term_expr '>' term_expr
        | term_expr LE_OP term_expr
        | term_expr GE_OP term_expr
        | term_expr '=' term_expr
        | term_expr NE_OP term_expr
        ;

term_expr /*% load into edu.columbia.mipl.runtime.Expression */
	: term_expr '+' term_fact
	| term_expr '-' term_fact
	| term_fact
	;

term_fact
	: term_fact '*' term_term
	| term_fact '/' term_term
	| term_term
	;

term_term:
	| NUMBER
	| IDENTIFIER
	| '(' term_expr ')'
	;

arg_cand
	: IDENTIFIER
	| IDENTIFIER '(' arg_list ')'
	| VARIABLE
	| NUMBER
        | STRING_LITERAL
	;

arg_list
	: arg_cand
	| arg_list ',' arg_cand
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
	: array_idx_element
	| array_idx_list ',' array_idx_element
	;

array_idx_element
	: assignment_expr
	| assignment_expr ':'
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

private int yylex () {
	int yyl_return = -1;
	try {
		yylval = new ParserVal(0);
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

	throw new Error("Syntax Error: " + lexer.yytext());
}

public Parser(Reader r) {
	lexer = new Yylex(r, this);
}

public static void parse(InputStream in) throws IOException {
	new Parser(new InputStreamReader(System.in)).yyparse();
}

public static void parse(String file) throws IOException {
	Parser parser = new Parser(new FileReader(file));
	parser.yyparse();
	parser.filename = file;
}

