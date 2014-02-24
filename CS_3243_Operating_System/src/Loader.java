import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loader to load lines from the job file, stripping out control cards, storing data on created PCBs, and writing 
 * data into the disk. 
 * @author wmasters
 */
/**
 * @author wmasters
 *
 */
public final class Loader {

	/**
	 * Disk into which data from the job file will be written. 
	 */
	private Disk disk;

	/**
	 * List of PCBs to populate with data from the job file. 
	 */
	private ArrayList<PCB> pcbList;
	
	/**
	 * Scanner to read the file. 
	 */
	private Scanner reader;

	public Loader(String fileName, Disk disk, ArrayList<PCB> pcbList) {
		this.disk = disk;
		this.pcbList = pcbList;
		try {
			reader = new Scanner(new File(fileName));
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * Performs the task of loading. 
	 */
	public void load() {
		int diskIndex = 0;
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
			pcb.processId = Integer.parseInt(tokens[2], 16);//Set the process ID
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
				//System.out.println(line);
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