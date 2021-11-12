import java.lang.reflect.Method;
import java.lang.Class;
import java.util.Scanner;

public class Terminal {
	
	Parser parser;
	
	//Implement each command in a method, for example:
	public void cd(String[] args) {
		
	}
	public void mkdir(String[] args) {
		
	}
	public void rmdir(String[] args) {
		
	}
	public void touch(String[] args) {
		
	}
				
	//Constructor to set parser
	public Terminal(Parser parser) {
		this.parser = parser;
	}
	
	//This method will choose the suitable command method to be called
	public void chooseCommandAction() {
		Terminal obj = new Terminal(parser);  

		try {
			Method mthd = obj.getDeclaredMethod(parser.getCommandName(), parser.getArgs());  
	        System.out.println(mthd);     
        }
        catch (Exception e) {
            System.out.println("Error: Command not found or invalid parameters are entered!");
        }
	}
	

	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    while(true) {
	    	String input = scanner.next();
	    	if(input == "exit") 
	    		break;
	    	Parser parser = new Parser();
	    	parser.parse(input);
	    	Terminal terminal = new Terminal(parser);
	    	terminal.chooseCommandAction();
	    }

	}
	
}
