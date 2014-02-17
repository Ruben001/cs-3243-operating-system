import java.util.ArrayList;

public class LongTermScheduler {
	private Disk disk;
	private Memory memory;
	private ArrayList<PCB> readyQueue;
	
	public LongTermScheduler(Disk disk, Memory memory, ArrayList<PCB> readyQueue) {
		this.disk = disk;
		this.memory = memory;
		this.readyQueue = readyQueue;
	}
}