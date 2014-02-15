
public class CPU {
	static int pc; // this variable is a program counter 
	static int[][] register;
	static int baseRegister;
	static int limitRegister;
	
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
	private void decode(int[] binaryArray){
		
		
		if(binaryArray[0] == 0 && binaryArray[1] == 0){
			
		}
		else if(binaryArray[0] == 0 && binaryArray[1] == 1){
			
		}
		else if(binaryArray[0] == 1 && binaryArray[1] == 0){
			
		}
		else if(binaryArray[0] == 1 && binaryArray[1] == 1){
			
		}
			
		
	}
	private void execute(){
		
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
