/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Yylex.java / mipl.jflex
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Akshai Sarma <as4107@columbia.edu>
 * Description: Automatically generated tokenizer from mipl.jflex
 *              PLEASE DO NOT MODIFY THIS Yylex.java FILE,
 *              INSTEAD, MODIFY mipl.jflex
 */

package edu.columbia.mipl.syntax;

import edu.columbia.mipl.runtime.*;

%%

%public
%final
%byaccj

%unicode
%line
%column

%{
	/* store a reference to the parser object */
	private Parser yyparser;

	/* constructor taking an additional parser object */
	public Yylex(java.io.Reader r, Parser yyparser) {
		this(r);
		this.yyparser = yyparser;
	}

	public int getLine() {
		return yyline + 1;
	}

	public int getColumn() {
		return yycolumn + 1;
	}

	private String getTrimmedString(String input) {
		return input.substring(1, input.length() - 1);
	}
%}

IDENTIFIER = [a-z][a-zA-Z_]*
STRING_LITERAL = \".*\" |
		\'.*\'

LE_OP = <=
GE_OP = >=
EQ_OP = ==
NE_OP = \!=

AND_OP = &&
OR_OP = \|\|
MUL_ASSIGN = \*=
DIV_ASSIGN = \/=
MOD_ASSIGN = %=
ADD_ASSIGN = \+=
SUB_ASSIGN = \-=

REGEX = \^.*\$
VARIABLE = [A-Z][a-zA-Z_]*
NUMBER = [0-9]+(.[0-9]+)?([Ee][+-]?[0-9]+)?
LARROW_OP = <- |
	:-

%%

/* operators */
"+" | 
"-" | 
"*" | 
"/" | 
"[" | 
"]" | 
"." | 
"," | 
"?" | 
"<" | 
">" | 
"=" | 
";" | 
"{" | 
"}" | 
"@" | 
"%" | 
"~" | 
"_" | 
"(" | 
")"    { return (int) yycharat(0); }

"if" { return ParserTokens.IF; }
"else" { return ParserTokens.ELSE; }
"do"  { return ParserTokens.DO; }
"while" { return ParserTokens.WHILE; }
"not" { return ParserTokens.NOT; }
"is" { return ParserTokens.IS; }
"job" { return ParserTokens.JOB; }

"true" { return ParserTokens.TRUE; }
"false" { return ParserTokens.FALSE; }

{STRING_LITERAL} { yyparser.yylval = getTrimmedString(yytext()); return ParserTokens.STRING_LITERAL; }

\#.*\n { /* Ignore Comments */ }

" "  |
"\t" |
"\r" |
"\n" { /* Ignore White Spaces */ } 

{LE_OP} { return ParserTokens.LE_OP; }
{GE_OP} { return ParserTokens.GE_OP; }
{EQ_OP} { return ParserTokens.EQ_OP; }
{NE_OP} { return ParserTokens.NE_OP; }
{AND_OP} { return ParserTokens.AND_OP; }
{OR_OP} { return ParserTokens.OR_OP; }
{LARROW_OP} { return ParserTokens.LARROW_OP; }

{MUL_ASSIGN} { return ParserTokens.MUL_ASSIGN; }
{DIV_ASSIGN} { return ParserTokens.DIV_ASSIGN; }
{MOD_ASSIGN} { return ParserTokens.MOD_ASSIGN; }
{ADD_ASSIGN} { return ParserTokens.ADD_ASSIGN; }
{SUB_ASSIGN} { return ParserTokens.SUB_ASSIGN; }

{REGEX} { yyparser.yylval = getTrimmedString(yytext()); return ParserTokens.REGEX; }

{VARIABLE} { yyparser.yylval = yytext(); return ParserTokens.VARIABLE; }

{NUMBER}  { yyparser.yylval = Double.parseDouble(yytext()); return ParserTokens.NUMBER; }

{IDENTIFIER} { yyparser.yylval = yytext(); return ParserTokens.IDENTIFIER; }

. { throw new java.io.IOException("Illegal character <" + yytext() + ">"); } 
