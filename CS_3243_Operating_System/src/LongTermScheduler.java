import java.util.PriorityQueue;
import java.util.Queue;

public class LongTermScheduler {
	
//public static void main(String[] args) {
	
	private Queue<Integer> jobPriorityQueue;
	private int jobPriority;
	//private int jobId;
	//private int jobDiskAddress;
	//private jobMemoryAddress; 
	
	//private PCB pcb; 
			
	/*
	 * constructor -initialisation
	 */
	public LongTermScheduler() {
		
		jobPriorityQueue = new PriorityQueue<>(20);//for 20 jobs 
		
		/* 
		 * 	adds jobs to priority queue to order jobs according to their priority 	
		 */
		for(int i=0;i<jobPriorityQueue.size();i++){//it should be count of processes loaded on PCB 
			//jobPriority = pcb.getPriority();//required to sort processes in order of priority 
			jobPriorityQueue.offer(new Integer(jobPriority));//add priority of each process from PCB			
		}
	}
		
		/* 
		 * writes one top job to RAM when prompted by CPU-//instead of whole disk-queue at once  
		 */
		public void writeJobToRAM(){
			Integer topPriorityJob = jobPriorityQueue.poll();
			//jobId = PCB.getProcessId(topPriorityJob);
			//jobDiskAddress = PCB.getAddress(jobId);
			
			//RAM.writeBitToRam(line, bitPosition, readData(address) )
			
			//TO DO :store pid's RAM address(line and bit position to PCB					
		}
}
//}
				
	