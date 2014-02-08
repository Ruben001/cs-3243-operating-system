import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


public class LongTermScheduler {
	
//public static void main(String[] args) {
	private Queue<Integer> jobPriorityQueue;
	
	private int jobPriority;
	//private int jobId;
	//private int jobDiskAddress;
	
	//private jobMemoryAddress; job is written at in RAM
	//add a get set method for this member
		
	//constructor -initialisation
	public LongTermScheduler() {
		
		jobPriorityQueue = new PriorityQueue<>(20);
		
		/* 
		 * 	add jobs to priority queue to order jobs according to their priority 	
		 */
		for(int i=0;i<jobPriorityQueue.size();i++){//it should be 1024 max int on disk. length of count of process id on PCB initially
			jobPriority = PCB.getPriority();//required to write to memory from a given disk address 
			jobPriorityQueue.offer(new Integer(jobPriority));//add priority of each process id from PCB			
		}
	}
		
		/* 
		 * writes the top priority job to RAM when prompted by CPU		
		 */
		//I think it is to write one job at a time instead of looping through the whole queue 
		public void writeJobToRAM(){
			Integer topPriorityJob = jobPriorityQueue.poll();
			//System.out.println("Processing :"+topPrirityJob);
			
			//on basis of known priority, find jobId and address from PCB and get from Disk
			jobId = PCB.getProcessId(topJobPriority);
			jobDiskAddress = PCB.getAddress(jobId);
			
			//TO DO : RAM.writeBitToRam(line, bitPosition, readData(int address) )
			
			//TO DO :store pid's RAM address to PCB					
		}
}
//}
				
	