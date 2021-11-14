import java.util.Scanner;

public class Parser {
	String commandName;
	String[] args;
	
	//This method will divide the input into commandName and args
	//where "input" is the string command entered by the user
	public boolean parse(String input){
		String[] arrOfStr = input.split(" ");
		commandName = arrOfStr[0];
		
        args = new String[arrOfStr.length-1];
        for (int i = 1; i < arrOfStr.length; i++)
        	args[i] = arrOfStr[i];
		return true;
	}
	
	public String getCommandName(){
		return commandName;

	}
	public String[] getArgs(){
		return args;

	}
	   public static void main(String[] args) throws Exception
	   {
	       Scanner scanner = new Scanner(System.in);
	       String input = scanner.next();
	       Parser p =new Parser();
	       p.parse(input);
	       String[] arrOfStr = p.getArgs();
//	       for (int i = 0; i < arrOfStr.length; i++)
	    	   System.out.print(p.getArgs()[1]);
	       System.out.println(p.getCommandName());
	   }
}
