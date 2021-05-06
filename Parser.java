package proj3;

import proj3.Scanner.Token;
import proj3.Scanner;

public class Parser 
{
	private Token currentToken;
	Scanner scanner;
	boolean f= true, f1 = true, p=true;
	int count=0;
	
	private void accept(byte expectedKind) {
		if(currentToken.kind == expectedKind)
			currentToken = scanner.scan();
		
		//else
			//System.out.println("Syntax error: " + currentToken.spelling + " is not expected.", currentToken.line);
	}
	
	private void acceptIt() 
	{
	    currentToken = scanner.scan();
	 }
	
	private boolean ac(byte expectedKind) 
	{
		return (currentToken.kind == expectedKind);
	}
	
	public void parse() 
	{
		
	    SourceFile sourceFile = new SourceFile();
	    scanner = new Scanner(sourceFile.openFile());
	    //scanner.help();
	    currentToken = scanner.scan(); 
	    parseExpr();
	    
	  }
	
	  private void parseExpr()
	  {
		  System.out.println("Enter EXPR");
		  boolean flag=true;
		  accept(Token.LP);
		  while(flag) 
		  {
			  if (currentToken.kind == Token.NAME) 
			  {
				  parseName();
			  }
			  else if (currentToken.kind == Token.FUNCTION) 
			  {
				  f1=false;
				  parseFunction();
				  f1=true;
			  }
			  else if (currentToken.kind == Token.APPLICATION) 
			  {
				  f1=false;
				  parseApplication();
				  f1=true;
			  }
			  else 
				  flag= false;
		  }
		  accept(Token.RP);
		  System.out.println("Leave EXPR");
		  if(f1) {
			  if(ac(Token.RP))
				  f=false;
		  }
	  
	  }
	  
	  private void parseApplication()
	  {
		  System.out.println("Enter APPLICATION");
		  parseExpr();
		  parseExpr();
		  System.out.println("Leave APPLICATION");
	  }
	 
	  private void parseFunction()
	  {
		  System.out.println("Enter FUNCTION");
		  accept(Token.LP);
		  accept(Token.LAMBA);
		 
		  parseExpr();
		  parseExpr();
		  accept(Token.RP);
		  System.out.println("Leave FUNCTION");
		  
	  }
	  
	  private void parseDefine()
	  {
		  System.out.println("Enter DEFINE");
		  accept(Token.LP);
		  accept(Token.LAMBA);
		  parseName();
		  parseExpr();
		  System.out.println("Leave DEFINE");
		  
	  }
	  
	  private void parseName()
	  {
		  System.out.println("Enter NAME");
		  currentToken.kind = Token.NAME;
		  accept(Token.NAME);
		  System.out.println("Leave NAME");
	  }
	  
	  private void parseList()
	  {
		  System.out.println("Enter LIST");
		  boolean flag=true;
		  accept(Token.LP);
		  accept(Token.TICK);
		  accept(Token.LP);
		  while(flag) 
		  {
			  if(currentToken.kind == Token.ELEM)
			  parseElem();
		  }
		  accept(Token.RP);
		  System.out.println("Leave LIST");
		  
	  }
	
	  private void parseElem()
	  {
		  System.out.println("Enter ELEM");
			  if (currentToken.kind == Token.PROC) 
			  {
				  parseProc();
			  } 
			  else if (currentToken.kind == Token.NAME) {
				  parseName();
			  }
			  else if (currentToken.kind == Token.INT) 
			  {
				 
				 System.out.println("WHAT DO I PARSE");
				  
			  }
			  else if (currentToken.kind == Token.FLOAT) 
			  {	  
				  System.out.println("WHAT DO I PARSE");  
				  
			  }
			  else if (currentToken.kind == Token.STRING) 
			  {
				  System.out.println("WHAT DO I PARSE");
				  
			  }
			  else if (currentToken.kind == Token.BOOL) 
			  {
				  parseBool();
			  }
			  else 
				  accept(Token.RP);
		  
		 
		  if(f1) {
			  if(ac(Token.RP))
				  f=false;
		  }
		  System.out.println("Leave ELEM");

	  }
	  
	  private void parseProc()
	  {
		  System.out.println("Enter PROC");
		  parseName();
		  accept(Token.OPERATOR);
		  System.out.println("Leave PROC");
	  }
	  
	  private void parseIf()
	  {
		  System.out.println("Enter IF");
		  accept(Token.LP);
		  accept(Token.IF);
		  parseExpr();
		  parseExpr();
		  parseExpr();
		  accept(Token.RP);
		  System.out.println("Leave IF");
		  
	  }
	  
	  private void parseBool()
	  {
		  System.out.println("Enter BOOL");
		  System.out.println("Leave BOOL");
	  }
	  
	  public static void main(String[] args) 
	  {
		 Parser p = new Parser();
		 p.parse();
		 System.out.println("The syntax of the source program is correct.");		
	  }
}
