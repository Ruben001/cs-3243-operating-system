import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 *The Dispatcher is accessed by each CPU and the short term scheduler.
 *They can only access it by acquiring a lock (writeLock).
 *The short term scheduler uses it to give jobs to each CPU.
 *
 *
 * 
 * Ruben munive
 */

public  class  Dispatcher {
	
	//Lock that has to be acquired to access this class is created 
	static Semaphore dispatcherLock = new Semaphore(1);
	
	boolean killCPUs = false;
	
	//set to true when there is a job for the CPU 
	private  boolean cpuHasProcess = false;
	public synchronized boolean hasProcessForCPU(){
		return cpuHasProcess;
	}
	
	public synchronized void setHasProcessForCPU(boolean cpuHasProcess){
		this.cpuHasProcess = cpuHasProcess;
	}
	
	
	
	//Holds jobs PCB
	public ArrayList<PCB> pcbHolder = new ArrayList<PCB>();
	
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

	//What CPU can consume
	int whatCPUCunsumes = 0;
	
	
	
	public Dispatcher(){
		
	}
	
	public synchronized void shortTermProduce(PCB nProcess) throws InterruptedException{
		synchronized(this){
			
		//CPU time
		cpuStartTime = nProcess.cpuStartTime;
		cpuEndTime = nProcess.cpuEndTime;
	//If its the first time the job gets the CPU
	if(nProcess.pc == 0){
		cpuStartTime = System.currentTimeMillis();
	}
	
	//Sets the CPU variables from the PCB
	pc = nProcess.pc;
	priority = nProcess.priority;
	jobFileAddress = nProcess.jobFileAddress;
	jobFileLength = nProcess.jobFileLength;
	processId = nProcess.processId;
	registers = nProcess.registers;
	
	//sets the status to Running
	nProcess.state = ProcessState.RUNNING;
	
	//Brings in the cache
	cache = nProcess.cache;
	
	
	
	
	//Buffers
	inputBufferAddress = nProcess.inputBufferAddress;
	inputBufferLength = nProcess.inputBufferLength;
	outputBufferAddress = nProcess.outputBufferAddress;
	outputBufferLength = nProcess.outputBufferLength;
	tempBufferAddress = nProcess.tempBufferAddress;
	tempBufferLength = nProcess.tempBufferLength;
	//TIME
	
	//Turn around time
	startTime = nProcess.startTime;
	endTime = nProcess.endTime;
	completionTime = nProcess.completionTime;
	
	//Average times
	waitTime = nProcess.waitTime;
	numberIO = nProcess.numberIO;
	ramUsage = nProcess.ramUsage;
	cacheUsage = nProcess.cacheUsage;
		
	
	//Hold Job PCB
	pcbHolder.add(nProcess);
	
	//Tells CPU there is a job waiting
	//cpuHasProcess = true;
	setHasProcessForCPU(true);
		}
	}
	
	public synchronized void cpuProduce() throws InterruptedException{
		synchronized(this){
			
		}
	}
	
	public synchronized void shortTermConsume() throws InterruptedException{
		synchronized(this){
			
		}
	}
	
	public synchronized void cpuConsume(CPU cpu) throws InterruptedException{
		synchronized(this){
			//CPU time
		cpu.cpuStartTime = cpuStartTime;
		cpu.cpuEndTime = cpuEndTime;
		//If its the first time the job gets the CPU
		
		
		//Sets the CPU variables from the PCB
		cpu.pc = pc;
		cpu.priority = priority;
		cpu.processAddress = jobFileAddress;
		cpu.processLength = jobFileLength;
		cpu.processId = processId;
		cpu.register = registers;
		
		
		//Brings in the cache
		cpu.cache = cache;
		
		
		
		
		//Buffers
		cpu.inputBufferAddress = inputBufferAddress;
		cpu.inputBufferLength = inputBufferLength;
		cpu.outputBufferAddress = outputBufferAddress;
		cpu.outputBufferLength = outputBufferLength;
		cpu.tempBufferAddress = tempBufferAddress;
		cpu.tempBufferLength = tempBufferLength;
		//TIME
		
		//Turn around time
		cpu.startTime = startTime;
		cpu.endTime = endTime;
		cpu.completionTime = completionTime;
		
		//Average times
		cpu.waitTime = waitTime;
		cpu.numberIO = numberIO;
		cpu.ramUsage = ramUsage;
		cpu.cacheUsage = cacheUsage;
			
		
		//Hold Job PCB
		//cpu.pcbHolder.remove(0);
		cpu.pcbHolder =  pcbHolder;
		
		//Tells Dispatcher it can add a job
		
		//cpuHasProcess = false;
		setHasProcessForCPU(false);
		}
	}
	
	
	
}
