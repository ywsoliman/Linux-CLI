import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.lang.Class;
import java.util.Scanner;
import java.util.stream.Stream;

public class Terminal {
	
	Parser parser;
	
	//Implement each command in a method, for example:
	public void cd(String[] args) {
		String currPath = System.getProperty("user.dir");
		if(args.length==0) {
			String[] arr = currPath.split(":", 2);
			currPath = arr[0]+":\\";
		}
		else if(args[0].contains(":")){
			currPath = args[0];
		}
		else if(args[0]==".."){
		    String target="\\";
		    String replacement="\\\\";
		    currPath=currPath.replace(target, replacement);
		    String[] arr = currPath.split(replacement);
		    currPath="";
		    for(int i=0;i<arr.length-1;i++) 
		    	currPath+= arr[i] + "\\";
		    currPath=currPath.replace(replacement, target);
		}
		else {
			currPath = currPath+"\\"+args[0];
		}
		
	}
	public void mkdir(String[] args) {
		String currPath = System.getProperty("user.dir");
		for(String dir:args) {
			File f;
			if(dir.contains(":")) {
				f = new File(dir);
			}
			else {
				f = new File(currPath+"\\"+dir);
			}
	        f.mkdir();
		}
	}
	public void rmdir(String[] args) throws IOException {
		String currPath = System.getProperty("user.dir");

		if(args[0].contains("*")) {
			File f = new File(currPath);
		    File[] allContents = f.listFiles();
		        for (File file : allContents) {
		             Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
					 if (Files.isDirectory(path)) {
				        try (Stream<Path> entries = Files.list(path)) {
				            if(!entries.findFirst().isPresent()) {
				            	File directory = new File(file.getAbsolutePath());
				                directory.delete();
				            }
				            	
				        }
				    }
		        }
		}
		else {		
			 if(args[0].contains(":"))
				 currPath = args[0];
			 else currPath = currPath+"\\"+args[0]; 
			 Path path = FileSystems.getDefault().getPath(currPath);
			 if (Files.isDirectory(path)) {
		        try (Stream<Path> entries = Files.list(path)) {
		            if(!entries.findFirst().isPresent()) {
		            	File directory = new File(currPath);
		                directory.delete();
		            }
		            	
		        }
		    }
		
		}
	}
	public void touch(String[] args) throws IOException {
		String currPath = System.getProperty("user.dir");
		File f;
		if(args[0].contains(":")){
		     f = new File(args[0]);
		}
		else {
			f = new File(currPath+"\\"+args[0]);
		}
		f.createNewFile();
	}
				
	//Constructor to set parser
	public Terminal(Parser parser) {
		this.parser = parser;
	}
	
	//This method will choose the suitable command method to be called
	public void chooseCommandAction() throws ClassNotFoundException {
		Terminal obj = new Terminal(parser);  

		try {
			Method mthd = obj.getDeclaredMethod(parser.getCommandName(), parser.getArgs());  
	        
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
