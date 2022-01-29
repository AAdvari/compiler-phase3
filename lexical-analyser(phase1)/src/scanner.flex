/* this section will be added at the first of java file */
package app;
%%










%class Scanner
%public  //accessible from all over the program
%unicode

%line
%column

%function nextToken //the function name

%type Symbol

%state CHARACTER
%state STRING



/* **********  marcos ******** */

/* ********** general marcos ******** */
Digit = [0-9]
Letter = [a-zA-Z]
Sign = (\+|\-)
EveryThing = [^]
UnderLine = "_"
/* ********** input marcos ******** */
LineTerminator = \n|\r|\r\n
InputCharacter = [^\n\r]

/* ********** numbers ******** */
UnSignedInteger = {Digit}+
DecimalInteger = {Sign}?{Digit}+
RealNumbers = {DecimalInteger}((\.{UnSignedInteger}) | (\.))
HexadecimaNumber = {Sign}? 0 [Xx] [0-9A-Fa-f]+
ScientificNumber = {RealNumbers} [Ee] {Sign}? {DecimalInteger}

/* ********** Comments ******** */
SingleLineComment = "//" {InputCharacter}* {LineTerminator}
MultiLineComment = "/*" {EveryThing}* "*/"


/* ********** specia charachter ******** */
Space = [" "]      //Warninh
DoubleQoutes =  "\""
SingleQoutes = "\'"

/* ********** Identifier ******** */
Identifier = {Letter} ({Digit}|{UnderLine}|{Letter}){0,30}

/* ********** specia charachter ******** */
ReservedKeyWords = "let" | "void" | "int" | "real" | "bool" | "string" | "static" | "class" | "for" | "rof" | "loop" | "pool" | "while" | "break" | "continue" | "if" |
 "fi" | "else" |  "then" | "new" | "Array" | "return" | "in_String" | "in_int" | "print" | "len"
/**************** operators and punctuation  *****************/
Operator = "+" | "-" | "*" | "/" | "+=" | "-=" | "*=" | "/=" | "++" | "--" | "<" | "<=" | ">" | ">=" | "!=" | "==" | "="| "%" | "&&" | "||" | "&" | "|" | "^" | "!"
           | "." | "," | ";" | "[" | "]" | "(" | ")" | "{" | "}"
/**************** Error/Undifiend  *******************/
Undifiend = [^]

/* ***************************** Special Characters *********************************/
Special_Characters = "\\n" | "\\t" | "\\r" | "\\\""



%% /*  states */
<YYINITIAL> {
    {ReservedKeyWords} {return new Symbol(yytext(),TokenType.RESERVED,yyline,yycolumn);}
    {Operator} {return new Symbol(yytext(),TokenType.OPERATOR,yyline,yycolumn);}
    {Identifier} {return new Symbol(yytext(),TokenType.IDENTIFIER,yyline,yycolumn);}
    ({SingleLineComment} | {MultiLineComment}) {return new Symbol(yytext(),TokenType.COMMENT,yyline,yycolumn);}
    ({DecimalInteger}|{HexadecimaNumber}) {return new Symbol(yytext(),TokenType.INTEGER,yyline,yycolumn);}
    ({RealNumbers}|{ScientificNumber}) {return new Symbol(yytext(),TokenType.REAL,yyline,yycolumn);}
    /* ********** move to SPECIAL_CHARACHTER ********* */
    {LineTerminator} {return new Symbol("\n",TokenType.NEW_LINE,yyline,yycolumn); }
    "\t" {return new Symbol(yytext(),TokenType.TAB,yyline,yycolumn);}
    {Space} {return new Symbol(yytext(),TokenType.SPACE,yyline,yycolumn);}

      /* ********** move to String *********** */
     {DoubleQoutes} {yybegin(STRING);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}
      /* ********** move to SPECIAL_CHARACHTER ********* */
     {SingleQoutes} {yybegin(CHARACTER);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}
      /* ***********************  Undifiend  *********************** */
      {Undifiend} {return new Symbol(yytext(),TokenType.UNDEFINED,yyline,yycolumn);}
    }

    <STRING>{
        {DoubleQoutes} {yybegin(YYINITIAL);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}
        {Special_Characters} {return new Symbol(yytext(),TokenType.SPECIAL,yyline,yycolumn);}

        .                    {return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}
    }

    <CHARACTER> {

        {Special_Characters}{SingleQoutes} {
          String s = yytext();
          System.out.println(s);
          yybegin(YYINITIAL);return new Symbol(s,TokenType.SPECIAL,yyline,yycolumn);
      }
        .{SingleQoutes} {yybegin(YYINITIAL);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}

    }

    {Undifiend} {return new Symbol(yytext(),TokenType.UNDEFINED,yyline,yycolumn);}




   
