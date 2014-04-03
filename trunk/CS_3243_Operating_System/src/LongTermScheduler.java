import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * This class behaves as a Long Term Scheduler
 * Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
 *
 */
public class LongTermScheduler implements Runnable{
	/*
	 * Field variables for LongTermScheduler
	 */
	private Disk disk; 
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private static ArrayList<PCB> readyQueue;
	private String algorithm;
	//private SchedulingAlgorithm algorithm;
	//Update, algorithm has been changed from type ShedulingAlgorithm to String
	//to enable input type from the GUI to be used

	/**
	 * Constructor for LongTermScheduler
	 * @param takes in disk
	 * @param takes in memory
	 * @param takes in pcbList
	 * @param takes in readyQueue
	 * @param take is scheduling algorithm
	 */
	//public LongTermScheduler(Disk disk, Memory memory, ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
	public LongTermScheduler(Disk disk, Memory memory, ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, String algo) {
		this.disk = disk;
		this.memory = memory;
		this.pcbList = pcbList;
		LongTermScheduler.readyQueue = readyQueue;
		this.algorithm = algo;
	}
	
	public void run(){
		this.schedule();
	}

	/**
	 * This methods selects the proper method for scheduling i.e. FCFS, Priority, or SJF
	 * 
	 */
	public void schedule() {
		switch(algorithm)
		{
		case "FCFS":	
					
			fcfsSchedule();
						
			break;
		case "PRIORITY":
			prioritySchedule();
			break;
		case "RR":
			prioritySchedule();
			break;
		case "SJF":
			shortestJobFirstSchedule();
			break;
		}
	}
	
		private void shortestJobFirstSchedule() {
			
			/*
			 * disk lock
			 */
			try {
				Disk.diskLock.acquire();
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			
			/*
			 * pcb lock
			 */
			 try {
			
				PCB.pcbLock.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			ArrayList<PCB> priorityList = new ArrayList<PCB>(pcbList);
			
			System.out.println("initial list");
			
			for(int i=0; i<priorityList.size();i++)
				System.out.println(priorityList.get(i).getMemoryFootprint());
			
			
			
			Collections.sort(priorityList, new JobComparator());
					
			
			System.out.println("sorted list");
			for(int i=0; i<priorityList.size();i++)
				System.out.println(priorityList.get(i).getMemoryFootprint());
			int count = 0;
			while (true) {
				if (priorityList.size() == 0)
					return;
				
				/*
				 * memory lock
				 */
				try {
					Memory.memoryLock.acquire();
									
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				int[] memoryChunk = memory.GetLargestMemoryChunk();
				
				if (memoryChunk[1] < priorityList.get(0).getMemoryFootprint()){
					System.out.println("Return called here");
					return;
				}
				
				count++;
				PCB pcb = priorityList.get(0);
				System.out.println(priorityList.get(0));
				System.out.println("Size of job is " + priorityList.get(0).getMemoryFootprint() );
				int memoryIndex = memoryChunk[0];
				int tempIndex = memoryIndex;
				for (int i = pcb.jobFileAddress; i - pcb.jobFileAddress < pcb.jobFileLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.jobFileAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.inputBufferAddress; i - pcb.inputBufferAddress < pcb.inputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.inputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.outputBufferAddress; i - pcb.outputBufferAddress < pcb.outputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.outputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.tempBufferAddress; i - pcb.tempBufferAddress < pcb.tempBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				
				Memory.memoryLock.release();
				Disk.diskLock.release();
				
				pcb.tempBufferAddress = tempIndex;
				readyQueue.add(pcb);
				pcb.startTime = System.currentTimeMillis();
				pcbList.remove(pcb);
				priorityList.remove(pcb);
				System.out.println(count);
				
				PCB.pcbLock.release();
			}
			
		}
		

		private void prioritySchedule() {
			
			ArrayList<PCB> priorityList = new ArrayList<PCB>(pcbList);
			
			System.out.println("initial list");
			
			for(int i=0; i<priorityList.size();i++)
				System.out.println(priorityList.get(i).getMemoryFootprint());
			
			
			
			Collections.sort(priorityList, new PriorityComparator());
			
			System.out.println("priority list");
			
			for(int i=0; i<priorityList.size();i++)
				System.out.println(priorityList.get(i).getPriority());
					
			int count=0;
			while (true) {
				if (priorityList.size() == 0)
					return;
				int[] memoryChunk = memory.GetLargestMemoryChunk();
				if (memoryChunk[1] < priorityList.get(0).getMemoryFootprint()){
					System.out.println("return called here");
					return;
				}
				
				count++;
				PCB pcb = priorityList.get(0);
				int memoryIndex = memoryChunk[0];
				int tempIndex = memoryIndex;
				for (int i = pcb.jobFileAddress; i - pcb.jobFileAddress < pcb.jobFileLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.jobFileAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.inputBufferAddress; i - pcb.inputBufferAddress < pcb.inputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.inputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.outputBufferAddress; i - pcb.outputBufferAddress < pcb.outputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.outputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.tempBufferAddress; i - pcb.tempBufferAddress < pcb.tempBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.tempBufferAddress = tempIndex;
				readyQueue.add(pcb);
				pcb.startTime = System.currentTimeMillis();
				pcbList.remove(pcb);
				priorityList.remove(pcb);
				System.out.println(count);
			}
		
	}

		private void fcfsSchedule() {
			
			int count = 0;
			while (true) {
				if (pcbList.size() == 0)
					return;
				int[] memoryChunk = memory.GetLargestMemoryChunk();
				if (memoryChunk[1] < pcbList.get(0).getMemoryFootprint())
					return;
				count++;
				PCB pcb = pcbList.get(0);
				//System.out.println(pcbList.get(0));
				//System.out.println("Size of job is " + pcbList.get(0).getMemoryFootprint() );
				int memoryIndex = memoryChunk[0];
				int tempIndex = memoryIndex;
				for (int i = pcb.jobFileAddress; i - pcb.jobFileAddress < pcb.jobFileLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.jobFileAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.inputBufferAddress; i - pcb.inputBufferAddress < pcb.inputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.inputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.outputBufferAddress; i - pcb.outputBufferAddress < pcb.outputBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.outputBufferAddress = tempIndex;
				tempIndex = memoryIndex;
				for (int i = pcb.tempBufferAddress; i - pcb.tempBufferAddress < pcb.tempBufferLength; ++i) {
					memory.writeData(memoryIndex++, disk.readData(i));
				}
				pcb.tempBufferAddress = tempIndex;
				pcb.startTime = System.currentTimeMillis();
				readyQueue.add(pcb);
				pcb.startTime = System.currentTimeMillis();
				pcbList.remove(pcb);
				System.out.println(count);
			}
			
			
		}
			
			
	}


	/**
	 * This method sorts an arrayList of jobs
	 * It sorts jobs in ascending order
	 */
	class JobComparator implements Comparator<PCB>{

		public JobComparator() {
			
		}

		@Override
		public int compare(PCB o1, PCB o2) {
			return (o1.jobFileLength < o2.jobFileLength ) ? -1: (o1.jobFileLength > o2.jobFileLength) ? 1:0 ;
	
		}
      
    }
	
	/**
	 * This method sorts an arrayList of jobs
	 * It sorts jobs in ascending order
	 */
	class PriorityComparator implements Comparator<PCB>{

		@Override
		public int compare(PCB o1, PCB o2) {
			return (o1.priority < o2.priority ) ? -1: (o1.priority > o2.priority) ? 1:0 ;
	
}
	}
      	

