	
public class Parser {

	String commandName;
	String[] args;
	String[] allCommands = {"echo", "pwd", "cd", "ls", "mkdir", "rmdir", "touch", "cp", "rm", "cat"};
	
	public boolean parse(String input) {
		
		if (input.equals(""))
			return false;
		
		String[] arr = input.split(" ");
		args = new String[arr.length - 1];
		
		for (int i = 0; i < allCommands.length; i++) {
			
			if (arr[0].equals(allCommands[i])) {
				
				commandName = arr[0];
				
				if (commandName.equals("pwd") ||
					commandName.equals("ls"))
				{
					if (arr.length != 1) {
						System.out.println(commandName + " doesn't take any arguments.");
						return false;
					}
					else
						return true;
				}
				else if (commandName.equals("cd"))
				{
					if (args.length == 0)
						return true;
					else if (args.length == 1) {
						args[0] = arr[1];
						return true;
					}
					else {
						System.out.println(commandName + " requires one argument only.");
						return false;
					}
				}
				else if (commandName.equals("rm") || 
						 commandName.equals("touch") ||
						 commandName.equals("rmdir"))
				{
					if (arr.length != 2) {
						System.out.println(commandName + " requires one argument only.");
						return false;
					}
					else {
						args[0] = arr[1];
						return true;
					}
				}
				
				else if (commandName.equals("cp"))
				{
					if (arr.length == 4 && arr[1].equals("-r")) {
						args[0] = arr[1];
						args[1] = arr[2];
						args[2] = arr[3];
						return true;
					}
					else if (arr.length == 3) {
						args[0] = arr[1];
						args[1] = arr[2];
						return true;
					}
					else {
						System.out.println(commandName + " requires two arguments only.");
						return false;
					}
				}
				
				else if (commandName.equals("mkdir"))
				{
					if (arr.length == 1) {
						System.out.println(commandName + " requires at least one argument.");
						return false;
					}
					else {
						for (int j = 1; j < arr.length; j++) {
							args[j - 1] = arr[j];
						}
						return true;
					}
				}
				else if (commandName.equals("cat"))
				{
					if (arr.length == 2) {
						args[0] = arr[1];
						return true;
					}
					else if (arr.length == 3) {
						args[0] = arr[1];
						args[1] = arr[2];
						return true;
					}
					else {
						System.out.println(commandName + " takes one or two arguments.");
						return false;
					}
				}
				else if (commandName.equals("echo"))
				{
					for (int j = 1; j < arr.length; j++)
						args[j - 1] = arr[j];
					return true;
				}
			}
		}
		return false;
	}
	
	public String getCommandName() {
		return this.commandName;
	}
	
	public String[] getArgs() {
		return this.args;
	}
	
	
}
