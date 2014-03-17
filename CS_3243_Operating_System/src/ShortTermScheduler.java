import java.util.ArrayList;

/**
 * The Short term scheduler keeps its scheduling algorithms that 
 * are called by ScheduleAndDispatch().  The dispatcher puts jobs 
 * into the CPU according to he algorithm used.
 * 
 * @Group Members Corey Masters
 * 			Mitchell Byrd
 * 			Mohil Patel 
 * 			Rahat Shahwar
 * 			Ruben Munive
 * 			Ivan Mba
 */


public class ShortTermScheduler {
	private static CPU cpu;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private static LongTermScheduler ltScheduler;
	
	private PCB currentCPUProcess;
	private PCB nextProcess;
	//
	private ArrayList<PCB> readyQueue;
	private int currentQIndex;
	
	private SchedulingAlgorithm algorithm;
	
	//Constructor
	public ShortTermScheduler(CPU cpu,LongTermScheduler ltScheduler,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.cpu = cpu;
		this.ltScheduler = ltScheduler;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
		this.currentQIndex = 0;
	}
	
	//The dispatcher gives jobs to the CPU and does context switching
	public void dispatch(PCB nProcess){
		//Take CPU from job
		if(readyQueue.size() != 10)
		{
			currentCPUProcess = cpu.pcbHolder.remove(0);
					//CPU time
			currentCPUProcess.startTime =cpu.cpuStartTime;
			currentCPUProcess.cpuEndTime = cpu.cpuEndTime;
			//If its the first time the job gets the CPU
			if(nProcess.pc == 0){
				cpu.cpuStartTime = System.currentTimeMillis();
			}
			
			//Sets the CPU variables from the PCB
			currentCPUProcess.pc = cpu.pc;
			currentCPUProcess.priority = cpu.priority;
			
			currentCPUProcess.registers = cpu.register;
			currentCPUProcess.state = ProcessState.READY;
			//Brings in the cache
			currentCPUProcess.cache = cpu.cache;
			
			//TIME
			
			//Turn around time
			currentCPUProcess.startTime = cpu.startTime;
			cpu.endTime = nProcess.endTime;
			cpu.completionTime = nProcess.completionTime;
			
			//Average times
			cpu.waitTime = nProcess.waitTime;
			cpu.numberIO = nProcess.numberIO;
			cpu.ramUsage = nProcess.ramUsage;
			cpu.cacheUsage = nProcess.cacheUsage;
			
			cpu.begin();
			//Long term scheduler runs again if the Ready queue falls under 5 jobs
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			
			//Once all the jobs are done you calculate averages 
			if(readyQueue.size() == 0){
				cpu.averagecompletionTime();
				cpu.averageWaitTime();
				cpu.averageNumberOfIORequests();
				cpu.averageRamUsageTime();
				cpu.averageCacheUsageTime();
				
			}
			
			
		}
		
		//Give CPU to next Job
		if(readyQueue.size() != 0)
		{
			
					//CPU time
					cpu.cpuStartTime = nProcess.startTime;
					cpu.cpuEndTime = nProcess.endTime;
			//If its the first time the job gets the CPU
			if(nProcess.pc == 0){
				cpu.cpuStartTime = System.currentTimeMillis();
			}
			
			//Sets the CPU variables from the PCB
			cpu.pc = nProcess.pc;
			cpu.priority = nProcess.priority;
			cpu.processAddress = nProcess.jobFileAddress;
			cpu.processLength = nProcess.jobFileLength;
			cpu.processId = nProcess.processId;
			cpu.register = nProcess.registers;
			//sets the statues to Running
			nProcess.state = ProcessState.RUNNING;
			//Brings in the cache
			cpu.cache = nProcess.cache;
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
			cpu.completionTime = nProcess.completionTime;
			
			//Average times
			cpu.waitTime = nProcess.waitTime;
			cpu.numberIO = nProcess.numberIO;
			cpu.ramUsage = nProcess.ramUsage;
			cpu.cacheUsage = nProcess.cacheUsage;
			//Gives jobs PCB to CPU
			cpu.pcbHolder.add(nProcess);
			
			cpu.begin();
			//Long term scheduler runs again if the Ready queue falls under 5 jobs
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			
			//Once all the jobs are done you calculate averages 
			if(readyQueue.size() == 0){
				cpu.averagecompletionTime();
				cpu.averageWaitTime();
				cpu.averageNumberOfIORequests();
				cpu.averageRamUsageTime();
				cpu.averageCacheUsageTime();
				
			}
			
			
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
