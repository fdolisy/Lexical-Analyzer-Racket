package proj3;
import java.io.*;

public class Scanner 
{
	//TOKEN CLASS =============================
	public class Token 
	{
		public byte kind;
		public String spelling;
		public int line;
		
		private final String[] spellings = 
		{
			 "lambda", "define", "if"
		};
		
		
		public final static byte
		LP = 1,
		RP = 2,
		LAMBA = 3,
		DEFINE = 4,
		IF = 5,
		NAME = 6,
		INT = 7,
		FLOAT = 8,
		STRING = 9,
		SYMBOL = 10,
		ARG = 11,
		TICK = 12,
		IDENTIFIER = 13,
		LITERAL = 14,
		NOTHING = 15,					         
		EOT        = 16,
		OPERATOR = 17,
		
		
		APPLICATION = 19,
		BOOL = 20,
		FUNCTION = 21,
		PROC = 22,
		ELEM = 23,
		SKIP = 18;
	
		
		public Token(byte kind, String spelling, int line) 
		{
			this.kind = kind;
			this.spelling = spelling;
			this.line = line;
			if(kind == SYMBOL) 
			{
				//System.out.println("WE HAVE A SYMBOL "+ spelling);
				
				//get out the key words
				for(int k = 0; k < 3; k++) 
				{
			        if(spelling.equals(spellings[k]))
			        {
			          this.kind = (byte)(k +3);
			          break;
			        }
				}   
			}
			if(kind == LITERAL)
			{
				if(spelling.contains("."))
				{
					this.kind = (byte)8;
				}
				else
				{
					this.kind = (byte)7;
				}
			}
			print(); 
		}
		
		public void print() {
			if(kind == 15)
				System.out.print("Line: "+line+" Wrong token "+spelling+"\n");
			else if(kind == 16)
				System.out.print("End"+"\n");
			else
				System.out.print("Next Token is: " + kind +" Next Lexeme is: "+spelling+"\n") ;			
		}
		
	}
	
	
	//======================================================================

	 private char currentChar;
	  private byte currentKind;
	  private StringBuffer currentSpelling;
	  private BufferedReader inFile;
	  private static int line = 1;
	  private static byte finalkind;      //Created this variable to test scanner.java

	  public Scanner(BufferedReader inFile)
	  {
	    this.inFile = inFile;
	    try{
	      int i = this.inFile.read();
	      if(i == -1) //end of file
	        currentChar = '\u0000';
	      else
	        currentChar = (char)i;
	    }
	    catch(IOException e){
	        System.out.println(e);
	    }
	  }

	  private void takeIt()
	  {
	    currentSpelling.append(currentChar);
	    try{
	      int i = inFile.read();
	      if(i == -1) //end of file
	        currentChar = '\u0000';
	      else
	        currentChar = (char)i;
	    }
	    catch(IOException e){
	        System.out.println(e);
	    }
	  }

	  private void discard()
	  {
	    try{
	      int i = inFile.read();
	      if(i == -1) //end of file
	        currentChar = '\u0000';
	      else
	        currentChar = (char)i;
	    }
	    catch(IOException e){
	        System.out.println(e);
	    }
	  }
	  
	  private byte scanToken()
	  {
		byte result = 0;  
		
	    switch(currentChar){
	    	case '(': result = Token.LP;  takeIt(); break;	
	    	case ')': result = Token.RP;  takeIt(); break;
	    	case '\'': result = Token.TICK;  takeIt(); break;
	    	case '\u0000': result = Token.EOT; break;
	    	default: 
	    		
	    		if(isLetter(currentChar)) {
	    			
	    			if(currentChar == '\"')
	    			{
	    				while(isLetter(currentChar)) 
	    				{  
		    				takeIt();
		    				result = Token.STRING;
		    				}
	    			}
	    			while(isLetter(currentChar)) {  
	    				takeIt();
	    				result = Token.SYMBOL;
	    				}
	    		break;
	    		}
	    		else if(isDigit(currentChar)) {
	    			while(isDigit(currentChar)) {
	    				takeIt();	
	    				result = Token.LITERAL;
	    				}
	    		break;
	    		}
	    		else if(isGraphic(currentChar)) { 
	    			while(isGraphic(currentChar)) {
	    				takeIt();
	    				result = operator(currentSpelling);  //This method returns NOTHING if there is a unknown operator
	    				if(isLetter(currentChar) || isDigit(currentChar))
	    					break;
	    			}
	    			break;
	    		}
	    }
	    return result;                 
	  }

	  private void scanSeparator()
	  {
	    switch(currentChar){
	      case ' ': case '\n': case '\r': case '\t':
	        if(currentChar == '\n')
	          line++;
	        discard();
	    }
	  }

	  public Token scan()
	  {
	    currentSpelling = new StringBuffer("");
	    while(currentChar == ' ' || currentChar == '\n' || currentChar == '\r')
	      scanSeparator();
	    currentKind = scanToken();
	    finalkind = currentKind;
	    return new Token(currentKind, currentSpelling.toString(), line);
	  }
	  
	  private byte operator(StringBuffer s) 
	  {                  
		  byte r=0;
		  String ss = s.toString();
		  boolean flag=false;
		  String[] op = {"+" ,"-" , "*" , "/" , "<" , "<=" , ">", ">=", "=", "!=" };
		  for(int i=0; i<op.length;i++) {
			  if(ss.equals(op[i])) {
				  r = Token.OPERATOR;
				  flag = true;
				  break;
			  }
		  }
		  if(flag == false) {
			  r = Token.NOTHING;
		  }
		  return r;
	  }
	  
	  
	  private boolean isGraphic(char c){
	    return c == '\t' ||(' ' < c && c <= '~');       // ' ' <= c changed to ' ' < c         
	  }

	  private boolean isDigit(char c){
	    return ('0' <= c && c <= '9') || c == '.';
	  }

	  private boolean isLetter(char c){
	    return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (c == '\"');
	  }
	  
	  //Before running this file go to Token.java and in constructor Token comment print()
	  public static void help() {  
		  SourceFile sf = new SourceFile();
		  BufferedReader br = sf.openFile();
		  Scanner sc = new Scanner(br);
		  Token a1;
		 
		  while(finalkind != 16) {              
		  a1 = sc.scan();						
		  a1.print();
		  }
		  
	  }
}
