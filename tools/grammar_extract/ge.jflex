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

import java.io.*;

%%

%public
%final
%byaccj

%unicode
%line
%column

%{
	int nStage = 0;
	static FileWriter fw;

	static String resultString = "";

	public static void main(String[] args) {
		try {
			Yylex lexer = new Yylex(new FileReader(args[0]));
			fw = new FileWriter(args[1]);
			lexer.yylex();
			fw.write(resultString.replaceAll(" '", " `"));
			fw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
%}

SECTION = %%
ACTION = \{.*\}
COMMENT = [	 ]*\/\*.*\*\/
LCOMMENT = \/\/.*\n

%%

{SECTION} { nStage++; }
{ACTION}  { }
{COMMENT} { }
{LCOMMENT} { }
[ \n\r	] |
.	{ if (nStage == 1) resultString += yytext(); }


