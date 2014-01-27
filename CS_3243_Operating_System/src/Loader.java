import java.io.File;
import java.util.Scanner;

public class Loader {
	
	private Scanner reader;
	
	public Loader() {
		try {
			reader = new Scanner(new File("DataFile2-Cleaned.txt"));
		}
		catch (Exception e) {
		}
	}
	
	public void load() {
		while (reader.hasNext()) {
			System.out.println(reader.nextLine());
		}
	}
}