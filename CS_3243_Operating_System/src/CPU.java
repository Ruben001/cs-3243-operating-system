import java.util.ArrayList;


public class CPU {
	private static Memory memory;
	private ArrayList<PCB> pcbList;
	private static ShortTermScheduler stScheduler;
	
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

	}
	public void begin(){
		
		
		System.out.println("\n\n\n\n\nLoading Process number: " + processId  );
		System.out.println("Loading Process length: " + processLength  );
		
		for(int i = pc; i < processLength;i++){
			fetch(processAddress + pc);
			pc = ++i;
		}
		
		
		
	}
	private void fetch(int lineRam){
		
		decode(memory.readBinaryData(lineRam));
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
			}
			else{
				opcodeString = opcodeString + "0";
			}
			 
			count--;
		}
		opcode = Integer.parseInt(opcodeString,2);
		System.out.println("Opcode: " + opcode);
		//00
		if(binaryArray[31] == false && binaryArray[30] == false){
			System.out.println("Arithmetic instruction format: " );
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					s1RegString = s1RegString + "1";
				}
				else{
					s1RegString = s1RegString + "0";
				}
				 
				count--;
			}
			s1Reg = Integer.parseInt(s1RegString,2);
			System.out.println("S1-Reg: " + s1Reg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					s2RegString = s2RegString + "1";
				}
				else{
					s2RegString = s2RegString + "0";
				}
				count--;
			}
			s2Reg = Integer.parseInt(s2RegString,2);
			System.out.println("S2-Reg: " + s2Reg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					dRegString = dRegString + "1";
				}
				else{
					dRegString = dRegString + "0";
				}
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
			System.out.println("D-Reg: " + dReg);
		}
		//01
		else if(binaryArray[31] == false && binaryArray[30] == true){
			System.out.println("Conditional Branch and Immediate format: " );
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					bRegString = bRegString + "1";
				}
				else{
					bRegString = bRegString + "0";
				}
				count--;
			}
			bReg = Integer.parseInt(bRegString,2);
			System.out.println("B-Reg: " + bReg);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					dRegString = dRegString + "1" ;
				}
				else{
					dRegString = dRegString + "0" ;
				}
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
			System.out.println("D-Reg: " + dReg);
			for(int i = 0; i < 16;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1";
				}
				else{
					addressString = addressString + "0";
				}
				
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
		}
		//10
		else if(binaryArray[31] == true && binaryArray[30] == false){
			System.out.println("Unconditional Jump format: " );
			for(int i = 0; i < 24;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1";
				}
				else{
					addressString = addressString + "0";
				}
				
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
			
		}
		//11
		else if(binaryArray[31] == true && binaryArray[30] == true){
			System.out.println("Input and Output instruction format: " );
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					reg1String = reg1String + "1";
				}
				else{
					reg1String = reg1String + "0";
				}
				
				count--;
			}
			reg1 = Integer.parseInt(reg1String,2);
			System.out.println("Reg1: " + reg1);
			for(int i = 0; i < 4;i++){
				if(binaryArray[count] == true){
					reg2String = reg2String + "1";
				}
				else{
					reg2String = reg2String + "0";
				}
				
				count--;
			}
			reg2 = Integer.parseInt(reg2String,2);
			System.out.println("Reg2: " + reg2);
			for(int i = 0; i < 16;i++){
				if(binaryArray[count] == true){
					addressString = addressString + "1" ;
				}
				else{
					addressString = addressString + "0";
				}
				
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
			
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
		case "000000":
			System.out.println("Instruction: RD  Type: I/O" );
			
			break;
		case "000001":
			System.out.println("Instruction: WR  Type: I/O" );
			
			break;
			
		case "000010":
			System.out.println("Instruction: ST  Type: I" );
			
			break;
		case "000011":
			System.out.println("Instruction: LW  Type: I" );
			break;
		case "000100":
			System.out.println("Instruction: MOV  Type: R" );
			break;
		case "000101":
			System.out.println("Instruction: ADD  Type: R" );
			break;
			
		case "000110":
			System.out.println("Instruction: SUB  Type: R" );
			break;
		case "000111":
			System.out.println("Instruction: MUL  Type: R" );
			break;
		case "001000":
			System.out.println("Instruction: DIV  Type: R" );
			break;
		case "001001":
			System.out.println("Instruction: AND  Type: R" );
			break;
			
		case "001010":
			System.out.println("Instruction: OR  Type: R" );
			break;
		case "001011":
			System.out.println("Instruction: MOVI  Type: I" );
			break;
		case "001100":
			System.out.println("Instruction: ADDI  Type: I" );
			break;
		case "001101":
			System.out.println("Instruction: MULI  Type: I" );
			break;
			
		case "001110":
			System.out.println("Instruction: DIVI  Type: I" );
			break;
		case "001111":
			System.out.println("Instruction: LDI  Type: I" );
			break;
		case "010000":
			System.out.println("Instruction: SLT  Type: R" );
			break;
		case "010001":
			System.out.println("Instruction: SLTI  Type: I" );
			break;
			
		case "010010":
			System.out.println("Instruction: HLT  Type: J" );
			break;
		case "010011":
			System.out.println("Instruction: NOP  Type: -" );
			break;
		case "010100":
			System.out.println("Instruction: JMP  Type: J" );
			break;
		case "010101":
			System.out.println("Instruction: BEQ  Type: I" );
			break;
			
		case "010110":
			System.out.println("Instruction: BNE  Type: I" );
			break;
		case "010111":
			System.out.println("Instruction: BEZ  Type: I" );
			break;
		case "011000":
			System.out.println("Instruction: BNZ  Type: I" );
			break;
		case "011001":
			System.out.println("Instruction: BGZ  Type: I" );
			break;
		case "011010":
			System.out.println("Instruction: BLZ  Type: I" );
			break;
		default:
			System.out.println("Did not read Opcode");
			break;
		}
		
	}
	
	public void Arithmetic(int i){
		
		/*
		0 = add
		1 = sub
		2 = mul
		3 = div
		*/
		if(i == 0){
			dReg = s1Reg + s2Reg;
		}
		else if(i == 1){
			dReg = s1Reg - s2Reg;
		}
		else if(i == 2){
			dReg = s1Reg * s2Reg;
		}
		else if(i == 3){
			dReg = s1Reg / s2Reg;
		}
	}
	
}
