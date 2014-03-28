import java.util.ArrayList;
import java.util.concurrent.Semaphore;
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
	//Lock that has to be acquired to access this class is created 
	static Semaphore pcbLock = new Semaphore(1);

	
	public int processId;
	public int priority;
	
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
	public int memoryStart;
	public int memoryLength;
	
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

	/**
	 * Constructor for the PCB class
	 */
	public PCB() {
		children = new ArrayList<PCB>();
		registers = new long[16];
		cache = new ArrayList<boolean[]>();
		pc = 0;
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
		
	
	
}
