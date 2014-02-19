import java.util.ArrayList;

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
			break;
		case RR:
			break;
		case SFJ:
			break;
		}
	}
	
	private void fcfsSchedule() {
		while (true) {
			if (pcbList.size() == 0)
				return;
			int[] memoryChunk = memory.GetLargestMemoryChunk();
			if (memoryChunk[1] >= pcbList.get(0).getMemoryFootprint()) {
				PCB pcb = pcbList.get(0);
				int memoryIndex = memoryChunk[0];
				for (int i = pcb.jobFileAddress; i < pcb.jobFileLength; ++i) {
					memory.writeData(memoryIndex, disk.readData(i));
				}
				for (int i = pcb.inputBufferAddress; i < pcb.inputBufferLength; ++i) {
					memory.writeData(memoryIndex, disk.readData(i));
				}
				for (int i = pcb.outputBufferAddress; i < pcb.outputBufferLength; ++i) {
					memory.writeData(memoryIndex, disk.readData(i));
				}
				for (int i = pcb.tempBufferAddress; i < pcb.tempBufferLength; ++i) {
					memory.writeData(memoryIndex, disk.readData(i));
				}
				readyQueue.add(pcb);
				pcbList.remove(pcb);
			}
			else
				return;
		}
	}
}