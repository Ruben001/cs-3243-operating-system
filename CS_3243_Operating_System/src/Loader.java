import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

	private Disk disk;
	private ArrayList<PCB> pcbList;
	
	private Scanner reader;
	private int diskIndex;
	
	public Loader(Disk disk, ArrayList<PCB> pcbList) {
		this.disk = disk;
		this.pcbList = pcbList;
		try {
			reader = new Scanner(new File("DataFile2-Cleaned.txt"));
		}
		catch (Exception e) {
		}
		diskIndex = 0;
	}
	
	public void load() {
		String line = "";
		String[] tokens;
		PCB pcb;
		while (true) {
			if (!reader.hasNext())
				break;
			pcb = new PCB();
			line = reader.nextLine();
			tokens = line.split(" ");
			pcb.priority = Integer.parseInt(tokens[4], 16);
			PcbFile exeFile = new PcbFile(diskIndex, Integer.parseInt(tokens[3], 16), PcbFileType.JOB);
			pcb.files.add(exeFile);
			for (int i = 0; i < exeFile.getFileLength(); ++i) {
				line = reader.nextLine();
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				diskIndex++;
			}
			line = reader.nextLine();
			tokens = line.split(" ");
			PcbFile dataFile = new PcbFile(diskIndex, Integer.parseInt(tokens[2], 16) + Integer.parseInt(tokens[3], 16) + Integer.parseInt(tokens[4], 16), PcbFileType.DATA);
			pcb.files.add(dataFile);
			for (int i = 0; i < dataFile.getFileLength(); ++i) {
				line = reader.nextLine();
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				diskIndex++;
			};
			line = reader.nextLine();
		}
	}
}