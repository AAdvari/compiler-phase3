// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: scanner.flex

/* this section will be added at the first of java file */
package scanner;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class LexicalAnalyser implements Lexical {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int CHARACTER = 2;
  public static final int STRING = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2, 2
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\2\3\1\4\22\0\1\1\1\5"+
    "\1\6\2\0\1\7\1\10\1\11\2\7\1\12\1\13"+
    "\1\7\1\14\1\15\1\16\1\17\11\20\1\0\1\7"+
    "\1\5\1\21\1\5\2\0\1\22\3\23\1\24\1\23"+
    "\14\25\1\26\4\25\1\27\2\25\1\7\1\30\2\7"+
    "\1\31\1\0\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\25\1\43\1\44\1\25\1\45"+
    "\1\46\1\47\1\25\1\50\1\51\1\52\1\53\1\54"+
    "\1\55\1\27\1\56\1\25\1\7\1\57\1\7\7\0"+
    "\1\3\u01a2\0\2\3\326\0\u0100\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\4\2\3\1\5\3\3"+
    "\2\6\17\7\1\3\1\1\1\10\1\11\1\12\1\1"+
    "\2\0\1\13\1\0\7\7\1\14\14\7\1\15\1\0"+
    "\2\16\1\0\1\6\7\7\1\14\11\7\1\16\1\0"+
    "\1\13\2\7\1\14\13\7\1\14\11\7\1\14\4\7"+
    "\1\14\2\7\1\14\26\7";

  private static int [] zzUnpackAction() {
    int [] result = new int[142];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\60\0\140\0\220\0\220\0\300\0\360\0\220"+
    "\0\220\0\u0120\0\220\0\u0150\0\u0180\0\u01b0\0\u01e0\0\u0210"+
    "\0\u0240\0\u0270\0\u02a0\0\u02d0\0\u0300\0\u0330\0\u0360\0\u0390"+
    "\0\u03c0\0\u03f0\0\u0420\0\u0450\0\u0480\0\u04b0\0\u04e0\0\u0510"+
    "\0\u0540\0\u0540\0\220\0\220\0\u0570\0\u05a0\0\u05d0\0\u0600"+
    "\0\u0630\0\u0660\0\u0690\0\u06c0\0\u06f0\0\u0720\0\u0750\0\u0780"+
    "\0\u0660\0\u07b0\0\u07e0\0\u0810\0\u0840\0\u0870\0\u08a0\0\u08d0"+
    "\0\u0900\0\u0930\0\u0960\0\u0990\0\u09c0\0\220\0\u09f0\0\220"+
    "\0\u0a20\0\u0a50\0\u0630\0\u0a80\0\u0ab0\0\u0ae0\0\u0b10\0\u0b40"+
    "\0\u0b70\0\u0ba0\0\u0a80\0\u0bd0\0\u0c00\0\u0c30\0\u0c60\0\u0c90"+
    "\0\u0cc0\0\u0cf0\0\u0d20\0\u0d50\0\u05a0\0\u0d80\0\u0d80\0\u0db0"+
    "\0\u0de0\0\u0db0\0\u0e10\0\u0e40\0\u0e70\0\u0ea0\0\u0ed0\0\u0f00"+
    "\0\u0f30\0\u0f60\0\u0f90\0\u0fc0\0\u0ff0\0\u0ff0\0\u1020\0\u1050"+
    "\0\u1080\0\u10b0\0\u10e0\0\u1110\0\u1140\0\u1170\0\u11a0\0\u1140"+
    "\0\u11d0\0\u1200\0\u1230\0\u1260\0\u1260\0\u1290\0\u12c0\0\u12c0"+
    "\0\u12f0\0\u1320\0\u1350\0\u1380\0\u13b0\0\u13e0\0\u1410\0\u1440"+
    "\0\u1470\0\u14a0\0\u14d0\0\u1500\0\u1530\0\u1560\0\u1590\0\u15c0"+
    "\0\u15f0\0\u1620\0\u1650\0\u1680\0\u16b0\0\220";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[142];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\1\4\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\7\1\14\1\15\1\11\1\16\1\17\1\20"+
    "\1\7\1\21\5\22\2\4\1\22\1\23\1\24\1\22"+
    "\1\25\1\26\2\22\1\27\1\22\1\30\1\31\1\22"+
    "\1\32\1\33\1\34\1\35\1\22\1\36\1\37\1\22"+
    "\1\40\2\41\3\4\4\41\1\42\46\41\6\43\1\44"+
    "\21\43\1\45\27\43\62\0\1\5\76\0\1\11\46\0"+
    "\1\11\62\0\1\11\5\0\1\11\52\0\1\11\4\0"+
    "\1\11\50\0\1\46\3\0\1\47\2\0\1\11\53\0"+
    "\1\50\1\0\2\20\6\0\1\51\45\0\1\50\1\0"+
    "\2\20\56\0\2\52\1\0\6\52\1\0\17\52\1\53"+
    "\6\52\20\0\2\52\1\0\6\52\1\0\26\52\20\0"+
    "\2\52\1\0\6\52\1\0\15\52\1\54\1\52\1\55"+
    "\6\52\20\0\2\52\1\0\6\52\1\0\13\52\1\56"+
    "\1\52\1\57\10\52\20\0\2\52\1\0\6\52\1\0"+
    "\13\52\1\60\12\52\20\0\2\52\1\0\6\52\1\0"+
    "\11\52\1\61\3\52\1\62\10\52\20\0\2\52\1\0"+
    "\6\52\1\0\6\52\1\61\5\52\1\63\11\52\20\0"+
    "\2\52\1\0\6\52\1\0\5\52\1\64\7\52\1\65"+
    "\10\52\20\0\2\52\1\0\6\52\1\0\5\52\1\66"+
    "\20\52\20\0\2\52\1\0\6\52\1\0\15\52\1\54"+
    "\1\52\1\67\6\52\20\0\2\52\1\0\6\52\1\0"+
    "\5\52\1\70\7\52\1\71\10\52\20\0\2\52\1\0"+
    "\6\52\1\0\21\52\1\72\4\52\20\0\2\52\1\0"+
    "\6\52\1\0\10\52\1\73\15\52\20\0\2\52\1\0"+
    "\6\52\1\0\15\52\1\74\10\52\20\0\2\52\1\0"+
    "\6\52\1\0\10\52\1\75\15\52\60\0\1\11\11\0"+
    "\1\76\54\0\1\43\36\0\1\43\2\0\1\43\1\0"+
    "\1\43\5\0\12\46\1\77\45\46\2\47\1\100\1\47"+
    "\1\101\53\47\17\0\2\50\3\0\1\102\11\0\1\102"+
    "\40\0\2\103\1\0\3\103\5\0\6\103\37\0\2\104"+
    "\1\0\6\104\1\0\26\104\20\0\2\104\1\0\6\104"+
    "\1\0\17\104\1\105\6\104\20\0\2\104\1\0\6\104"+
    "\1\0\15\104\1\106\10\104\20\0\2\104\1\0\6\104"+
    "\1\0\5\104\1\107\20\104\20\0\2\104\1\0\6\104"+
    "\1\0\1\104\1\110\24\104\20\0\2\104\1\0\6\104"+
    "\1\0\14\104\1\111\11\104\20\0\2\104\1\0\6\104"+
    "\1\0\20\104\1\112\5\104\20\0\2\104\1\0\6\104"+
    "\1\0\17\104\1\113\6\104\20\0\2\104\1\0\6\104"+
    "\1\0\1\114\20\104\1\113\4\104\20\0\2\104\1\0"+
    "\6\104\1\0\14\104\1\113\4\104\1\113\4\104\20\0"+
    "\2\104\1\0\6\104\1\0\15\104\1\115\10\104\20\0"+
    "\2\104\1\0\6\104\1\0\24\104\1\113\1\104\20\0"+
    "\2\104\1\0\6\104\1\0\11\104\1\116\14\104\20\0"+
    "\2\104\1\0\6\104\1\0\1\104\1\106\17\104\1\117"+
    "\4\104\20\0\2\104\1\0\6\104\1\0\6\104\1\113"+
    "\17\104\20\0\2\104\1\0\6\104\1\0\1\104\1\120"+
    "\15\104\1\121\6\104\20\0\2\104\1\0\6\104\1\0"+
    "\5\104\1\122\20\104\20\0\2\104\1\0\6\104\1\0"+
    "\11\104\1\123\14\104\20\0\2\104\1\0\6\104\1\0"+
    "\11\104\1\124\14\104\1\0\12\46\1\77\3\46\1\125"+
    "\41\46\2\0\1\100\70\0\2\126\2\0\2\127\56\0"+
    "\2\130\1\0\6\130\1\0\26\130\20\0\2\130\1\0"+
    "\6\130\1\0\1\130\1\131\24\130\20\0\2\130\1\0"+
    "\6\130\1\0\13\130\1\132\12\130\20\0\2\130\1\0"+
    "\6\130\1\0\1\130\1\133\24\130\20\0\2\130\1\0"+
    "\6\130\1\0\20\130\1\134\5\130\20\0\2\130\1\0"+
    "\6\130\1\0\21\130\1\135\4\130\20\0\2\130\1\0"+
    "\6\130\1\0\5\130\1\132\20\130\20\0\2\130\1\0"+
    "\4\130\1\136\1\130\1\0\11\130\1\137\14\130\20\0"+
    "\2\130\1\0\6\130\1\0\16\130\1\132\7\130\20\0"+
    "\2\130\1\0\6\130\1\0\14\130\1\140\11\130\20\0"+
    "\2\130\1\0\6\130\1\0\22\130\1\141\3\130\20\0"+
    "\2\130\1\0\6\130\1\0\21\130\1\142\4\130\20\0"+
    "\2\130\1\0\6\130\1\0\11\130\1\143\14\130\20\0"+
    "\2\130\1\0\6\130\1\0\14\130\1\132\11\130\20\0"+
    "\2\130\1\0\6\130\1\0\4\130\1\132\21\130\20\0"+
    "\2\130\1\0\6\130\1\0\13\130\1\144\12\130\20\0"+
    "\2\127\56\0\2\145\1\0\6\145\1\0\26\145\20\0"+
    "\2\145\1\0\6\145\1\0\25\145\1\146\20\0\2\145"+
    "\1\0\6\145\1\0\12\145\1\146\13\145\20\0\2\145"+
    "\1\0\6\145\1\0\20\145\1\146\5\145\20\0\2\145"+
    "\1\0\6\145\1\0\11\145\1\147\14\145\20\0\2\145"+
    "\1\0\6\145\1\0\21\145\1\150\4\145\20\0\2\145"+
    "\1\0\6\145\1\0\14\145\1\151\11\145\20\0\2\145"+
    "\1\0\6\145\1\0\21\145\1\146\4\145\20\0\2\145"+
    "\1\0\6\145\1\0\17\145\1\152\6\145\20\0\2\145"+
    "\1\0\6\145\1\0\11\145\1\153\14\145\20\0\2\145"+
    "\1\0\6\145\1\0\14\145\1\154\11\145\20\0\2\145"+
    "\1\0\6\145\1\0\5\145\1\146\20\145\20\0\2\155"+
    "\1\0\6\155\1\0\26\155\20\0\2\155\1\0\6\155"+
    "\1\0\14\155\1\156\11\155\20\0\2\155\1\0\6\155"+
    "\1\0\17\155\1\157\6\155\20\0\2\155\1\0\6\155"+
    "\1\0\21\155\1\160\4\155\20\0\2\155\1\0\6\155"+
    "\1\0\14\155\1\160\11\155\20\0\2\155\1\0\6\155"+
    "\1\0\3\155\1\160\22\155\20\0\2\155\1\0\6\155"+
    "\1\0\7\155\1\160\16\155\20\0\2\161\1\0\6\161"+
    "\1\0\26\161\20\0\2\161\1\0\6\161\1\0\22\161"+
    "\1\162\3\161\20\0\2\161\1\0\6\161\1\0\11\161"+
    "\1\163\14\161\20\0\2\164\1\0\6\164\1\0\26\164"+
    "\20\0\2\164\1\0\6\164\1\0\5\164\1\165\20\164"+
    "\20\0\2\164\1\0\6\164\1\0\14\164\1\166\11\164"+
    "\20\0\2\167\1\0\6\167\1\0\26\167\20\0\2\167"+
    "\1\0\6\167\1\0\7\167\1\170\16\167\20\0\2\171"+
    "\1\0\6\171\1\0\26\171\20\0\2\172\1\0\6\172"+
    "\1\0\26\172\20\0\2\173\1\0\6\173\1\0\26\173"+
    "\20\0\2\174\1\0\6\174\1\0\26\174\20\0\2\175"+
    "\1\0\6\175\1\0\26\175\20\0\2\176\1\0\6\176"+
    "\1\0\26\176\20\0\2\177\1\0\6\177\1\0\26\177"+
    "\20\0\2\200\1\0\6\200\1\0\26\200\20\0\2\201"+
    "\1\0\6\201\1\0\26\201\20\0\2\202\1\0\6\202"+
    "\1\0\26\202\20\0\2\203\1\0\6\203\1\0\26\203"+
    "\20\0\2\204\1\0\6\204\1\0\26\204\20\0\2\205"+
    "\1\0\6\205\1\0\26\205\20\0\2\206\1\0\6\206"+
    "\1\0\26\206\20\0\2\207\1\0\6\207\1\0\26\207"+
    "\20\0\2\210\1\0\6\210\1\0\26\210\20\0\2\211"+
    "\1\0\6\211\1\0\26\211\20\0\2\212\1\0\6\212"+
    "\1\0\26\212\20\0\2\213\1\0\6\213\1\0\26\213"+
    "\20\0\2\214\1\0\6\214\1\0\26\214\20\0\2\215"+
    "\1\0\6\215\1\0\26\215\20\0\2\216\1\0\6\216"+
    "\1\0\26\216\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5856];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\2\11\2\1\2\11\1\1\1\11\27\1\2\11"+
    "\1\1\2\0\1\1\1\0\24\1\1\11\1\0\1\11"+
    "\1\1\1\0\23\1\1\0\67\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[142];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
    public int ICV;
    public boolean logSymbols = true;
    public String string = "";
    public Symbol currentSymbol;
            public String nextToken(){
                try{
                    currentSymbol = next();
                    if(logSymbols)
                        System.out.println(current);
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


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public LexicalAnalyser(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public Symbol next() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { return new Symbol(yytext(),TokenType.UNDEFINED,yyline,yycolumn);
            }
            // fall through
          case 15: break;
          case 2:
            { yybegin(YYINITIAL);
            }
            // fall through
          case 16: break;
          case 3:
            { return new Symbol(yytext(),TokenType.OPERATOR,yyline,yycolumn);
            }
            // fall through
          case 17: break;
          case 4:
            { yybegin(STRING); yytext();
            }
            // fall through
          case 18: break;
          case 5:
            { yybegin(CHARACTER);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);
            }
            // fall through
          case 19: break;
          case 6:
            { return new Symbol(yytext(),TokenType.INTEGER,yyline,yycolumn);
            }
            // fall through
          case 20: break;
          case 7:
            { return new Symbol(yytext(),TokenType.IDENTIFIER,yyline,yycolumn);
            }
            // fall through
          case 21: break;
          case 8:
            { String s = yytext();
          yybegin(YYINITIAL);return new Symbol(s,TokenType.SPECIAL,yyline,yycolumn);
            }
            // fall through
          case 22: break;
          case 9:
            { string += yytext();
          yybegin(STRING);
            }
            // fall through
          case 23: break;
          case 10:
            { String temp = string;
                  string = "";
                  yybegin(YYINITIAL);
                  return new scanner.Symbol(temp,TokenType.STRING,yyline,yycolumn);
            }
            // fall through
          case 24: break;
          case 11:
            { return new Symbol(yytext(),TokenType.REAL,yyline,yycolumn);
            }
            // fall through
          case 25: break;
          case 12:
            { return new Symbol(yytext(),TokenType.RESERVED,yyline,yycolumn);
            }
            // fall through
          case 26: break;
          case 13:
            { yybegin(YYINITIAL);return new Symbol(yytext(),TokenType.STRING,yyline,yycolumn);
            }
            // fall through
          case 27: break;
          case 14:
            { return new Symbol(yytext(),TokenType.COMMENT,yyline,yycolumn);
            }
            // fall through
          case 28: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
