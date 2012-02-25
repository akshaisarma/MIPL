%token IDENTIFIER CONSTANT STRING_LITERAL SIZEOF
%token PTR_OP INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP MUL_ASSIGN DIV_ASSIGN MOD_ASSIGN ADD_ASSIGN
%token SUB_ASSIGN LEFT_ASSIGN RIGHT_ASSIGN AND_ASSIGN
%token XOR_ASSIGN OR_ASSIGN TYPE_NAME

%token TYPEDEF EXTERN STATIC AUTO REGISTER
%token CHAR SHORT INT LONG SIGNED UNSIGNED FLOAT DOUBLE CONST VOLATILE VOID
%token STRUCT UNION ENUM ELLIPSIS

%token CASE DEFAULT IF ELSE SWITCH WHILE DO FOR GOTO CONTINUE BREAK RETURN

%token REGEX VARIABLE INTEGER NUMBER NOT LARROW_OP IS

%start program
%%


program
	: lines
	;

lines
	: lines '\n' line
	| line
        | '\n'
	;

line
	: fact
	| query
	| rule
	| job
	;

fact
	: term '.'
	| '[' maf_list ']' LARROW_OP IDENTIFIER '(' maf_arg_list ')'
	;

maf_list
	: maf_list ',' IDENTIFIER
	| IDENTIFIER
	;

maf_arg_list
	: maf_arg_list ',' IDENTIFIER
	| maf_arg_list ',' NUMBER
	| IDENTIFIER
	| NUMBER
	;

query
	: term '?'
	;

rule
	: strict_term LARROW_OP or_terms '.'
	;

or_terms
	: and_terms
	| or_terms ';' and_terms
	;

and_terms
	: term
	| and_terms ',' term
	;

term
	: sub_term
        | REGEX
        | REGEX '(' term_arg_list ')'
        | REGEX '(' '*' ')'
        | NOT term
        | sub_term IS sub_term
        | sub_term '<' sub_term
        | sub_term '>' sub_term
        | sub_term LE_OP sub_term
        | sub_term GE_OP sub_term
        ;

sub_term
	: NUMBER
	| strict_term
	;

strict_term
	: IDENTIFIER
	| IDENTIFIER '(' strict_term_arg_list ')'
	| IDENTIFIER '(' '*' ')'
	| VARIABLE
	;

term_arg_cand
	: VARIABLE
	| strict_term_arg_cand
	;

strict_term_arg_cand
	: NUMBER
	| IDENTIFIER
        | STRING_LITERAL
	;

term_arg_list
	: term_arg_cand
	| term_arg_list ',' term_arg_cand
	;

strict_term_arg_list
	: strict_term_arg_cand
	| strict_term_arg_list ',' strict_term_arg_cand
	;

job
	: IDENTIFIER '(' job_arg_list ')' '{' stmt_list '}'
	;

job_arg_list
	: VARIABLE
	| job_arg_list ',' VARIABLE
	;

stmt
	: selection_stmt
	| return_stmt
	| expr_stmt
	| iteration_stmt
	;

stmt_list
	: stmt
	| stmt_list stmt
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
	| SWITCH '(' expr ')' stmt
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
	| unary_expr LARROW_OP assignment_expr
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
	| INC_OP unary_expr
	| DEC_OP unary_expr
	| unary_operator unary_expr
	;

argument_expr_list
	: assignment_expr
	| argument_expr_list ',' assignment_expr
	;


primary_expr
	: IDENTIFIER
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
	| postfix_expr INC_OP
	| postfix_expr DEC_OP
	;

unary_operator
	: '&'
	| '*'
	| '+'
	| '-'
	| '~'
	| '!'
	;
%%
#include <stdio.h>

extern char yytext[];
extern int column;

yyerror(s)
char *s;
{
	fflush(stdout);
	printf("\n%*s\n%*s\n", column, "^", column, s);
}

