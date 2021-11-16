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
	
	public void echo(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.print(args[i] + " ");
	}
	
	public String pwd() {
		return currPath;
    }
	
	public void cp(String source, String destination) throws IOException {
		
		File sourceFile = new File(source);
		File destFile = new File(destination);
		
		if (sourceFile.isFile()) {
			
			Scanner myReader = new Scanner(sourceFile);
			String srcData = "";
			while (myReader.hasNextLine()) {
				srcData += srcData.concat(myReader.nextLine());
			}
			myReader.close();
			
			FileWriter myWriter = new FileWriter(destination);
		    myWriter.write(srcData);
		    System.out.println("Data has been copied successfuly!");
		    myWriter.close();
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
		
//		File file = new File(args[]);
//		
//		
	}
	
	public void ls() {

		File directoryPath = new File(currPath);
		String files[] = directoryPath.list();
		
		Arrays.sort(files);
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
			cp(parser.args[0], parser.args[1]);
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
			
		}
		
	}
	
}
