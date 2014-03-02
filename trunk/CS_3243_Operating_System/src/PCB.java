import java.util.ArrayList;

public class PCB {
	
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
	public ArrayList<boolean[]> iRegister;
	
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
	
	
	long waitTime;
	long numberIO;
	long ramUsage;
	long cacheUsage;


	public PCB() {
		children = new ArrayList<PCB>();
		registers = new long[16];
		iRegister = new ArrayList<boolean[]>();
		pc = 0;
	}
	
	/**
	 * Returns the estimated memory footprint of the process associated with this PCB. 
	 * @return estimated memory footprint
	 */
	public int getMemoryFootprint() {
		return jobFileLength + inputBufferLength + outputBufferLength + tempBufferLength;
	}
	
	
		
		
	
	
}
