import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * This class behaves as a CPU in this simulator.
 * It fetches jobs, decodes jobs, and executes job.
 * Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
 */


public class CPU implements Runnable {
	private static Memory memory; // field variable for Memory
	public static Dispatcher dispatcher;
	public static AverageCalculator averageCalculator;
	//int MemoryFootprint;
	//private static ShortTermScheduler stScheduler; // field variable for Short Term Scheduler
	public ArrayList<boolean[]> cache; // field variable for CPU cache
	
	public ArrayList<Long> accumulatorList;

	//Holds jobs PCB
	public ArrayList<PCB> pcbHolder;
	
	/*
	 * Phase 1 part-2
	 * If false the CPU does not do anything.  
	 * The dispatcher sets to true once it give a process to the CPU
	 */
	/*
	volatile boolean cpuIsRunning = true;
	volatile boolean cpuHasProcess = false;
	volatile boolean processIsDone = false;
	
	public synchronized void setCPUHasProcess(boolean has){
		this.cpuHasProcess = has;
	}
	
	public synchronized boolean getCPUHasProcess(){
		return this.cpuHasProcess;
	}
	*/
	
	private int cpuNumber = 0;
	
	//Registers
	static int pc; // this variable is a program counter 
	static long[] register; // field variables for Register in CPU
	
	static int baseRegister; // base register not implemented
	static int limitRegister; // limit register not implemented
	
	/*
	//Time
	public ArrayList<Long> completionTimeList; // field variable to keep track of processes completion time
	public ArrayList<Long> waitTimeList; // field variable to keep track of processes waiting time
	*/
	
	
	long waitTime; // field variable for wait time 
	
	//Process CPU time
	long cpuStartTime;
	long cpuEndTime;
	
	//Process start time and end time
	long startTime;
	long endTime;
	long completionTime;
	//keeps track of RAM usage
	public ArrayList<Long> ramUsageList; 
	long ramUsage;
	
	long pageFault;
	//keeps track of Cache usage
	//public ArrayList<Long> cacheUsageList; // field
	long cacheUsage;
	
	
	//Number of IO requests
	//public ArrayList<Long> numberIOList;
	long numberIO;
	
	DecimalFormat form = new DecimalFormat("#.00");
	
	
	//Process 
	int processId;
	int processAddress;
	int processLength;
	int priority;
	
	
	
	//buffers
	int inputBufferAddress;
	int inputBufferLength;
	int outputBufferAddress;
	int outputBufferLength;
	int tempBufferAddress;
	int tempBufferLength;
	
	//Decoding
	String opcodeString = "";
	
	int instruction;
	int opcode;
	int s1Reg;
	int s2Reg;
	int dReg;
	int bReg;
	int address;
	int reg1;
	int reg2;
	
	/**
	 * Construct for the CPU
	 * @param takes in memory
	 * @param takes in stScheduler
	 */
	public CPU( int cpuNumber, Memory memory, Dispatcher dispatcher, AverageCalculator averageCalculator){
		this.cpuNumber = cpuNumber;
		this.averageCalculator = averageCalculator;
		this.dispatcher = dispatcher;
		
		this.memory = memory;
		register = new long[16];
		cache = new ArrayList<boolean[]>();
		pcbHolder = new ArrayList<PCB>();
		accumulatorList = new ArrayList<Long>();
		/*
		//Average Times
		completionTimeList = new ArrayList<Long>();
		ramUsageList = new ArrayList<Long>();
		cacheUsageList = new ArrayList<Long>();
		waitTimeList = new ArrayList<Long>();
		numberIOList = new ArrayList<Long>();
		*/
	}
	/*
	 * Phase 1 part-2
	 * Starts the thread and executes once startCPU is true
	 */
	
	
	public synchronized void run(){
		System.out.println("CPU number: " + cpuNumber + " has started");
		
		try{
					FileOutputStream fos2 = new FileOutputStream("cpu.txt",true);
					PrintWriter pw2 = new PrintWriter( fos2 );
				
					pw2.print("\nCPU number: ");
					pw2.print(cpuNumber);
					pw2.print(" has started");
					pw2.close();
				}
				catch(FileNotFoundException fnfe){
					fnfe.printStackTrace();
				}
		while(true){
			
			
			
			try {
				dispatcher.dispatcherLock.acquire();
				//writeLock.acquire();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(dispatcher.hasProcessForCPU() == true){
				dispatcher.setHasProcessForCPU(false);
				this.begin();
			}
			dispatcher.dispatcherLock.release();
			//writeLock.release();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(dispatcher.killCPUs == true){
				System.out.println("CPU number: " + cpuNumber + " has terminated");
				
				try{
					FileOutputStream fos2 = new FileOutputStream("cpu.txt",true);
					PrintWriter pw2 = new PrintWriter( fos2 );
				
					pw2.print("\nCPU number: ");
					pw2.print(cpuNumber);
					pw2.print(" has terminated");
					pw2.close();
				}
				catch(FileNotFoundException fnfe){
					fnfe.printStackTrace();
				}
				
				break;
			}
		}
		
		
		
		//this.run();
		
	}
	public void begin(){
		
		try {
			dispatcher.cpuConsume(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("CPU number: " + cpuNumber + " is starting..with Process number: " +processId );
		//System.out.println("Loading Process number: " + processId  );
		//System.out.println("Loading Process length: " + processLength  );
		
		
		
		
		
try{
			
			
			
			FileOutputStream fos2 = new FileOutputStream("cpu.txt",true);
			PrintWriter pw2 = new PrintWriter( fos2 );
		
			pw2.print("\nCPU number: ");
			pw2.print(cpuNumber);
			pw2.print(" is starting..with Process number: ");
			pw2.print(processId);
			pw2.close();
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		try{
			
			FileOutputStream fos = new FileOutputStream("results.txt",true);
			PrintWriter pw = new PrintWriter( fos );
			
			
			
			pw.print("\nJob: ");
			pw.print(processId);
			pw.print("\nJob length: ");
			pw.print(processLength);
			pw.println();
			pw.close();
			
			
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		
		
		//Put the instructions into the Cache
		for(int i =0; i < processLength;i++){
			//fetch(processAddress + i);
			fetch(i);
			cacheUsage++;
		}
		
		//Starts decoding from cache
		while(pc < processLength){
			decode(cache.get(pc));
			cacheUsage++;
		}
		
		//this.cpuHasProcess = false;
		
	}
	
	
	/**
	 * This method fetches an instruction from the Memory
	 * @param takes in memory address
	 */
	private void fetch(int lineRam){
		try {
			memory.memoryLock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCache(OSDriver.memoryManager.fetchData(lineRam,pcbHolder.get(0)));
		//setCache(memory.readBinaryData(lineRam));
		memory.memoryLock.release();
		ramUsage++;
	}
	/**
	 * This is a setter for cache
	 * @param takes in a binaryArray of boolean
	 */
	private void setCache(boolean[] binaryArray){
		cache.add(binaryArray);
	}

	/**
	 * Receives a instruction and decodes the instruction format then
	 * it separates the appropriate sections of the instruction. 
	 * @param boolean array the represents the instruction.
	 * 
	 */
	public void decode(boolean[] binaryArray){
		
		
		String opcodeString = "";
		String s1RegString = "";
		String s2RegString = "";
		String dRegString = "";
		String bRegString = "";
		String addressString = "";
		String reg1String = "";
		String reg2String = "";
		
		instruction = 0;
		opcode = 0;
		s1Reg = 0;
		s2Reg = 0;
		dReg = 0;
		bReg = 0;
		address = 0;
		reg1 = 0;
		reg2 = 0;
		
		int count = 29;
		
		for(int i = 0; i < 6;i++){
			if(binaryArray[count] == true){
				opcodeString = opcodeString + "1";
				count--;
			}
			else{
				opcodeString = opcodeString + "0";
				count--;
			}
			 
			
		}
		opcode = Integer.parseInt(opcodeString,2);
		//System.out.println("Opcode: " + opcode);
		//00
		if(binaryArray[31] == false && binaryArray[30] == false){
			//System.out.println("Arithmetic instruction format: " );
			
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					s1RegString = s1RegString + "1";
					count--;
				}
				else{
					s1RegString = s1RegString + "0";
					count--;
				}
			}
			s1Reg = Integer.parseInt(s1RegString,2);
			//System.out.println("S1-Reg: " + s1Reg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					s2RegString = s2RegString + "1";
					count--;
				}
				else{
					s2RegString = s2RegString + "0";
					count--;
				}
			}
			s2Reg = Integer.parseInt(s2RegString,2);
			//System.out.print(" S2-Reg: " + s2Reg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					dRegString = dRegString + "1";
					count--;
				}
				else{
					dRegString = dRegString + "0";
					count--;
				}
			}
			dReg = Integer.parseInt(dRegString,2);
			//System.out.print(" D-Reg: " + dReg);
		}
		//01
		else if(binaryArray[31] == false && binaryArray[30] == true){
			//System.out.println("Conditional Branch and Immediate format: " );
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					bRegString = bRegString + "1";
					count--;
				}
				else{
					bRegString = bRegString + "0";
					count--;
				}
				
			}
			bReg = Integer.parseInt(bRegString,2);
			//System.out.print(" B-Reg: " + bReg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					dRegString = dRegString + "1" ;
					count--;
				}
				else{
					dRegString = dRegString + "0" ;
					count--;
				}
				
			}
			dReg = Integer.parseInt(dRegString,2);
			//System.out.print(" D-Reg: " + dReg);
			for(int i = 0; i < 16;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1";
					count--;
				}
				else{
					addressString = addressString + "0";
					count--;
				}
				
				
			}
			address = Integer.parseInt(addressString,2);
			//System.out.print(" address: " + address);
		}
		//10
		else if(binaryArray[31] == true && binaryArray[30] == false){
			//System.out.println("Unconditional Jump format: " );
			for(int i = 0; i < 24;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1";
					count--;
				}
				else{
					addressString = addressString + "0";
					count--;
				}
				
			}
			address = Integer.parseInt(addressString,2);
			//System.out.print(" address: " + address);
			
		}
		//11
		else if(binaryArray[31] == true && binaryArray[30] == true){
			//System.out.println("Input and Output instruction format: " );
			//numberIO++;
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					reg1String = reg1String + "1";
					count--;
				}
				else{
					reg1String = reg1String + "0";
					count--;
				}
				
			}
			reg1 = Integer.parseInt(reg1String,2);
			//System.out.print(" Reg1: " + reg1);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					reg2String = reg2String + "1";
					count--;
				}
				else{
					reg2String = reg2String + "0";
					count--;
				}
				
			}
			reg2 = Integer.parseInt(reg2String,2);
			//System.out.print(" Reg2: " + reg2);
			for(int i = 0; i < 16;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1" ;
					count--;
				}
				else{
					addressString = addressString + "0";
					count--;
				}
			}
			address = Integer.parseInt(addressString,2);
			//System.out.print(" address: " + address);
			
		}
		
		execute(opcodeString);
		
	}
	/**
	 * Receives the Opcode and then sets it up with its
	 * instruction set 
	 * @param A binary string of the opcode.
	 * 
	 */
	private void execute(String opcodeS){
		
		switch(opcodeS){
		case "000000"://0
			//System.out.println("Instruction: RD  Type: I/O" );
			//Reads content of I/P buffer into a accumulator
			ramUsage++;
			numberIO++;
			if(address > 0){
				try {
					memory.memoryLock.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//register[reg1] = memory.readData(processAddress + inputBufferAddress);
				register[reg1] = OSDriver.memoryManager.fetchLongData(inputBufferAddress - processAddress , pcbHolder.get(0));
				memory.memoryLock.release();
				pc++;
				break;
				
			}
			else{
				try {
					memory.memoryLock.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//register[reg1] = memory.readData((int)register[reg2]);
				register[reg1] = OSDriver.memoryManager.fetchLongData((int)register[reg2] - processAddress, pcbHolder.get(0));
				memory.memoryLock.release();
				pc++;
				break;
			}
			
			
		case "000001"://1
			//System.out.println("Instruction: WR  Type: I/O" );
			//Writes the content of accumulator into O/P buffer
			ramUsage++;
			numberIO++;
			try {
				memory.memoryLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//memory.writeData(outputBufferAddress, register[reg1]);
			accumulatorList.add(register[reg1]);
			OSDriver.memoryManager.writeData(outputBufferAddress, pcbHolder.get(0), register[reg1]);
			memory.memoryLock.release();
			pc++;
			break;
			
		case "000010"://2
			//System.out.println("Instruction: ST  Type: I" );
			//Stores content of a reg. into an address
			try {
				memory.memoryLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//memory.writeData((int)register[dReg], register[bReg]);
			OSDriver.memoryManager.writeData((int)register[dReg], pcbHolder.get(0), register[bReg]);
			memory.memoryLock.release();
			pc++;
			break;
		case "000011"://3
			//System.out.println("Instruction: LW  Type: I" );
			//Loads the content of an address into a reg.
			try {
				memory.memoryLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//register[dReg] = memory.readData((int)register[bReg]);
			register[dReg] = OSDriver.memoryManager.fetchLongData((int)register[bReg] - processAddress , pcbHolder.get(0));
			memory.memoryLock.release();
			pc++;
			break;
		case "000100"://4
			//System.out.println("Instruction: MOV  Type: R" );
			//Transfers the content of two S2-reg into S1-reg
			register[s1Reg] = register[s2Reg];
			pc++;
			break;
		case "000101"://5
			//System.out.println("Instruction: ADD  Type: R" );
			//Adds content of two S-regs into D-reg
			register[dReg] = register[s1Reg] + register[s2Reg];
			
			pc++;
			break;
			
		case "000110"://6
			//System.out.println("Instruction: SUB  Type: R" );
			//Subtracts content of two S-regs into D-reg
			if(register[s1Reg] > register[s2Reg]){
				register[dReg] = register[s1Reg] - register[s2Reg];
				pc++;
				break;
			}
			else{
				register[dReg] = register[s2Reg] - register[s1Reg];
				pc++;
				break;
			}
			
			
		case "000111"://7
			//System.out.println("Instruction: MUL  Type: R" );
			//Multiplies content of two S-regs into D-reg
			register[dReg] = register[s1Reg] * register[s2Reg];
			pc++;
			break;
		case "001000"://8
			//System.out.println("Instruction: DIV  Type: R" );
			//Divides content of two S-regs into D-reg
			if(register[s2Reg] == 0){
				//System.out.println("Process number: " + processId + "didn't execute becuase it divided by 0");
				pc++;
				break;
			}
			else if(register[s1Reg] > register[s2Reg]){
				
				register[dReg] = register[s1Reg] / register[s2Reg];
				pc++;
				break;
			}
			else{
				register[dReg] = register[s2Reg] / register[s1Reg];
				pc++;
				break;
			}
		case "001001"://9
			//System.out.println("Instruction: AND  Type: R" );
			//Logical AND of two S-regs into D-reg
			if(register[s1Reg] == 1 && register[s2Reg] == 1){
				register[dReg] = 1;
				pc++;
				break;
			}
			else{
				register[dReg] = 0;
				pc++;
				break;
			}
		case "001010"://10
			//System.out.println("Instruction: OR  Type: R" );
			//Logical OR of two S-regs into D-reg
			if((register[s1Reg] == 1) || (register[s2Reg] == 1)){
				register[dReg] = 1;
				pc++;
				break;
			}
			else{
				register[dReg] = 0;
				pc++;
				break;
			}
			
		case "001011"://11
			//System.out.println("Instruction: MOVI  Type: I" );
			//Transfers address/data directly into a register
			if(bReg == 0){
				register[dReg] = address;
				pc++;
				break;
			}
			//?
			
			
		case "001100"://12
			//System.out.println("Instruction: ADDI  Type: I" );
			//Adds a data directly to the content of a register
			if (address % 4 == 0 && address != 1) {
				register[dReg] = register[dReg] + (address/4);
				pc++;
				break;
			}else{
				register[dReg] = register[dReg] + address;
				pc++;
				break;
			}
		case "001101"://13
			//System.out.println("Instruction: MULI  Type: I" );
			//Multiplies a data directly to the content of a register
			if (address % 4 == 0 && address != 1) {
				register[dReg] = register[dReg] * (address/4);
				pc++;
				break;
			}else{
				register[dReg] = register[dReg] * address;
				pc++;
				break;
			}
			
		case "001110"://14
			//System.out.println("Instruction: DIVI  Type: I" );
			//Divides a data directly to the content of a register
			if (address % 4 == 0 && address != 1) {
				register[dReg] = register[dReg] / (address/4);
				pc++;
				break;
			}else{
				register[dReg] = register[dReg] / address;
				pc++;
				break;
			}
		case "001111"://15
			//System.out.println("Instruction: LDI  Type: I" );
			//Loads a data/address directly to the content of a register
			if ((address/4) == processLength) {
				register[dReg] = (inputBufferAddress);
				pc++;
				break;
			}
			else if((address/4) == (processLength + inputBufferLength)){
				register[dReg] = (outputBufferAddress);
				pc++;
				break;
			}
			else if(((address/4) == (processLength + inputBufferLength + outputBufferLength))){
				register[dReg] = (tempBufferAddress);
				pc++;
				break;
			}
			else{
				register[dReg] = address/4;
				pc++;
				break;
			}
			
		case "010000"://16
			//System.out.println("Instruction: SLT  Type: R" );
			//Sets the D-reg to 1 if first S-reg is less than 
			//second S-reg, 0 otherwise
			//System.out.println("s1Reg: " + register[s1Reg] +	"<" + " s2Reg" + register[s2Reg] );
			if(register[s1Reg] < register[s2Reg]){
				register[dReg] = 1;
				pc++;
				break;
			}
			else{
				register[dReg] = 0;
				pc++;
				break;
			}
		case "010001"://17?? DATA?
			//System.out.println("Instruction: SLTI  Type: I" );
			//Sets the D-reg to 1 if first S-reg is less 
			//than a data, and 0 otherwise
			if(register[s1Reg] < register[s2Reg]){
				register[dReg] = 1;
				pc++;
				break;
			}
			else{
				register[dReg] = 0;
				pc++;
				break;
			}
		case "010010"://18
			//System.out.println("Instruction: HLT  Type: J" );
			//Logical end of program
			//System.out.println("****************Job" + processId+ ": " + register[0] + "*************");
			
			//End process time
			endTime = System.currentTimeMillis();
			//Process CPU time
			cpuEndTime = System.currentTimeMillis();
			completionTime = cpuEndTime - cpuStartTime;
			//Process turn around time
			//completionTime = endTime - startTime;
			//completionTimeList.add(completionTime);
			
			//Process wait time
			waitTime = cpuStartTime - startTime;
			//waitTimeList.add(waitTime);
			pageFault = pcbHolder.get(0).pageFault;
			//number of IO requests put onto a Array
			//numberIOList.add(numberIO);
			
			
			//ramUsageList.add(ramUsage);
			
			//cacheUsageList.add(cacheUsage);
			
			try {
				averageCalculator.calculatorLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			averageCalculator.addToCompletionTimeList(completionTime);
			averageCalculator.addToWaitTimeList(waitTime);
			averageCalculator.addToNumberIOList(numberIO);
			averageCalculator.addToRamUsageListt(ramUsage);
			averageCalculator.addToCacheUsageList(cacheUsage);
			averageCalculator.addToPageFaultList(pageFault);
			
			averageCalculator.calculatorLock.release();
			
			System.out.println("completion time: " + completionTime + " Wait time: " + waitTime + " Number IO requests: " + numberIO);
			try{
				
				FileOutputStream fos = new FileOutputStream("results.txt",true);
				PrintWriter pw = new PrintWriter( fos );
				
				FileOutputStream fos2 = new FileOutputStream("cpu.txt",true);
				PrintWriter pw2 = new PrintWriter( fos2 );
				
				pw.print("\nCompletion time: ");
				pw.print(completionTime);
				pw.print(" Wait time: ");
				pw.print(waitTime);
				pw.print(" Number IO requests: ");
				pw.print(numberIO);
				pw.print(" \nRam Usage: ");
				pw.print(ramUsage);
				pw.print(" Cache Usage: ");
				pw.print(cacheUsage);
				pw.print(" Page Fault: ");
				pw.print(pageFault);
				
				pw.print("\n****************Accumulator");
				pw.print(": ");
				pw.print(accumulatorList.toString());
				pw.print("*************");
				
				
				pw2.print("\nCompletion time: ");
				pw2.print(completionTime);
				pw2.print(" Wait time: ");
				pw2.print(waitTime);
				pw2.print(" Number IO requests: ");
				pw2.print(numberIO);
				
				accumulatorList.clear();
				
				
				
				pw.println();
				pw.close();
				pw2.println();
				pw2.println();
				pw2.close();
			}
			catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
				
				
			}
			
			//Free up memory for next process
			try {
				memory.memoryLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//memory.free(processAddress,(processLength + inputBufferLength + outputBufferLength + tempBufferLength));
			OSDriver.memoryManager.freeMemory(pcbHolder.get(0));
			pcbHolder.remove(0);
			memory.memoryLock.release();
			pc++;
			break;
		case "010011"://19
			System.out.println("Instruction: NOP  Type: -" );
			//Does nothing and moves to next instruction
			pc++;
			break;
		case "010100"://20
			System.out.println("Instruction: JMP  Type: J" );
			//Jumps to a specified location
			pc = address/4;
			break;
		case "010101"://21
			System.out.println("Instruction: BEQ  Type: I" );
			System.out.println("BReg= " + register[bReg] + "dReg= " + register[dReg]);
			//Branches to an address when content of B-reg = D-reg
			if(register[bReg] == register[dReg]){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
			
		case "010110"://22
			System.out.println("Instruction: BNE  Type: I" );
			System.out.println("BReg= " + register[bReg] + "dReg= " + register[dReg]);
			//Branches to an address when content of B-reg != D-reg
			if(register[bReg] != register[dReg]){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
			
		case "010111"://23
			System.out.println("Instruction: BEZ  Type: I" );
			//Branches to an address when content of B-reg = 0
			if(register[bReg] == 0 ){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
		case "011000"://24
			System.out.println("Instruction: BNZ  Type: I" );
			//Branches to an address when content of B-reg <> 0
			if(register[bReg] != 0 ){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
		case "011001"://25
			System.out.println("Instruction: BGZ  Type: I" );
			//Branches to an address when content of B-reg > 0
			if(register[bReg] > 0 ){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
		case "011010"://26
			System.out.println("Instruction: BLZ  Type: I" );
			//Branches to an address when content of B-reg < 0
			if(register[bReg] < 0 ){
				//Branch
				pc = (address/4);
				
				break;
			}
			else{
				//keep going
				pc++;
				break;
			}
		default:
			
			
			try{
				
				FileOutputStream fos = new FileOutputStream("results.txt",true);
				PrintWriter pw = new PrintWriter( fos );
				
				pw.print("\nDid not read Opcode");
				pw.println();
				pw.close();
			}
			catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
				
				
			}
			
			break;
		}
		
	}
	
	
	
}
