import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

	private Disk disk;
	private ArrayList<PCB> pcbList;
	private Scanner reader;
	
	public Loader(Disk disk, ArrayList<PCB> pcbList) {
		this.disk = disk;
		this.pcbList = pcbList;
		try {
			reader = new Scanner(new File("DataFile2-Cleaned.txt"));
		}
		catch (Exception e) {
		}
	}
	
	public void load() {
		String line = "";
		PCB pcb;
		while (true) {
			if (!reader.hasNext())
				break;
			line = reader.nextLine();
			
		}
	}
}