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
	
	private String algorithm;
	
	//public Constructor instantiated in OSDriver.java
	public ShortTermScheduler(Dispatcher dispatcher,LongTermScheduler ltScheduler,Memory memory,ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, String algo, AverageCalculator averageCalculator) {
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
		case "FCFS":
			fcfsSchedule();
			break;
		case "PRIORITY":
			prioritySchedule();
			break;
		case "SJF":
			sjfSchedule();
			break;
		}
		
		//Once all the jobs are done you calculate averages 
		if(readyQueue.size() == 0){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispatcher.killCPUs = true;
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
				dispatcher.dispatcherLock.acquire();
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
			dispatcher.dispatcherLock.release();
			//writeLock.release();
			/*
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}	
		
	}
	
	
	
	

	
	/**
	 * This method sorts an arrayList of jobs
	 * It sorts jobs in ascending order
	 */
	private static class PriorityComparator implements Comparator<PCB>{

		@Override
		public int compare(PCB o1, PCB o2) {
			return (o1.priority < o2.priority ) ? -1: (o1.priority > o2.priority) ? 1:0 ;
	
		}
      
    }

	
	
	
	
	

	private void prioritySchedule() {
		
		//Shortest next CPU Burst algorithm -see long term scheduler
		// may be both pre emption and non pre emption
		
		Collections.sort(readyQueue, new PriorityComparator());
				
		while(readyQueue.size() != 0){
			//see long term scheduling 
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			try {
				dispatcher.dispatcherLock.acquire();
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
			
			dispatcher.dispatcherLock.release();
			//writeLock.release();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
	}
	
	
	
	
	/**
	 * This method sorts an arrayList of jobs
	 * It sorts jobs in ascending order
	 */
	private static class JobComparator implements Comparator<PCB>{

		@Override
		public int compare(PCB o1, PCB o2) {
			return (o1.jobFileLength < o2.jobFileLength ) ? -1: (o1.jobFileLength > o2.jobFileLength) ? 1:0 ;
	
		}
	}
      
	
	
	
	
	
	private void sjfSchedule() {
		
		//Shortest next CPU Burst algorithm -see long term scheduler
		// may be both pre emption and non pre emption
		
		Collections.sort(readyQueue, new JobComparator());
				
		while(readyQueue.size() != 0){
			//see long term scheduling 
			if(readyQueue.size() < 5){
				ltScheduler.schedule();
			}
			try {
				dispatcher.dispatcherLock.acquire();
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
			
			dispatcher.dispatcherLock.release();
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
