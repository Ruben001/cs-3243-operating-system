import java.util.ArrayList;

public class ShortTermScheduler {
	private static CPU cpu;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	
	private PCB currentProcess;
	private PCB nextProcess;
	
	private ArrayList<PCB> readyQueue;
	private int currentQIndex;
	private SchedulingAlgorithm algorithm;
	
	public ShortTermScheduler(CPU cpu,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.cpu = cpu;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
		this.currentQIndex = 0;
	}
	
	public void dispatch(PCB nProcess){
		
		cpu.pc = nProcess.pc;
		cpu.priority = nProcess.priority;
		cpu.processAddress = nProcess.jobFileAddress;
		cpu.processLength = nProcess.jobFileLength;
		cpu.processId = nProcess.processId;
		cpu.register = nProcess.registers;
		nProcess.state = ProcessState.RUNNING;
		
		//Buffers
		cpu.inputBufferAddress = nProcess.inputBufferAddress;
		cpu.inputBufferLength = nProcess.inputBufferLength;
		
		cpu.begin();
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
		while(readyQueue.size() != 0){
			nextProcess =  readyQueue.remove(0);
			dispatch(nextProcess);
		}
		
	}
	
	private void prioritySchedule() {
		
	}
	
	private void rrSchedule() {
		
	}
	
	private void sjfSchedule() {

	}
	
}
