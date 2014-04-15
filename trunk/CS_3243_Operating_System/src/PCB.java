import java.util.ArrayList;
/**
 * This class behaves as Process Context Block
 * Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
 *
 */
/**
 * @author RAMI
 *
 */
public class PCB {
	
	public int processId;
	public int priority;
	
	public PageTable[] pageTable;
	
	public int pageFault;

	public ProcessState state;
	public int pc;
	
	// file information
	public int jobFileAddress;
	public int jobFileLength;
	public int inputBufferAddress;
	public int inputBufferLength;
	public int outputBufferAddress;
	public int outputBufferLength;
	public int tempBufferAddress;
	public int tempBufferLength;
	
	// memory
	//public int memoryStart;
	//public int memoryLength;
	
	// progeny
	public PCB parent;
	public ArrayList<PCB> children;
	
	// registers
	public long[] registers;
	
	//Cache
	public ArrayList<boolean[]> cache; // field variable for CPU cache
	
	//Time
	
	
	//Process CPU time
	long cpuStartTime;
	long cpuEndTime;
	long cpuBurstTime;
	//Turn around time
	long startTime;
	long endTime;
	long completionTime;
	
	long timeLimit;
	long timeDelay;
	
	//used to calculate averages
	long waitTime;
	long numberIO;
	long ramUsage;
	long cacheUsage;
	
	public int diskFileAddress;

	/**
	 * Constructor for the PCB class
	 */
	public PCB() {
		pageFault=0;
		children = new ArrayList<PCB>();
		registers = new long[16];
		cache = new ArrayList<boolean[]>();
		pc = 0;
	}

	public int getPageFault() {
		return pageFault;
	}

	public void setPageFault(int pageFault) {
		this.pageFault = pageFault;
	}

	/**
	 * Returns the estimated memory footprint of the process associated with this PCB. 
	 * @return estimated memory footprint
	 */
	public int getMemoryFootprint() {
		return jobFileLength + inputBufferLength + outputBufferLength + tempBufferLength;
	}
	
	
	/**
	 * Gets Process State - interested in the running state of process currently running on CPU
	 * @return
	 */
	public ProcessState getState() {
		return state;
	}

	/**
	 * Sets Process State -interested in setting state of process currently running on CPU i.e. swapping
	 * 
	 */

	public void setState(ProcessState state) {
		this.state = state;
	}


    /**
     * Gets the priority of the process currently running on CPU -
     *
     * @return The priority of the running process.
     */
	public int getPriority() {
		return priority;
		
		//TODO as interested in priority of process running on cpu, 
		//to see if good to have a list of running processes ordered by priority and size
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}	
	
	/**
	 * this method initiate page table for the pcb
	 */
	public void initiatePageTable(){
		int length = getMemoryFootprint();
		int div = length/OSDriver.memoryManager.getPAGE_SIZE();
		if(length%OSDriver.memoryManager.getPAGE_SIZE()>0){
			pageTable = new PageTable[div+1];
			for(int i =0; i<div+1;i++){
				pageTable[i]= new PageTable();
			}
		} else {
			pageTable = new PageTable[div];
			for(int i =0; i<div;i++){
				pageTable[i]= new PageTable();
			}
		}
	}
	
	
}
