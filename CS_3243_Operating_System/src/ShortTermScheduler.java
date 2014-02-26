import java.util.ArrayList;

public class ShortTermScheduler {
	private static CPU cpu;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private static LongTermScheduler ltScheduler;
	
	private PCB currentProcess;
	private PCB nextProcess;
	
	private ArrayList<PCB> readyQueue;
	private int currentQIndex;
	private SchedulingAlgorithm algorithm;
	
	public ShortTermScheduler(CPU cpu,LongTermScheduler ltScheduler,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.cpu = cpu;
		this.ltScheduler = ltScheduler;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
		this.currentQIndex = 0;
	}
	
	public void dispatch(PCB nProcess){
		
		//CPU time
				cpu.cpuStartTime = nProcess.startTime;
				cpu.cpuEndTime = nProcess.endTime;
				cpu.cpuBurstTime = nProcess.cpuBurstTime;
		//If its the first time the job gets the CPU
		if(nProcess.pc == 0){
			cpu.cpuStartTime = System.currentTimeMillis();
		}
		
		
		cpu.pc = nProcess.pc;
		cpu.priority = nProcess.priority;
		cpu.processAddress = nProcess.jobFileAddress;
		cpu.processLength = nProcess.jobFileLength;
		cpu.processId = nProcess.processId;
		cpu.register = nProcess.registers;
		
		nProcess.state = ProcessState.RUNNING;
		cpu.iRegister.clear();
		//Buffers
		cpu.inputBufferAddress = nProcess.inputBufferAddress;
		cpu.inputBufferLength = nProcess.inputBufferLength;
		cpu.outputBufferAddress = nProcess.outputBufferAddress;
		cpu.outputBufferLength = nProcess.outputBufferLength;
		cpu.tempBufferAddress = nProcess.tempBufferAddress;
		cpu.tempBufferLength = nProcess.tempBufferLength;
		//TIME
		
		//Turn around time
		cpu.startTime = nProcess.startTime;
		cpu.endTime = nProcess.endTime;
		cpu.turnAroundTime = nProcess.turnAroundTime;
		
		cpu.waitTime = nProcess.waitTime;
		cpu.numberIO = nProcess.numberIO;
		
		cpu.begin();
		if(readyQueue.size() == 0){
			cpu.averageTurnAroundTime();
			cpu.averageWaitTime();
			cpu.averageNumberOfIORequests();
			
		}
		
		
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
