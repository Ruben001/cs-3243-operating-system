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
			reader = new Scanner(new File("DataFile2-Jobs1+2.txt"));
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
			pcbList.add(pcb);
			line = reader.nextLine();
			tokens = line.split(" ");
			pcb.priority = Integer.parseInt(tokens[4], 16);
			pcb.processId = Integer.parseInt(tokens[2]);//Set the process ID
			pcb.jobFileAddress = diskIndex;
			pcb.jobFileLength = Integer.parseInt(tokens[3], 16);
			for (int i = 0; i < pcb.jobFileLength; ++i) {
				line = reader.nextLine();
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				diskIndex++;
			}
			line = reader.nextLine();
			tokens = line.split(" ");
			pcb.inputBufferAddress = diskIndex;
			pcb.inputBufferLength = Integer.parseInt(tokens[2], 16);
			for (int i = 0; i < pcb.inputBufferLength; ++i) {
				line = reader.nextLine();
				System.out.println(line);
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				++diskIndex;
			}
			pcb.outputBufferAddress = diskIndex;
			pcb.outputBufferLength = Integer.parseInt(tokens[3], 16);
			for (int i = 0; i < pcb.outputBufferLength; ++i) {
				line = reader.nextLine();
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				++diskIndex;
			}
			pcb.tempBufferAddress = diskIndex;
			pcb.tempBufferLength = Integer.parseInt(tokens[4], 16);
			for (int i = 0; i < pcb.tempBufferLength; ++i) {
				line = reader.nextLine();
				disk.writeData(diskIndex, Long.parseLong(line.replace("0x", ""), 16));
				++diskIndex;
			}
			line = reader.nextLine();
		}
	}
}