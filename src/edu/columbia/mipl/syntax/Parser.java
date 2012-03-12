//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package edu.columbia.mipl.syntax;



//#line 2 "/home/jung/plt/project/MIPL/src/grammar/mipl.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             implements ParserTokens
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    2,    3,    3,    8,
    8,    4,    5,   10,   10,   11,   11,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,   12,   12,   12,   13,   13,   13,
   14,   14,   14,   14,   15,   15,   15,   15,   15,    9,
    9,    6,   17,   17,   17,   17,   16,   16,   19,   20,
   18,   18,   18,   21,   21,   22,   22,   23,   23,   24,
   24,   26,   26,   27,   27,   27,   28,   28,   28,   28,
   28,   29,   29,   29,   30,   30,   30,   30,   25,   25,
   25,   25,   33,   33,   34,   34,   34,   34,   35,   35,
   36,   36,   36,   31,   31,   31,   31,   31,   31,   32,
   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    1,    1,    1,    2,    9,    3,
    1,    2,    4,    1,    3,    1,    3,    1,    1,    4,
    4,    1,    1,    1,    4,    4,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    1,    3,    3,    1,
    0,    1,    1,    3,    1,    4,    1,    1,    1,    1,
    3,    7,    1,    1,    1,    1,    1,    2,    3,    2,
    5,    7,    5,    5,    7,    1,    3,    1,    3,    1,
    3,    1,    3,    1,    3,    3,    1,    3,    3,    3,
    3,    1,    3,    3,    1,    3,    3,    3,    1,    2,
    2,    2,    1,    3,    1,    1,    1,    3,    1,    3,
    1,    2,    3,    1,    4,    3,    4,    2,    2,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   22,    0,    0,   23,    0,    0,    0,    0,
    3,    4,    5,    6,    7,    0,    0,    0,   40,    0,
    0,    0,    0,   27,   11,    0,   43,   42,    0,    2,
    0,    8,   12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   49,   47,   48,    0,    0,
   50,    0,    0,    0,    0,    0,   44,   16,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   38,   39,
    0,   21,    0,    0,   26,   25,    0,    0,   10,   13,
    0,    0,    0,    0,   51,   20,    0,    0,   17,   46,
   95,   97,    0,    0,    0,    0,    0,    0,   96,    0,
  111,  112,  113,    0,  110,  114,  115,    0,   57,   53,
   54,   55,   56,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  104,    0,   90,   91,    0,    0,
    0,    0,    0,    0,   52,   58,   60,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  108,  109,    0,    0,   92,    0,    0,    0,
    0,    0,   98,   59,   67,   85,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,   87,   88,
    0,    0,   99,  106,   93,    0,    0,    0,    0,    0,
    0,    0,  105,    0,  107,    0,    9,    0,   63,   64,
    0,  103,  100,   94,    0,    0,   62,   65,
};
final static short yydgoto[] = {                          9,
   10,   11,   12,   13,   14,   15,   16,   26,   50,   59,
   60,   17,   18,   19,   51,  108,  109,  110,  111,  112,
  113,  114,  115,  116,  117,  118,  119,  120,  121,  122,
  123,  124,  186,  125,  182,  183,
};
final static short yysindex[] = {                       -13,
  -34,  -37,    0,    0,   10,    0, -235,  -39,    0,  -13,
    0,    0,    0,    0,    0,  -12,   -8,   -9,    0,  -39,
   63,   77,  -26,    0,    0,    5,    0,    0,  178,    0,
   10,    0,    0,  -39,  -39,  -39,  -39,  -39,  -39,  -39,
  -39,  -39,  -39,   -3,   22,    0,    0,    0,   42,   55,
    0,   62,  166,   63, -199, -104,    0,    0,  -35,  125,
   -3,   -3,   -3,   -3,   -3,   -3,   -9,   -9,    0,    0,
 -255,    0,   65, -255,    0,    0,  172,  -51,    0,    0,
   10,   10,  184,  -17,    0,    0,  171,  125,    0,    0,
    0,    0,   37,   37,  177,  209,  212,  -17,    0,   37,
    0,    0,    0,   37,    0,    0,    0,  -33,    0,    0,
    0,    0,    0,  197,    0,  -23,  -64,   28, -239,   11,
  339,   27,  107,   37,    0, -255,    0,    0,   37,   37,
   37,  -45,  192,  321,    0,    0,    0,   37,   37,   37,
   37,   37,   37,   37,   37,   37,   37,   37,   37,   37,
   37,   37,    0,    0,   37,    3,    0,  245,  283,  306,
  308,  217,    0,    0,    0,    0,   28,    0, -239,   11,
   11,  339,  339,  339,  339,   27,   27,    0,    0,    0,
  215,   51,    0,    0,    0,  312,  235,  -17,  -17,  -17,
   37,   37,    0,   37,    0,   37,    0,   -2,    0,    0,
  318,    0,    0,    0,  -17,  271,    0,    0,
};
final static short yyrindex[] = {                       612,
  208,   99,    0,  134,  612,    0,    0,  469,    0,  373,
    0,    0,    0,    0,    0,    0,    0,   79,    0,   13,
    0,    0,  140,    0,    0,    0,    0,    0,    0,    0,
  612,    0,    0,   13,   13,   13,   13,   13,   13,   71,
   71,   71,   71,  145,  319,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -27,
  146,  168,  176,  272,  295,  298,  105,  111,    0,    0,
    0,    0,   48,    0,    0,    0,    0,    0,    0,    0,
  612,  612,    0,    0,    0,    0,    0,   38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  588,  399,  578,  550,  524,
  463,  427,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  587,    0,  565,  537,
  544,  483,  491,  511,  517,  437,  457,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   82,    0,    0,    0,    0,    0,  -25,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  313,    0,    0,    0,    0,  381,    0,  193,    0,
  249,  789,   67,   85,  269,    0,  199,    0,    0,    0,
    0,  322,  522,    0,  596,  227,  210,   -7,  -79,   12,
    0,    0,    0,    0,    0,  181,
};
final static int YYTABLESIZE=881;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        107,
    8,   45,   22,   46,  105,   21,  100,   61,  101,  102,
   80,  103,   61,   54,   61,  107,   61,   61,   14,   61,
  105,   25,  100,   81,  101,  102,    8,  103,  142,  143,
  104,   14,   42,   32,   40,  107,   41,   43,   61,   40,
  105,   41,  100,  184,  101,  102,  104,  103,   56,    8,
   33,   37,   39,   38,   41,   41,   41,   41,   41,   41,
   47,   71,   48,  152,  172,  173,  174,  175,  150,  107,
  146,   41,  147,  151,  105,   41,  100,    7,  101,  102,
   89,  103,   72,   15,   89,   89,   89,   89,   89,   89,
   89,  135,  106,   20,  194,   73,   15,   55,   74,   61,
   61,   89,   75,   89,   49,   89,   67,   68,  106,  101,
   20,   41,   41,   41,   41,   41,   41,   41,   52,   37,
   78,   37,   37,   37,   37,  102,   69,   70,  106,   41,
   41,   41,   41,   41,  170,  171,   89,   37,   37,   37,
   37,   37,   24,  193,   24,   35,  156,   35,   35,   35,
   35,   36,   79,   36,   36,   36,   36,   24,  101,  176,
  177,   24,  106,   35,   35,   35,   35,   35,   82,   36,
   36,   36,   36,   36,  102,   42,   42,   18,   42,   18,
   42,   43,   43,   19,   43,   19,   43,   84,   28,   31,
   28,   31,   18,   42,   42,   42,   18,  155,   19,   43,
   43,   43,   19,   28,   31,   87,   76,   28,   31,   74,
  126,   32,   86,   32,   53,   74,  129,   27,   57,   34,
   40,   34,   41,   91,   90,   92,   32,   74,   93,   94,
   32,   61,  163,   61,   34,  138,   61,   61,   34,   91,
  138,   92,  137,    1,   93,   94,   77,  139,  130,   43,
   43,  131,   43,   19,   43,  140,  191,   34,   35,   91,
   36,   92,  162,   83,   93,   94,   23,   43,   43,   43,
   19,   95,  192,   96,   97,   98,  144,  145,   28,   61,
  197,   61,   61,   61,   99,  187,   20,   95,   74,   96,
   97,   98,   61,   91,   20,   92,  132,  141,   93,   94,
   99,    2,    3,  205,    4,    5,  136,   31,    6,   89,
   89,   89,   89,   89,   89,   29,  208,   29,  158,   45,
   99,   46,   30,  188,    2,    3,  138,    4,    5,   88,
   29,    6,   41,   45,   29,   46,   41,   41,   30,   41,
   30,   33,   85,   33,   37,   37,  189,   37,  190,  138,
  169,  138,  195,   30,   99,  196,   33,   30,  206,   45,
   33,  138,   45,   89,  138,  167,  164,   20,  153,  154,
   35,   35,    1,   35,  203,    0,   36,   36,   47,   36,
   48,  148,    0,  149,    0,   24,  198,  199,  200,    0,
   41,    0,   47,    0,   48,    0,    0,    0,   37,   42,
   42,    0,   42,  207,    0,   43,   43,    0,   43,    0,
    0,   58,    0,    0,   41,   41,    0,   41,   24,   41,
    0,  133,    0,    0,   35,  134,    0,    0,    0,    0,
   36,    0,   41,   41,   41,   85,    0,    0,    0,   85,
   85,   85,   85,   85,   85,   85,    0,    0,    0,    0,
  159,  160,  161,   18,    0,    0,   85,    0,   85,   19,
   85,   58,   89,    0,   28,   31,    0,   82,    0,   82,
   82,   82,   82,   43,   43,    0,   43,   83,    0,   83,
   83,   83,   83,    0,   82,    0,   82,   32,   82,    0,
    0,   85,    0,    0,   83,   34,   83,   84,   83,   84,
   84,   84,   84,   77,    0,    0,   77,    0,   77,   41,
   41,   41,  201,   41,   84,   41,   84,    0,   84,   82,
   77,    0,   77,   80,   77,    0,   80,   19,   80,   83,
    0,   81,    0,    0,   81,    0,   81,    0,    0,    0,
   80,    0,   80,    0,   80,    0,    0,    0,   81,   84,
   81,   78,   81,    0,   78,   77,   78,   79,    0,    0,
   79,    0,   79,    0,   74,    0,    0,   74,   78,   74,
   78,    0,   78,    0,   79,   80,   79,   75,   79,    0,
   75,   74,   75,   81,   76,    0,    0,   76,    0,   76,
   72,   29,    0,   72,   75,   72,    0,    0,    0,    0,
    0,   76,    0,   78,    0,   73,    0,   72,   73,   79,
   73,    0,    0,    0,   30,    0,   74,   33,   70,    0,
    0,   70,   73,   70,    0,    0,    0,   71,   68,   75,
   71,   68,   71,   68,    0,   70,   76,    0,   41,   41,
    0,   41,   72,    0,   71,   68,    0,    0,    0,    0,
    0,    0,    0,   41,   41,    0,   41,   73,   41,  165,
    0,  168,    0,    0,   85,   85,   85,   85,   85,   85,
   70,   41,   41,   41,    0,    0,  181,  185,    0,   71,
   68,    0,    0,    0,    0,    0,    0,    0,  127,  128,
    0,    0,   82,   82,   82,   82,   82,   82,    0,    0,
    0,    0,   83,   83,   83,   83,   83,   83,    0,    0,
    0,    0,    0,  202,    0,  181,    0,  204,    0,  157,
    0,    0,   84,   84,   84,   84,   84,   84,   77,   77,
   77,   77,   77,   77,  166,    0,  166,  166,  166,  166,
  166,  166,  166,  166,  166,  178,  179,  180,   80,   80,
   80,   80,   80,   80,    0,    0,   81,   81,   81,   81,
   81,   81,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   78,   78,   78,   78,
   78,   78,   79,   79,   79,   79,   79,   79,    0,    0,
    0,   74,   74,   74,   74,    0,   29,    0,    0,    0,
    0,    0,    0,    0,   75,   75,   75,   75,   44,    0,
    0,   76,   76,   76,   76,    0,    0,    0,    0,   72,
   72,    0,   61,   62,   63,   64,   65,   66,    0,    0,
    0,    0,    0,    0,   73,   73,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   70,    0,
    0,    0,    0,    0,    0,    0,    0,   71,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,   41,    0,
   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   40,  257,   40,  259,   38,   40,   40,   33,   42,   43,
   46,   45,   38,   40,   40,   33,   42,   43,   46,   45,
   38,  257,   40,   59,   42,   43,   40,   45,  268,  269,
   64,   59,   42,   46,   43,   33,   45,   47,   64,   43,
   38,   45,   40,   41,   42,   43,   64,   45,   44,   40,
   63,   60,   61,   62,   42,   43,   44,   45,   46,   47,
  316,   40,  318,   37,  144,  145,  146,  147,   42,   33,
   60,   59,   62,   47,   38,   63,   40,   91,   42,   43,
   37,   45,   41,   46,   41,   42,   43,   44,   45,   46,
   47,  125,  126,   46,   44,   41,   59,   93,   44,  125,
  126,   58,   41,   60,   42,   62,   40,   41,  126,   44,
   63,   41,   42,   43,   44,   45,   46,   47,   42,   41,
  320,   43,   44,   45,   46,   44,   42,   43,  126,   59,
   60,   61,   62,   63,  142,  143,   93,   59,   60,   61,
   62,   63,   44,   93,   46,   41,   40,   43,   44,   45,
   46,   41,  257,   43,   44,   45,   46,   59,   93,  148,
  149,   63,  126,   59,   60,   61,   62,   63,   44,   59,
   60,   61,   62,   63,   93,   42,   43,   44,   45,   46,
   47,   42,   43,   44,   45,   46,   47,  123,   44,   44,
   46,   46,   59,   60,   61,   62,   63,   91,   59,   60,
   61,   62,   63,   59,   59,  257,   41,   63,   63,   44,
   40,   44,   41,   46,   22,   44,   40,  257,   41,   44,
   43,   46,   45,  257,   41,  259,   59,   44,  262,  263,
   63,  257,   41,  259,   59,   44,  262,  263,   63,  257,
   44,  259,   46,  257,  262,  263,   54,  271,   40,   42,
   43,   40,   45,   46,   47,  320,   40,  266,  267,  257,
  269,  259,  308,   71,  262,  263,  257,   60,   61,   62,
   63,  305,   58,  307,  308,  309,  266,  267,  318,  305,
   46,  307,  308,  309,  318,   41,  321,  305,   44,  307,
  308,  309,  318,  257,  321,  259,   98,  270,  262,  263,
  318,  315,  316,  306,  318,  319,  108,  320,  322,  266,
  267,  268,  269,  270,  271,   44,   46,   46,  126,  257,
  318,  259,   10,   41,  315,  316,   44,  318,  319,   81,
   59,  322,  320,  257,   63,  259,  266,  267,   44,  269,
   46,   44,   74,   46,  266,  267,   41,  269,   41,   44,
  141,   44,   41,   59,  318,   44,   59,   63,   41,   41,
   63,   44,   44,  320,   44,  139,   46,  320,  262,  263,
  266,  267,    0,  269,  194,   -1,  266,  267,  316,  269,
  318,   43,   -1,   45,   -1,    5,  188,  189,  190,   -1,
  320,   -1,  316,   -1,  318,   -1,   -1,   -1,  320,  266,
  267,   -1,  269,  205,   -1,  266,  267,   -1,  269,   -1,
   -1,   31,   -1,   -1,   42,   43,   -1,   45,  320,   47,
   -1,  100,   -1,   -1,  320,  104,   -1,   -1,   -1,   -1,
  320,   -1,   60,   61,   62,   37,   -1,   -1,   -1,   41,
   42,   43,   44,   45,   46,   47,   -1,   -1,   -1,   -1,
  129,  130,  131,  320,   -1,   -1,   58,   -1,   60,  320,
   62,   81,   82,   -1,  320,  320,   -1,   41,   -1,   43,
   44,   45,   46,  266,  267,   -1,  269,   41,   -1,   43,
   44,   45,   46,   -1,   58,   -1,   60,  320,   62,   -1,
   -1,   93,   -1,   -1,   58,  320,   60,   41,   62,   43,
   44,   45,   46,   41,   -1,   -1,   44,   -1,   46,   41,
   42,   43,  191,   45,   58,   47,   60,   -1,   62,   93,
   58,   -1,   60,   41,   62,   -1,   44,  320,   46,   93,
   -1,   41,   -1,   -1,   44,   -1,   46,   -1,   -1,   -1,
   58,   -1,   60,   -1,   62,   -1,   -1,   -1,   58,   93,
   60,   41,   62,   -1,   44,   93,   46,   41,   -1,   -1,
   44,   -1,   46,   -1,   41,   -1,   -1,   44,   58,   46,
   60,   -1,   62,   -1,   58,   93,   60,   41,   62,   -1,
   44,   58,   46,   93,   41,   -1,   -1,   44,   -1,   46,
   41,  320,   -1,   44,   58,   46,   -1,   -1,   -1,   -1,
   -1,   58,   -1,   93,   -1,   41,   -1,   58,   44,   93,
   46,   -1,   -1,   -1,  320,   -1,   93,  320,   41,   -1,
   -1,   44,   58,   46,   -1,   -1,   -1,   41,   41,   93,
   44,   44,   46,   46,   -1,   58,   93,   -1,  266,  267,
   -1,  269,   93,   -1,   58,   58,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   93,   47,  138,
   -1,  140,   -1,   -1,  266,  267,  268,  269,  270,  271,
   93,   60,   61,   62,   -1,   -1,  155,  156,   -1,   93,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   94,
   -1,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   -1,   -1,  192,   -1,  194,   -1,  196,   -1,  124,
   -1,   -1,  266,  267,  268,  269,  270,  271,  266,  267,
  268,  269,  270,  271,  139,   -1,  141,  142,  143,  144,
  145,  146,  147,  148,  149,  150,  151,  152,  266,  267,
  268,  269,  270,  271,   -1,   -1,  266,  267,  268,  269,
  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,  268,  269,
  270,  271,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,  268,  269,  270,  271,   -1,    8,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  268,  269,  270,  271,   20,   -1,
   -1,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  270,
  271,   -1,   34,   35,   36,   37,   38,   39,   -1,   -1,
   -1,   -1,   -1,   -1,  270,  271,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,
  269,
};
}
final static short YYFINAL=9;
final static short YYMAXTOKEN=322;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'","'&'",null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'","'@'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'{'",null,"'}'","'~'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"IDENTIFIER","CONSTANT",
"STRING_LITERAL","SIZEOF","PTR_OP","INC_OP","DEC_OP","LEFT_OP","RIGHT_OP",
"LE_OP","GE_OP","EQ_OP","NE_OP","AND_OP","OR_OP","MUL_ASSIGN","DIV_ASSIGN",
"MOD_ASSIGN","ADD_ASSIGN","SUB_ASSIGN","LEFT_ASSIGN","RIGHT_ASSIGN",
"AND_ASSIGN","XOR_ASSIGN","OR_ASSIGN","TYPE_NAME","TYPEDEF","EXTERN","STATIC",
"AUTO","REGISTER","CHAR","SHORT","INT","LONG","SIGNED","UNSIGNED","FLOAT",
"DOUBLE","CONST","VOLATILE","VOID","STRUCT","UNION","ENUM","ELLIPSIS","CASE",
"DEFAULT","IF","ELSE","SWITCH","WHILE","DO","FOR","GOTO","CONTINUE","BREAK",
"RETURN","REGEX","VARIABLE","INTEGER","NUMBER","NOT","LARROW_OP","IS","NEWLINE",
};
final static String yyrule[] = {
"$accept : program",
"program : lines",
"lines : lines line",
"lines : line",
"line : fact",
"line : query",
"line : rule",
"line : job",
"fact : term '.'",
"fact : '[' maf_list ']' LARROW_OP IDENTIFIER '(' arg_list ')' '.'",
"maf_list : maf_list ',' IDENTIFIER",
"maf_list : IDENTIFIER",
"query : term '?'",
"rule : term LARROW_OP or_terms '.'",
"or_terms : and_terms",
"or_terms : or_terms ';' and_terms",
"and_terms : term",
"and_terms : and_terms ',' term",
"term : NUMBER",
"term : IDENTIFIER",
"term : IDENTIFIER '(' arg_list ')'",
"term : IDENTIFIER '(' '*' ')'",
"term : VARIABLE",
"term : NEWLINE",
"term : REGEX",
"term : REGEX '(' arg_list ')'",
"term : REGEX '(' '*' ')'",
"term : NOT term",
"term : IDENTIFIER IS term_expr",
"term : term_expr '<' term_expr",
"term : term_expr '>' term_expr",
"term : term_expr LE_OP term_expr",
"term : term_expr GE_OP term_expr",
"term : term_expr '=' term_expr",
"term : term_expr NE_OP term_expr",
"term_expr : term_expr '+' term_fact",
"term_expr : term_expr '-' term_fact",
"term_expr : term_fact",
"term_fact : term_fact '*' term_term",
"term_fact : term_fact '/' term_term",
"term_fact : term_term",
"term_term :",
"term_term : NUMBER",
"term_term : IDENTIFIER",
"term_term : '(' term_expr ')'",
"arg_cand : IDENTIFIER",
"arg_cand : IDENTIFIER '(' arg_list ')'",
"arg_cand : VARIABLE",
"arg_cand : NUMBER",
"arg_cand : STRING_LITERAL",
"arg_list : arg_cand",
"arg_list : arg_list ',' arg_cand",
"job : IDENTIFIER '(' arg_list ')' '{' stmt_list '}'",
"stmt : selection_stmt",
"stmt : return_stmt",
"stmt : expr_stmt",
"stmt : iteration_stmt",
"stmt_list : stmt",
"stmt_list : stmt_list stmt",
"return_stmt : '@' expr '.'",
"expr_stmt : expr '.'",
"selection_stmt : IF '(' expr ')' stmt",
"selection_stmt : IF '(' expr ')' stmt ELSE stmt",
"selection_stmt : SWITCH '(' expr ')' stmt",
"iteration_stmt : WHILE '(' expr ')' stmt",
"iteration_stmt : DO stmt WHILE '(' expr ')' '.'",
"expr : assignment_expr",
"expr : expr ',' assignment_expr",
"assignment_expr : logical_or_expr",
"assignment_expr : unary_expr LARROW_OP assignment_expr",
"logical_or_expr : logical_and_expr",
"logical_or_expr : logical_or_expr OR_OP logical_and_expr",
"logical_and_expr : equality_expr",
"logical_and_expr : logical_and_expr AND_OP equality_expr",
"equality_expr : relational_expr",
"equality_expr : equality_expr EQ_OP relational_expr",
"equality_expr : equality_expr NE_OP relational_expr",
"relational_expr : additive_expr",
"relational_expr : relational_expr '<' additive_expr",
"relational_expr : relational_expr '>' additive_expr",
"relational_expr : relational_expr LE_OP additive_expr",
"relational_expr : relational_expr GE_OP additive_expr",
"additive_expr : multiplicative_expr",
"additive_expr : additive_expr '+' multiplicative_expr",
"additive_expr : additive_expr '-' multiplicative_expr",
"multiplicative_expr : unary_expr",
"multiplicative_expr : multiplicative_expr '*' unary_expr",
"multiplicative_expr : multiplicative_expr '/' unary_expr",
"multiplicative_expr : multiplicative_expr '%' unary_expr",
"unary_expr : postfix_expr",
"unary_expr : INC_OP unary_expr",
"unary_expr : DEC_OP unary_expr",
"unary_expr : unary_operator unary_expr",
"argument_expr_list : assignment_expr",
"argument_expr_list : argument_expr_list ',' assignment_expr",
"primary_expr : IDENTIFIER",
"primary_expr : NUMBER",
"primary_expr : STRING_LITERAL",
"primary_expr : '(' expr ')'",
"array_idx_list : array_idx_element",
"array_idx_list : array_idx_list ',' array_idx_element",
"array_idx_element : assignment_expr",
"array_idx_element : assignment_expr ':'",
"array_idx_element : assignment_expr ':' assignment_expr",
"postfix_expr : primary_expr",
"postfix_expr : postfix_expr '[' array_idx_list ']'",
"postfix_expr : postfix_expr '(' ')'",
"postfix_expr : postfix_expr '(' argument_expr_list ')'",
"postfix_expr : postfix_expr INC_OP",
"postfix_expr : postfix_expr DEC_OP",
"unary_operator : '&'",
"unary_operator : '*'",
"unary_operator : '+'",
"unary_operator : '-'",
"unary_operator : '~'",
"unary_operator : '!'",
};

//#line 248 "/home/jung/plt/project/MIPL/src/grammar/mipl.y"

private Yylex lexer;

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

public void yyerror (String error) {
	System.err.println ("Error: " + error);
}

public Parser(Reader r) {
	lexer = new Yylex(r, this);
}

static boolean interactive;

public static void parse() throws IOException {
	parse(null);
}

public static void parse(String filename) throws IOException {
	Parser yyparser;
	if (filename == null) {
		// parse a file
		yyparser = new Parser(new FileReader(filename));
	}
	else {
		// interactive mode
		interactive = true;
		yyparser = new Parser(new InputStreamReader(System.in));
	}

	yyparser.yyparse();
}

//#line 648 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
