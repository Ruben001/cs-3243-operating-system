import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LongTermScheduler {
	private Disk disk;
	private Memory memory;
	private ArrayList<PCB> pcbList;
	private ArrayList<PCB> readyQueue;
	private SchedulingAlgorithm algorithm;
	
	public LongTermScheduler(Disk disk, Memory memory, ArrayList<PCB> pcbList, ArrayList<PCB> readyQueue, SchedulingAlgorithm algo) {
		this.disk = disk;
		this.memory = memory;
		this.pcbList = pcbList;
		this.readyQueue = readyQueue;
		this.algorithm = algo;
	}
	
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
	
	private void fcfsSchedule() {
		while (true) {
			if (pcbList.size() == 0)
				return;
			int[] memoryChunk = memory.GetLargestMemoryChunk();
			if (memoryChunk[1] < pcbList.get(0).getMemoryFootprint())
				return;
			PCB pcb = pcbList.get(0);
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
			pcbList.remove(pcb);
		}
	}
	
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