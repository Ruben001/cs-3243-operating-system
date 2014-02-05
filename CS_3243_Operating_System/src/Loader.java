import java.io.File;
import java.util.Scanner;

public class Loader {
	
	private Scanner reader;
	private Disk disk;
	
	public Loader(Disk disk) {
		try {
			reader = new Scanner(new File("DataFile2-Cleaned.txt"));
		}
		catch (Exception e) {
		}
		this.disk = disk;
	}
	
	public void load() {
		String line = "";
		while (true) {
			if (!reader.hasNext())
				break;
			line = reader.nextLine();
		}
	}
}