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
public class LongTermScheduler {
	/*
	 * Field variables for LongTermScheduler
	 */
	private Disk disk; 
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private ArrayList<PCB> readyQueue;
	private SchedulingAlgorithm algorithm;
	/**
	 * Constructor for LongTermScheduler
	 * @param takes in disk
	 * @param takes in memory
	 * @param takes in pcbList
	 * @param takes in readyQueue
	 * @param take is scheduling algorithm
	 */
	public LongTermScheduler(Disk disk, Memory memory, ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.disk = disk;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
	}
	/**
	 * This methods selects the proper method for scheduling i.e. FCFS, Priority, or SJF
	 * 
	 */
	public void schedule() {
		switch(algorithm)
		{
		case FCFS:
			fcfsSchedule();
			break;
		case PRIORITY:
			prioritySchedule();
			break;
		case RR:
			prioritySchedule();
			break;
		case SJF:
			shortestJobFirstSchedule();
			break;
		}
	}
	/**
	 * This method behaves as FCFS
	 * It sorts job according to the first come first serve rule
	 */
	private void fcfsSchedule() {
		while (true) {
			if (pcbList.size() == 0)
				return;
			int[] memoryChunk = memory.GetLargestMemoryChunk();
			if (memoryChunk[1] < pcbList.get(0).getMemoryFootprint())
				return;
			PCB pcb = pcbList.get(0);
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
		}
		
		
	}
	/**
	 * This method behaves as PrioritySchedule
	 * It sorts job according to the priority of the jobs
	 */
	private void prioritySchedule() {
		ArrayList<PCB> priorityList = (ArrayList<PCB>)pcbList.clone();
		Collections.sort(priorityList, new Comparator<PCB>() {
			@Override
			public int compare(PCB p1, PCB p2) {
				if (p1.priority == p2.priority)
					return 0;
				if (p1.priority > p2.priority)
					return 1;
				return -1;
			}
		});
		while (true) {
			if (priorityList.size() == 0)
				return;
			int[] memoryChunk = memory.GetLargestMemoryChunk();
			if (memoryChunk[1] < pcbList.get(0).getMemoryFootprint())
				return;
			PCB pcb = priorityList.get(0);
			int memoryIndex = memoryChunk[0];
			for (int i = pcb.jobFileAddress; i - pcb.jobFileAddress < pcb.jobFileLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.inputBufferAddress; i - pcb.inputBufferAddress < pcb.inputBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.outputBufferAddress; i - pcb.outputBufferAddress < pcb.outputBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.tempBufferAddress; i - pcb.tempBufferAddress < pcb.tempBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			readyQueue.add(pcb);
			pcb.startTime = System.currentTimeMillis();
			priorityList.remove(pcb);
			pcbList.remove(pcb);
		}
	}
	/**
	 * This method behaves as Shortest Job First
	 * It sorts job according to the shortest job first
	 */
	private void shortestJobFirstSchedule() {
		ArrayList<PCB> priorityList = (ArrayList<PCB>)pcbList.clone();
		Collections.sort(priorityList, new Comparator<PCB>() {
			@Override
			public int compare(PCB p1, PCB p2) {
				if (p1.priority == p2.priority)
					return 0;
				if (p1.jobFileLength < p2.jobFileLength)
					return 1;
				return -1;
			}
		});
		while (true) {
			if (priorityList.size() == 0)
				return;
			int[] memoryChunk = memory.GetLargestMemoryChunk();
			if (memoryChunk[1] < pcbList.get(0).getMemoryFootprint())
				return;
			PCB pcb = priorityList.get(0);
			int memoryIndex = memoryChunk[0];
			for (int i = pcb.jobFileAddress; i - pcb.jobFileAddress < pcb.jobFileLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.inputBufferAddress; i - pcb.inputBufferAddress < pcb.inputBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.outputBufferAddress; i - pcb.outputBufferAddress < pcb.outputBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			for (int i = pcb.tempBufferAddress; i - pcb.tempBufferAddress < pcb.tempBufferLength; ++i) {
				memory.writeData(memoryIndex++, disk.readData(i));
			}
			readyQueue.add(pcb);
			priorityList.remove(pcb);
			pcbList.remove(pcb);
		}
	}
}