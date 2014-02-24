import java.util.ArrayList;


public class OSDriver {
	
	private static CPU cpu;
	private static Disk disk;
	private static Memory memory;
	private static LongTermScheduler ltScheduler;
	private static ShortTermScheduler stScheduler;

	// list of all PCB's remaining in the disk
	private static ArrayList<PCB> pcbList;
	
	// queues
	private static ArrayList<PCB> readyQueue;
	
	public static void main(String[] args) {
		// initialize components
		disk = new Disk();
		memory = new Memory();
		cpu = new CPU(memory,stScheduler);
		pcbList = new ArrayList<PCB>();
		readyQueue = new ArrayList<PCB>();

		ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.FCFS);
		stScheduler = new ShortTermScheduler(cpu,memory,pcbList,readyQueue,SchedulingAlgorithm.FCFS);
		// call the loader to load the job file into the Disk
		Loader loader = new Loader("DataFile2-Cleaned.txt", disk, pcbList);
		loader.load();
		
		
		
		ltScheduler.schedule();
		stScheduler.ScheduleAndDispatch();
		
		System.out.println("\n\nNumber of I/O Requests: " + cpu.numberIO);

		return;
	}

}
