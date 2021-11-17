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
	
	public boolean hasOperator(String[] args) {
				
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(">")) {
				return true;
			}
		}
		return false;
	}
	
	public void writeToFile(String data, String[] args) throws IOException
	{
		FileWriter writeTo = new FileWriter(args[args.length - 1]);
	    writeTo.write(data);
	    System.out.println("Data has been copied successfuly!");
	    writeTo.close();
	}
	
	public void echo(String[] args) throws IOException {
		

		String data = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(">"))
				break;
			data += (args[i] + " ");
		}
		
		if (hasOperator(args))
			writeToFile(data, args);
		else
			System.out.println(data);
	}
	
	public String pwd(String[] args) throws IOException {
		if (hasOperator(args))
			writeToFile(currPath, args);
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
				
				FileWriter writeTo = new FileWriter(destFile);
			    writeTo.write(srcData);
			    System.out.println("Data has been copied successfuly!");
			    writeTo.close();
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
			System.out.println(".........");
		}
		else if(args[0].contains(":")){
			currPath = args[0];
		}
		else if(args[0].contains("..")){
			
		    String target="\\";
		    String replacement="\\\\";
		    currPath=currPath.replace(target, replacement);
		    String[] arr = currPath.split(replacement);
		    currPath="";
		    for(int i=0;i<arr.length-2;i++) 
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
					data += (myReader.nextLine() + "\n");
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
	
	public void ls(String[] args) throws IOException {
		
		File directoryPath = new File(currPath);
		String files[] = directoryPath.list();
		
		if (args.length == 0)
			Arrays.sort(files);
		else if (args.length == 1 && args[0].equals("-r"))
			Arrays.sort(files, Collections.reverseOrder()); 
		
		String content = "";
		for (int i = 0; i < files.length; i++)
			content += (files[i] + "\n");
		
		if (hasOperator(args))
				writeToFile(content, args);
			else
				System.out.println(content);  

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
	
	public void rm(String[] args) {
		File file = new File(args[0]);
		if(!file.exists())
			System.out.println("No such file");
		else if(file.isDirectory())
			System.out.println("Cant delete this file");
		else
			file.delete();
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
			System.out.println(pwd(parser.getArgs()));
			break;
		case "cp":
			cp(parser.getArgs());
			break;
		case "cat":
			cat(parser.getArgs());
			break;
		case "ls":
			ls(parser.getArgs());
			break;
		case "mkdir":
			mkdir(parser.getArgs());
			break;
		case "rm":
			rm(parser.getArgs());
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
