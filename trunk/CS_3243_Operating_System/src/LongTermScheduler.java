import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


public class LongTermScheduler {
	
public static void main(String[] args) {//?
	
	//Read from Disk Class where data is in int Array
	// public int Disk.readData(int address)
	//Write data to RAM class 
	//public void writeBitToRam(int line, int bitPosition, int data){
	//public void writeLineToRam(int line, int[] data){
	//to find beginning of job, by tracking size of each job?
	//2 d array line x bit- each line will take one 32 bit word of disk 
	////from where to take priority? from PCB? to order these
	
	//to be modified and use the code in short term scheduler for ready q  
	int i;
	Random rand = new Random();
	Queue<Integer> integerPriorityQueue = new PriorityQueue<>(7);
	//assuming 7 integer job id entering queue with random priority, with highest priority at peek 
		
	for(i=0;i<7;i++){
		integerPriorityQueue.add(new Integer(rand.nextInt(100)));
	}

	for(i=0;i<7;i++){
		Integer in = integerPriorityQueue.poll();
		System.out.println("Processing Integer:"+in);
	}
}
}

