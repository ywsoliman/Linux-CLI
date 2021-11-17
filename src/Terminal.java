import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Terminal {

	Parser parser;
	public static String currPath = System.getProperty("user.dir");
	
	public Terminal(Parser parser) {
		this.parser = parser;
	}
	
	public void echo(String[] args) throws IOException {
		
		boolean operatorFound = false;
		String data = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(">")) {
				operatorFound = true;
				break;
			}
			data += (args[i] + " ");
		}
		if (operatorFound)
		{
			FileWriter myWriter = new FileWriter(args[args.length - 1]);
		    myWriter.write(data);
		    System.out.println("Data has been copied successfuly!");
		    myWriter.close();
		}
	}
	
	public String pwd() {
		return currPath;
    }
	
	public void cp(String[] args) throws IOException {
		
		File sourceFile = new File(args[0]);
		File destFile = new File(args[1]);
		
		if (sourceFile.exists())
		{
			if (sourceFile.isFile()) {
				
				Scanner myReader = new Scanner(sourceFile);
				String srcData = "";
				while (myReader.hasNextLine()) {
					srcData += srcData.concat(myReader.nextLine());
				}
				myReader.close();
				
				FileWriter myWriter = new FileWriter(destFile);
			    myWriter.write(srcData);
			    System.out.println("Data has been copied successfuly!");
			    myWriter.close();
			}
		} else {
			System.out.println("Source file doesn't exist.");
			return;
		}
		
	}
	
	public void touch(String[] args) throws IOException {
		File f;
		if(args[0].contains(":")){
		     f = new File(args[0]);
		}
		else {
			f = new File(currPath+"\\"+args[0]);
		}
		f.createNewFile();
	}
	
	public void cd(String[] args) {
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
	
	public void cat(String[] files) throws IOException {
		String data = "";
		File file = null;
		for (int i = 0; i < files.length; i++) {
			file = new File(files[i]);
			if (file.isFile() && file.exists()) {
				Scanner myReader = new Scanner(file);
				while (myReader.hasNextLine()) {
					data += data.concat(myReader.nextLine());
				}
				myReader.close();
			}
			else {
				System.out.println("File couldn't be found.");
				return;
			}
		}
		System.out.println(data);	
	}
	
	public void ls() {
		
		File directoryPath = new File(currPath);
		String files[] = directoryPath.list();
		Arrays.sort(files);
		for(int i=0; i<files.length; i++) 
		System.out.println(files[i]);
	         
	}
	public void lsr(){
		
		File directoryPath = new File(currPath);
		String files[] = directoryPath.list();
		Arrays.sort(files, Collections.reverseOrder()); 
		
		for(int i=0; i<files.length; i++) 
			System.out.println(files[i]);
	      
	}
	
	public void mkdir(String[] args) {
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
	
	public void chooseCommandAction() throws IOException {
		
		switch(parser.commandName) {
		
		case "echo":
			echo(parser.getArgs());
			break;
		case "pwd":
			System.out.println(pwd());
			break;
		case "cp":
			cp(parser.getArgs());
			break;
		case "cat":
			cat(parser.getArgs());
			break;
		case "ls":
			ls();
			break;
		case "mkdir":
			mkdir(parser.getArgs());
			break;
		case "rmdir":
			rmdir(parser.getArgs());
			break;
		case "cd":
			cd(parser.getArgs());
			break;
			
		}
		
	}
	
}
