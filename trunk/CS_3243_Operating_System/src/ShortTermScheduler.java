import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;

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
	 static Runnable cpu;
	//private static CPU cpu;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private static LongTermScheduler ltScheduler;
	private static Dispatcher dispatcher;
	public static AverageCalculator averageCalculator;
	
	private PCB currentProcess;
	private PCB nextProcess;
	
	//All process in one readyQueue in single processor but scheduling 4 readyQueue private to each processor in 4cpu each self scheduling 
	//to avoid more than one cpu accessing one process making scheduling difficult.
	private static ArrayList<PCB> readyQueue;
	private int currentQIndex;
	
	private SchedulingAlgorithm algorithm;
	
	//public Constructor instantiated in OSDriver.java
	public ShortTermScheduler(Dispatcher dispatcher,LongTermScheduler ltScheduler,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo, AverageCalculator averageCalculator) {
		this.dispatcher = dispatcher;
		this.ltScheduler = ltScheduler;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
		this.currentQIndex = 0;	
		this.averageCalculator = averageCalculator;
				
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
		
		//Once all the jobs are done you calculate averages 
				if(readyQueue.size() == 0){
					averageCalculator.averagecompletionTime();
					averageCalculator.averageWaitTime();
					averageCalculator.averageNumberOfIORequests();
					averageCalculator.averageRamUsageTime();
					averageCalculator.averageCacheUsageTime();
					
				}
		
	}
	
	private void fcfsSchedule() {
		
		
		while(readyQueue.size() != 0){
			//nextProcess =  readyQueue.remove(0);
			//dispatch(nextProcess);
			
			
			//Long term scheduler runs again if the Ready queue falls under 5 jobs
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			/**
			 * Thread tries to acquire the lock
			 */
			try {
				dispatcher.writeLock.acquire();
				//writeLock.acquire();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * if it acquires the lock it goes into critical section
			 */
			if(dispatcher.hasProcessForCPU() == false){
				try {
					nextProcess =  readyQueue.remove(0);
					dispatcher.shortTermProduce(nextProcess);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * lock is released after exit of critical section
			 */
			dispatcher.writeLock.release();
			//writeLock.release();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
			try {
				dispatcher.shortTermProduce(nextProcess);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
			
	}
	
	private void rrSchedule() {
		//fcfs with pre emption allocating cpu to each in circular readu queue upto 1 time quantum 10ms-100ms
		
		while(readyQueue.size() != 0){
			//see long term scheduling 
			nextProcess =  readyQueue.remove(0);
			try {
				dispatcher.shortTermProduce(nextProcess);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		
	}
	
	private void sjfSchedule() {
		
		//Shortest next CPU Burst algorithm -see long term scheduler
		// may be both pre emption and non pre emption
				
		while(readyQueue.size() != 0){
			//see long term scheduling 
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			try {
				dispatcher.writeLock.acquire();
				//writeLock.acquire();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dispatcher.hasProcessForCPU() == false){
				try {
					nextProcess =  readyQueue.remove(0);
					dispatcher.shortTermProduce(nextProcess);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dispatcher.writeLock.release();
			//writeLock.release();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
	}
	
}
