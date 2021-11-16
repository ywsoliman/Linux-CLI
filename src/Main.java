import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		
		Parser p = new Parser();
		Terminal t = new Terminal(p);
		
		while (true) {
			String userInput = sc.nextLine();

			if (userInput.equals("exit"))
				break;
			
			if (p.parse(userInput)) {
				t.chooseCommandAction();
			}
		}
		
		sc.close();
		
	}

}
