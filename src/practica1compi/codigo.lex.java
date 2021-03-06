//import java.io.File;
import java.io.File;
import java.io.FileReader;
import java.lang.System;
import javax.swing.JFileChooser;

class Sample {
    public static void main(String argv[]) throws java.io.IOException {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(file);
        File archivo = file.getSelectedFile();
        FileReader archivos = new FileReader(archivo);        
	Yylex yy = new Yylex(archivos);
	Yytoken t;
	while ((t = yy.yylex()) != null)
	    System.out.println(t);
    }
}
class Utility {
  public static void Analizador
    (
     boolean expr
     )
      { 
	if (false == expr) {
	  throw (new Error("Error: Assertion failed."));
	}
      }
  private static final String errorMsg[] = {
    "Error: Unmatched end-of-comment punctuation.",
    "Error: Unmatched start-of-comment punctuation.",
    "Error: Unclosed string.",
    "Error: Illegal character."
    };
  public static final int E_ENDCOMMENT = 0; 
  public static final int E_STARTCOMMENT = 1; 
  public static final int E_UNCLOSEDSTR = 2; 
  public static final int E_UNMATCHED = 3; 
  public static void error
    (
     int code
     )
      {
	System.out.println(errorMsg[code]);
      }
}
class Yytoken {
  Yytoken 
    (
     int index,
     String text,
     int line,
     int charBegin,
     int charEnd
     )
      {
	m_index = index;
	m_text = new String(text);
	m_line = line;
	m_charBegin = charBegin;
	m_charEnd = charEnd;
      }
  public int m_index;
  public String m_text;
  public int m_line;
  public int m_charBegin;
  public int m_charEnd;
  public String toString() {
      return "Token #"+m_index+": "+m_text+" (line "+m_line+")";
  }
}


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  private int comment_count = 0;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		39
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NOT_ACCEPT,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"42:8,34:2,35,42:2,36,42:18,34,42,37,27,31,42,18,42,4,5,13,11,1,12,10,14,39:" +
"10,2,3,16,15,17,42:2,40:5,30,40:2,29,40:17,6,38,7,42,41,42,40:4,24,28,40,21" +
",22,40:2,23,40:3,32,40:2,25,26,33,40,20,40:3,8,19,9,42:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,66,
"0,1:2,2,1:11,3,1,4,5,1:2,6,1:2,7,8,1,9,1:5,10:2,1,10:3,11,12,1:2,13,14,15,1" +
"6,8,12,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,10:2")[0];

	private int yy_nxt[][] = unpackFromString(31,43,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,65,44,65:2,59,65,22,6" +
"5,50,65,23,62,65,24:2,-1,25,26,27,65,26:2,-1:58,28,-1:40,29,-1:44,30,-1,31," +
"-1:40,32,-1:47,65,63,65:5,-1,65:3,-1,65:2,-1:5,64,65,64,-1:35,24:2,-1:8,25:" +
"34,-1,25,35,45,25:4,-1:39,27,-1:23,65:7,-1,65:3,-1,65:2,-1:5,64,65,64,-1,1," +
"48:12,46,52,48:12,40,48:7,41,48:7,-1,48:12,49,54,48:20,-1,48:7,-1:34,43:2,-" +
"1:2,25,-1:24,65:7,-1,33,65:2,-1,65:2,-1:5,64,65,64,-1:2,25:33,51,43,25,47,4" +
"5,25:4,-1,48:12,53,-1,48:20,-1,48:7,-1:20,65:7,-1,65:2,34,-1,65:2,-1:5,64,6" +
"5,64,-1:2,25:33,51,43,25,35,45,25:4,-1,48:12,42,56,48:20,-1,48:7,-1,48:12,5" +
"3,54,48:20,-1,48:7,-1,48:12,-1,56,48:20,-1,48:7,-1:20,65:6,36,-1,65:3,-1,65" +
":2,-1:5,64,65,64,-1:2,48:12,49,56,48:20,-1,48:7,-1:20,65:5,37,65,-1,65:3,-1" +
",65:2,-1:5,64,65,64,-1:21,65:4,38,65:2,-1,65:3,-1,65:2,-1:5,64,65,64,-1:21," +
"65:4,55,65:2,-1,65:3,-1,65:2,-1:5,64,65,64,-1:21,65:6,57,-1,65:3,-1,65:2,-1" +
":5,64,65,64,-1:21,65:3,58,65:3,-1,65:3,-1,65:2,-1:5,64,65,64,-1:21,65:7,-1," +
"65:3,-1,65,60,-1:5,64,65,64,-1:21,65:2,61,65:4,-1,65:3,-1,65:2,-1:5,64,65,6" +
"4,-1");

	public Yytoken yylex ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return (new Yytoken(0,yytext(),yyline,yychar,yychar+1)); }
					case -3:
						break;
					case 3:
						{ return (new Yytoken(1,yytext(),yyline,yychar,yychar+1)); }
					case -4:
						break;
					case 4:
						{ return (new Yytoken(2,yytext(),yyline,yychar,yychar+1)); }
					case -5:
						break;
					case 5:
						{ return (new Yytoken(3,yytext(),yyline,yychar,yychar+1)); }
					case -6:
						break;
					case 6:
						{ return (new Yytoken(4,yytext(),yyline,yychar,yychar+1)); }
					case -7:
						break;
					case 7:
						{ return (new Yytoken(5,yytext(),yyline,yychar,yychar+1)); }
					case -8:
						break;
					case 8:
						{ return (new Yytoken(6,yytext(),yyline,yychar,yychar+1)); }
					case -9:
						break;
					case 9:
						{ return (new Yytoken(7,yytext(),yyline,yychar,yychar+1)); }
					case -10:
						break;
					case 10:
						{ return (new Yytoken(8,yytext(),yyline,yychar,yychar+1)); }
					case -11:
						break;
					case 11:
						{ return (new Yytoken(9,yytext(),yyline,yychar,yychar+1)); }
					case -12:
						break;
					case 12:
						{ return (new Yytoken(10,yytext(),yyline,yychar,yychar+1)); }
					case -13:
						break;
					case 13:
						{ return (new Yytoken(11,yytext(),yyline,yychar,yychar+1)); }
					case -14:
						break;
					case 14:
						{ return (new Yytoken(12,yytext(),yyline,yychar,yychar+1)); }
					case -15:
						break;
					case 15:
						{ return (new Yytoken(13,yytext(),yyline,yychar,yychar+1)); }
					case -16:
						break;
					case 16:
						{ return (new Yytoken(14,yytext(),yyline,yychar,yychar+1)); }
					case -17:
						break;
					case 17:
						{ return (new Yytoken(16,yytext(),yyline,yychar,yychar+1)); }
					case -18:
						break;
					case 18:
						{ return (new Yytoken(18,yytext(),yyline,yychar,yychar+1)); }
					case -19:
						break;
					case 19:
						{ return (new Yytoken(20,yytext(),yyline,yychar,yychar+1)); }
					case -20:
						break;
					case 20:
						{ return (new Yytoken(21,yytext(),yyline,yychar,yychar+1)); }
					case -21:
						break;
					case 21:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -22:
						break;
					case 22:
						{ return (new Yytoken(25,yytext(),yyline,yychar,yychar+1)); }
					case -23:
						break;
					case 23:
						{ return (new Yytoken(28,yytext(),yyline,yychar,yychar+1)); }
					case -24:
						break;
					case 24:
						{ }
					case -25:
						break;
					case 25:
						{
	String str =  yytext().substring(1,yytext().length());
	Utility.error(Utility.E_UNCLOSEDSTR);
	Utility.Analizador(str.length() == yytext().length() - 1);
	return (new Yytoken(41,str,yyline,yychar,yychar + str.length()));
}
					case -26:
						break;
					case 26:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED);
}
					case -27:
						break;
					case 27:
						{ 
	return (new Yytoken(42,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -28:
						break;
					case 28:
						{ return (new Yytoken(22,yytext(),yyline,yychar,yychar+2)); }
					case -29:
						break;
					case 29:
						{ yybegin(COMMENT); comment_count = comment_count + 1; }
					case -30:
						break;
					case 30:
						{ return (new Yytoken(17,yytext(),yyline,yychar,yychar+2)); }
					case -31:
						break;
					case 31:
						{ return (new Yytoken(15,yytext(),yyline,yychar,yychar+2)); }
					case -32:
						break;
					case 32:
						{ return (new Yytoken(19,yytext(),yyline,yychar,yychar+2)); }
					case -33:
						break;
					case 33:
						{ return (new Yytoken(26,yytext(),yyline,yychar,yychar+1)); }
					case -34:
						break;
					case 34:
						{ return (new Yytoken(27,yytext(),yyline,yychar,yychar+1)); }
					case -35:
						break;
					case 35:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	Utility.Analizador(str.length() == yytext().length() - 2);
	return (new Yytoken(40,str,yyline,yychar,yychar + str.length()));
}
					case -36:
						break;
					case 36:
						{ return (new Yytoken(24,yytext(),yyline,yychar,yychar+1)); }
					case -37:
						break;
					case 37:
						{ return (new Yytoken(29,yytext(),yyline,yychar,yychar+1)); }
					case -38:
						break;
					case 38:
						{ return (new Yytoken(23,yytext(),yyline,yychar,yychar+1)); }
					case -39:
						break;
					case 39:
						{ }
					case -40:
						break;
					case 40:
						{ comment_count = comment_count + 1; }
					case -41:
						break;
					case 41:
						{ }
					case -42:
						break;
					case 42:
						{ comment_count = comment_count + 1; }
					case -43:
						break;
					case 44:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -44:
						break;
					case 45:
						{
	String str =  yytext().substring(1,yytext().length());
	Utility.error(Utility.E_UNCLOSEDSTR);
	Utility.Analizador(str.length() == yytext().length() - 1);
	return (new Yytoken(41,str,yyline,yychar,yychar + str.length()));
}
					case -45:
						break;
					case 46:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED);
}
					case -46:
						break;
					case 47:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	Utility.Analizador(str.length() == yytext().length() - 2);
	return (new Yytoken(40,str,yyline,yychar,yychar + str.length()));
}
					case -47:
						break;
					case 48:
						{ }
					case -48:
						break;
					case 50:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -49:
						break;
					case 51:
						{
	String str =  yytext().substring(1,yytext().length());
	Utility.error(Utility.E_UNCLOSEDSTR);
	Utility.Analizador(str.length() == yytext().length() - 1);
	return (new Yytoken(41,str,yyline,yychar,yychar + str.length()));
}
					case -50:
						break;
					case 52:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED);
}
					case -51:
						break;
					case 53:
						{ }
					case -52:
						break;
					case 55:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -53:
						break;
					case 56:
						{ }
					case -54:
						break;
					case 57:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -55:
						break;
					case 58:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -56:
						break;
					case 59:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -57:
						break;
					case 60:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -58:
						break;
					case 61:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -59:
						break;
					case 62:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -60:
						break;
					case 63:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -61:
						break;
					case 64:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -62:
						break;
					case 65:
						{
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}
					case -63:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
