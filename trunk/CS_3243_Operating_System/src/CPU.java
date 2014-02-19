
public class CPU {
	static int pc; // this variable is a program counter 
	static int[][] register;
	static int baseRegister;
	static int limitRegister;
	
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
	
	
	public CPU(){
		pc=0;
		register=new int[16][32];
		

	}
	private void begin(){
		
	}
	private void fetch(int lineRam){
		
	}
	public void decode(long[] binaryArray){
		
		
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
			opcodeString = opcodeString + binaryArray[count];
			count--;
		}
		opcode = Integer.parseInt(opcodeString,2);
		System.out.println("Opcode: " + opcode);
		//00
		if(binaryArray[31] == 0 && binaryArray[30] == 0){
			System.out.println("Arithmetic instruction format: " );
			for(int i = 0; i < 4;i++){
				s1RegString = s1RegString + binaryArray[count];
				count--;
			}
			s1Reg = Integer.parseInt(s1RegString,2);
			System.out.println("S1-Reg: " + s1Reg);
			for(int i = 0; i < 4;i++){
				s2RegString = s2RegString + binaryArray[count];
				count--;
			}
			s2Reg = Integer.parseInt(s2RegString,2);
			System.out.println("S2-Reg: " + s2Reg);
			for(int i = 0; i < 4;i++){
				dRegString = dRegString + binaryArray[count];
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
			System.out.println("D-Reg: " + dReg);
		}
		//01
		else if(binaryArray[31] == 0 && binaryArray[30] == 1){
			System.out.println("Conditional Branch and Immediate format: " );
			for(int i = 0; i < 4;i++){
				bRegString = bRegString + binaryArray[count];
				count--;
			}
			bReg = Integer.parseInt(bRegString,2);
			System.out.println("B-Reg: " + bReg);
			for(int i = 0; i < 4;i++){
				dRegString = dRegString + binaryArray[count];
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
			System.out.println("D-Reg: " + dReg);
			for(int i = 0; i < 16;i++){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
		}
		//10
		else if(binaryArray[31] == 1 && binaryArray[30] == 0){
			System.out.println("Unconditional Jump format: " );
			for(int i = 0; i < 24;i++){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
			
		}
		//11
		else if(binaryArray[31] == 1 && binaryArray[30] == 1){
			System.out.println("Input and Output instruction format: " );
			for(int i = 0; i < 4;i++){
				reg1String = reg1String + binaryArray[count];
				count--;
			}
			reg1 = Integer.parseInt(reg1String,2);
			System.out.println("Reg1: " + reg1);
			for(int i = 0; i < 4;i++){
				reg2String = reg2String + binaryArray[count];
				count--;
			}
			reg2 = Integer.parseInt(reg2String,2);
			System.out.println("Reg2: " + reg2);
			for(int i = 0; i < 16;i++){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
			System.out.println("address: " + address);
			
		}
		
		execute(opcodeString);
		
	}
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