package opl;

import java.util.List;
import java.util.ArrayList;

/*
 * Lexical analyzer for Scheme-like minilanguage:
 * (define (foo x) (bar (baz x)))
 */
public class Lexer {
	
	// to add  arg name, what is arg and anme vs symbol and string
	
    public static enum Type {
        // This Scheme-like language has three token types:
        // open parens, close parens, and an "atom" type
        LP, RP, SYMBOL, TICK, LAMBDA, IF, DEFINE, NAME, STRING, INT, FLOAT;
    }
    
    
    public static class Token {
        public final Type t;
        public final String c; // contents mainly for atom tokens
        // could have column and line number fields too, for reporting errors later
        public Token(Type t, String c) {
            this.t = t;
            this.c = c;
        }
        public String toString() {
            if(t == Type.SYMBOL) {
                //return "SYMBOL<" + c + ">";
            }
            return t.toString();
        }
    }

    /*
     * Given a String, and an index, get the atom starting at that index
     */
    public static String getAtom(String s, int i) {
        int j = i;
        for( ; j < s.length(); ) {
        	//strings can have num or char
            if(Character.isLetter(s.charAt(j)) || s.charAt(j) == '\"') {
                j++;
            } else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }
    
    public static int identInt(String s, int i) 
    { 
    	int flag = 0;//flag 1 means floating point
    	int j = i;
         for( ; j < s.length(); ) 
         {
    	//strings can have num or char
        	// System.out.println(s.length());
	        //System.out.println(s.charAt(j));
	        if(s.charAt(j) == '.') {
	        	flag = 1;
	            j++;
	        }
		    if(Character.isDigit(s.charAt(j)))// && s.charAt(j) != ' ')
		     {
		        	j++;
		     }
		    else
		    {
		    	
		    	return flag;
		    }
       }
         return flag;
    }
         
        
    
    public static List<Token> lex(String input) {
    	int flag;
        List<Token> result = new ArrayList<Token>();
        for(int i = 0; i < input.length(); ) {
            switch(input.charAt(i)) {
            case '(':
                result.add(new Token(Type.LP, "("));
                i++;
                break;
            case ')':
                result.add(new Token(Type.RP, ")"));
                i++;
                break;
            case '\'':
            	result.add(new Token(Type.TICK, "'"));
                i++;
                break;   
           /* case '0':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	break; 
            case '1':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            
            	break; 
            case '2':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '3':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '4':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '5':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '6':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '7':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '8':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            case '9':
            	flag = identInt(input, i);
            	if(flag == 0) 
            	{
            		result.add(new Token(Type.INT, "'"));
            	}
            	else if(flag == 1)
            	{
            		result.add(new Token(Type.FLOAT, "'"));
            	}
            	i += input.length();
            	break; 
            */
            default:
                if(Character.isWhitespace(input.charAt(i))) {
                    i++;
                    continue;
                } else {
                    String atom = getAtom(input, i);
                   // System.out.println(atom.charAt(0));
                    if (atom.startsWith("\"") || atom.endsWith("\""))
                    {
                    	result.add(new Token(Type.STRING, atom));
                    }
                    if(Character.isUpperCase(atom.charAt(0)))
                    {
                    	result.add(new Token(Type.NAME, atom));
                    }
                    else
                    {
	                    switch(atom)
	                    {
	                    	case "lambda":
	                    		result.add(new Token(Type.LAMBDA, atom));
	                    		break;
	                    	case "if":
	                    		result.add(new Token(Type.IF, atom));
	                    		break;
	                    	case "define":
	                    		result.add(new Token(Type.DEFINE, atom));
	                    		break;
	                    	default:
	                    		result.add(new Token(Type.SYMBOL, atom));
	                    }
                    }
                    i += atom.length();
                   
                }
                break;
            }
        }
        return result;
    }

    
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java Lexer \"((some Scheme) (code to) lex)\".");
            return;
        }
        List<Token> tokens = lex(args[0]);
        for(Token t : tokens) {
            System.out.println(t);
        }
    }
}