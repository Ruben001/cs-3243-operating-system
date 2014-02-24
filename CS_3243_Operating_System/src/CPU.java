import java.util.ArrayList;


public class CPU {
	private static Memory memory;
	//int MemoryFootprint;
	private ArrayList<PCB> pcbList;
	private static ShortTermScheduler stScheduler;
	private ArrayList<boolean[]> iRegister;
	
	//Registers
	static int pc; // this variable is a program counter 
	//static int[][] register;
	static long[] register;
	
	static int baseRegister;
	static int limitRegister;
	
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
	
	
	public CPU( Memory memory, ShortTermScheduler stScheduler){
		
		this.memory = memory;
		this.stScheduler = stScheduler;
		
		
		//register=new int[16][32];
		register = new long[16];
		iRegister = new ArrayList<boolean[]>();
		
		

	}
	public void begin(){
		
		
		System.out.println("\n\n\n\n\nLoading Process number: " + processId  );
		System.out.println("Loading Process length: " + processLength  );
		/*
		for(int i = pc; i < processLength;i++){
			fetch(processAddress + pc);
			pc = ++pc;
			*/
		//Put the instructions into the instruction register
		for(int i =0; i < processLength;i++){
			fetch(processAddress + i);
		}
		
		//Starts decoding from iRegister
		while(pc < processLength){
			decode(iRegister.get(pc));
			
		}
		
	}
	private void fetch(int lineRam){
		instructionRegister(memory.readBinaryData(lineRam));
		//decode(memory.readBinaryData(lineRam));
		
	}
	
	private void instructionRegister(boolean[] binaryArray){
		iRegister.add(binaryArray);
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
		System.out.println("Opcode: " + opcode);
		//00
		if(binaryArray[31] == false && binaryArray[30] == false){
			System.out.println("Arithmetic instruction format: " );
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
			System.out.println("S1-Reg: " + s1Reg);
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
			System.out.print(" S2-Reg: " + s2Reg);
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
			System.out.print(" D-Reg: " + dReg);
		}
		//01
		else if(binaryArray[31] == false && binaryArray[30] == true){
			System.out.println("Conditional Branch and Immediate format: " );
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
			System.out.print(" B-Reg: " + bReg);
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
			System.out.print(" D-Reg: " + dReg);
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
			System.out.print(" address: " + address);
		}
		//10
		else if(binaryArray[31] == true && binaryArray[30] == false){
			System.out.println("Unconditional Jump format: " );
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
			System.out.print(" address: " + address);
			
		}
		//11
		else if(binaryArray[31] == true && binaryArray[30] == true){
			System.out.println("Input and Output instruction format: " );
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
			System.out.print(" Reg1: " + reg1);
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
			System.out.print(" Reg2: " + reg2);
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
			System.out.print(" address: " + address);
			
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
			System.out.println("Instruction: RD  Type: I/O" );
			//Reads content of I/P buffer into a accumulator
			if(address > 0){
				register[reg1] = memory.readData(inputBufferAddress);
				pc++;
				break;
				
			}
			else{
				register[reg1] = memory.readData((int)register[reg2]);
				pc++;
				break;
			}
			
			
		case "000001"://1
			System.out.println("Instruction: WR  Type: I/O" );
			//Writes the content of accumulator into O/P buffer
			memory.writeData(outputBufferAddress, register[0]);
			pc++;
			break;
			
		case "000010"://2
			System.out.println("Instruction: ST  Type: I" );
			//Stores content of a reg. into an address
			memory.writeData((int)register[dReg], register[bReg]);
			pc++;
			break;
		case "000011"://3
			System.out.println("Instruction: LW  Type: I" );
			//Loads the content of an address into a reg.
			register[dReg] = memory.readData((int)register[bReg]);
			pc++;
			break;
		case "000100"://4
			System.out.println("Instruction: MOV  Type: R" );
			//Transfers the content of two S2-reg into S1-reg
			register[s1Reg] = register[s2Reg];
			pc++;
			break;
		case "000101"://5
			System.out.println("Instruction: ADD  Type: R" );
			//Adds content of two S-regs into D-reg
			register[dReg] = register[s1Reg] + register[s2Reg];
			pc++;
			break;
			
		case "000110"://6
			System.out.println("Instruction: SUB  Type: R" );
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
			System.out.println("Instruction: MUL  Type: R" );
			//Multiplies content of two S-regs into D-reg
			register[dReg] = register[s1Reg] * register[s2Reg];
			pc++;
			break;
		case "001000"://8
			System.out.println("Instruction: DIV  Type: R" );
			//Divides content of two S-regs into D-reg
			if(register[s1Reg] > register[s2Reg]){
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
			System.out.println("Instruction: AND  Type: R" );
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
			System.out.println("Instruction: OR  Type: R" );
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
			System.out.println("Instruction: MOVI  Type: I" );
			//Transfers address/data directly into a register
			if(bReg == 0){
				register[dReg] = address;
				pc++;
				break;
			}
			//?
			
			
		case "001100"://12
			System.out.println("Instruction: ADDI  Type: I" );
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
			System.out.println("Instruction: MULI  Type: I" );
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
			System.out.println("Instruction: DIVI  Type: I" );
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
			System.out.println("Instruction: LDI  Type: I" );
			//Loads a data/address directly to the content of a register
			if (address % 4 == 0) {
				register[dReg] = (address/4);
				pc++;
				break;
			}
			else{
				register[dReg] = address;
				pc++;
				break;
			}
			
		case "010000"://16
			System.out.println("Instruction: SLT  Type: R" );
			//Sets the D-reg to 1 if first S-reg is less than 
			//second S-reg, 0 otherwise
			System.out.println("s1Reg: " + register[s1Reg] +
					"<" + " s2Reg" + register[s2Reg] );
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
			System.out.println("Instruction: SLTI  Type: I" );
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
			System.out.println("Instruction: HLT  Type: J" );
			//Logical end of program
			System.out.println("****************Job" + processId+ ": " + register[0] + "*************");
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
			System.out.println("Did not read Opcode");
			
			break;
		}
		
	}
	
	
	
}
