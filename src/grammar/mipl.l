/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Yylex.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Akshai Sarma <as4107@columbia.edu>
 * Description: Automatically generated tokenizer from mipl.l
 */
package edu.columbia.mipl.syntax;
%%

%public
%final
%byaccj

%{
	/* store a reference to the parser object */
	private Parser yyparser;

	/* constructor taking an additional parser object */
	public Yylex(java.io.Reader r, Parser yyparser) {
		this(r);
		this.yyparser = yyparser;
	}
%}

NUM = [0-9]+ ("." [0-9]+)?
NL  = \n | \r | \r\n

%%

/* operators */
"+" | 
..
"(" | 
")"    { return (int) yycharat(0); }

	/* newline */
{NL}   { return ParserTokens.NUMBER; }

/* float */
{NUM}  { yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
	return ParserTokens.NUMBER; }


