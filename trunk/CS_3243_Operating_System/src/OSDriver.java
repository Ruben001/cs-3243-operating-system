import java.util.ArrayList;


public class OSDriver {
	
	private static CPU cpu;
	private static Disk disk;
	// list of all PCB's for the given job file
	private static ArrayList<PCB> pcbList;
	
	// queues
	private static ArrayList<PCB> readyQueue;
	
	public static void main(String[] args) {
		// initialize components
		cpu = new CPU();
		disk = new Disk();
		pcbList = new ArrayList<PCB>();
		readyQueue = new ArrayList<PCB>();
		
		// call the loader to load the job file into the Disk
		Loader loader = new Loader("DataFile2-Jobs1+2.txt", disk, pcbList);
		loader.load();

		return;
	}

}
