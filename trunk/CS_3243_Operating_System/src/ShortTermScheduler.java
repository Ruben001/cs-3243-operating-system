import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

/**
 * Multiple CPU- Each CPU with its own private readyQueue to prevent accessing same process by more than one CPU 
 * also to maintain process affinity -same process to same CPU to use cache efficiently
 * 
 */
public class ShortTermScheduler {
	private static CPU cpu;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private static LongTermScheduler ltScheduler;
	
	private PCB currentProcess;
	private PCB nextProcess;
	
	//All process in one readyQueue in single processor but scheduling 4 readyQueue private to each processor in 4cpu each self scheduling 
	//to avoid more than one cpu accessing one process making scheduling difficult.
	private ArrayList<PCB> readyQueue;
	private int currentQIndex;
	
	private SchedulingAlgorithm algorithm;
	
	//public Constructor instantiated in OSDriver.java
	public ShortTermScheduler(CPU cpu,LongTermScheduler ltScheduler,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.cpu = cpu;
		this.ltScheduler = ltScheduler;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
		this.currentQIndex = 0;	
				
	}
	
	public void ScheduleAndDispatch()
	{
		/**TODO Phase ii 
		 * Check for new processes arrival in ready queue periodically or set a flag to alert CPU scheduler to run again  
		 * then run cpu scheduler checking if it changes ready queue order   
		 * CPU Scheduler chooses a process from readyQueue, to which CPU could be allocated
	     depending on whether CPU has no process before and if the scheduling has to be preemptive.
	     */  	
		
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
		
		//Once the readyQueue is prepared based on selected scheduling Algorithm by LOngTermScheduler
		//Go in loop of 4 to allocate each cpu thread, a process if it is available in ready queu
		//each cpu after completing a process will call short term scheduler to get a new job with a flag
		//if true call else allocate in for loop
		//ready queue would be based on chosen algorithm already  
		
		while(readyQueue.size() != 0){
			
			//getPriority() of running process on CPU to compare with one at top of PriorityQueue  
			//remove low priority on cpu and add to ready queue based on priority  pre emption phase 11 not part 2
				
			/** Phase 2
			 if (!cpu.isRunning()) 
		    	 dispatch(nextProcess);

		      if (readyQueue.peek() != null && cpu.isRunning() &&
		            readyQueue.peek().getPriority() > cpu.getRunningPriority())
		            //swapProcesses(); 
		             remove and despatch as below.
		    */
			
			nextProcess =  readyQueue.remove(0);
			dispatch(nextProcess);				
		}
			
	}
	
	private void rrSchedule() {
		//fcfs with pre emption allocating cpu to each in circular readu queue upto 1 time quantum 10ms-100ms
		
		while(readyQueue.size() != 0){
			//see long term scheduling 
			nextProcess =  readyQueue.remove(0);
			dispatch(nextProcess);				
		}
		
	}
	
	private void sjfSchedule() {
		
		//Shortest next CPU Burst algorithm -see long term scheduler
		// may be both pre emption and non pre emption
				
		while(readyQueue.size() != 0){
			//see long term scheduling 
			nextProcess =  readyQueue.remove(0);
			dispatch(nextProcess);				
		}
		
				
	}
	
	
	//The dispatcher gives jobs to the CPU and does context switching
		public void dispatch(PCB nProcess){
			
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
			
			//sets the status to Running
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
			
			//Begins CPU running the process
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
