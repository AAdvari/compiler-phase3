/* this section will be added at the first of java file */
%%



%class LexicalAnalyser
%implements Lexical
%public  //accessible from all over the program
%unicode

%line
%column

%function next //the function name

%type Symbol

%state CHARACTER
%state STRING

%{
    public int ICV;
    public String string = "";
            public String nextToken(){
                try{
                    Symbol current = next();
                    if (current == null)
                        return "$";
                    switch (current.getType()){
                        case RESERVED:
                            return current.getToken();
                        case OPERATOR:
                            if (current.getToken().equals(","))
                                return "comma";
                            return current.getToken();
                        case IDENTIFIER:
                            if(current.getToken().equals("in_int") || current.getToken().equals("in_string"))
                                return current.getToken();
                            return "id";
                        case STRING:
                            return "string";
                        case INTEGER:
                        case REAL:
                            return "id";
                    }
                    return nextToken();

                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
%}

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
DecimalInteger = {Digit}+
RealNumbers = {DecimalInteger}((\.{UnSignedInteger}) | (\.))
HexadecimaNumber =  0 [Xx] [0-9A-Fa-f]+
ScientificNumber = {RealNumbers} [Ee] {Sign}? {DecimalInteger}

/* ********** Comments ******** */
SingleLineComment = "//" {InputCharacter}* {LineTerminator}
MultiLineComment = "/*" {EveryThing}* "*/"


/* ********** specia charachter ******** */
Space = [" "]      //Warning
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
/**************** Error/Undefined  *******************/
Undefined = [^]

/* ***************************** Special Characters *********************************/
Special_Characters = "\\n" | "\\t" | "\\r" | "\\\""



%% /*  states */
<YYINITIAL> {
    {ReservedKeyWords} {return new Symbol(yytext(),src.TokenType.RESERVED,yyline,yycolumn);}
    {Operator} {return new Symbol(yytext(),TokenType.OPERATOR,yyline,yycolumn);}
    {Identifier} {return new Symbol(yytext(),TokenType.IDENTIFIER,yyline,yycolumn);}
    ({SingleLineComment} | {MultiLineComment}) {return new Symbol(yytext(),TokenType.COMMENT,yyline,yycolumn);}
    ({DecimalInteger}|{HexadecimaNumber}) {return new Symbol(yytext(),TokenType.INTEGER,yyline,yycolumn);}
    ({RealNumbers}|{ScientificNumber}) {return new Symbol(yytext(),TokenType.REAL,yyline,yycolumn);}
    /* ********** move to SPECIAL_CHARACHTER ********* */
    {LineTerminator} {yybegin(YYINITIAL);}
    "\t" {yybegin(YYINITIAL);}
    {Space} {yybegin(YYINITIAL);}

      /* ********** move to String *********** */
     {DoubleQoutes} {yybegin(STRING); yytext();}
      /* ********** move to SPECIAL_CHARACHTER ********* */
     {SingleQoutes} {yybegin(CHARACTER);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}
      /* ***********************  Undefined  *********************** */
      {Undefined} {return new Symbol(yytext(),TokenType.UNDEFINED,yyline,yycolumn);}
    }

    <STRING>{
        {DoubleQoutes} {
          String temp = string;
          string = "";
          yybegin(YYINITIAL);
          return new Symbol(temp,TokenType.STRING,yyline,yycolumn);
         }
        {Special_Characters} {
          string += yytext();
          yybegin(STRING);
        }

        [^\\] {
          string += yytext();
          yybegin(STRING);
        }
    }

    <CHARACTER> {

        {SingleQoutes} {
          String s = yytext();
          yybegin(YYINITIAL);return new Symbol(s,TokenType.SPECIAL,yyline,yycolumn);
      }
        .{SingleQoutes} {yybegin(YYINITIAL);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);}

    }

    {Undefined} {return new Symbol(yytext(),TokenType.UNDEFINED,yyline,yycolumn);}





