import java.util.ArrayList;

public class ShortTermScheduler {
	private Memory memory;
	private ArrayList<PCB> readyQueue;
	private SchedulingAlgorithm algorithm;
	
	public ShortTermScheduler(Memory memory, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.memory = memory;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
	}
	
	public void ScheduleAndDispatch()
	{
		switch(algorithm) {
		case FCFS:
			fcfsSchedule();
			break;
		case PRIORITY:
			prioritySchedule();
			break;
		case RR:
			rrSchedule();
			break;
		case SJF:
			sjfSchedule();
			break;
		}
	}
	
	private void fcfsSchedule() {
		
	}
	
	private void prioritySchedule() {
		
	}
	
	private void rrSchedule() {
		
	}
	
	private void sjfSchedule() {

	}
	
	private void Dispatch() {
		
	}
}
