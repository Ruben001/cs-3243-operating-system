
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
		
		
		String opcodeString = "";
		String s1RegString = "";
		String s2RegString = "";
		String dRegString = "";
		String bRegString = "";
		String addressString = "";
		String reg1String = "";
		String reg2String = "";
		
		int count = 29;
		
		for(int i = 0; i < 6;i--){
			opcodeString = opcodeString + binaryArray[count];
			count--;
		}
		opcode = Integer.parseInt(opcodeString,2);
		//00
		if(binaryArray[30] == 0 && binaryArray[31] == 0){
			
			for(int i = 0; i < 4;i--){
				s1RegString = s1RegString + binaryArray[count];
				count--;
			}
			s1Reg = Integer.parseInt(s1RegString,2);
			for(int i = 0; i < 4;i--){
				s2RegString = s2RegString + binaryArray[count];
				count--;
			}
			s2Reg = Integer.parseInt(s2RegString,2);
			for(int i = 0; i < 4;i--){
				dRegString = dRegString + binaryArray[count];
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
		}
		//01
		else if(binaryArray[30] == 0 && binaryArray[31] == 1){
			
			for(int i = 0; i < 4;i--){
				bRegString = bRegString + binaryArray[count];
				count--;
			}
			bReg = Integer.parseInt(bRegString,2);
			for(int i = 0; i < 4;i--){
				dRegString = dRegString + binaryArray[count];
				count--;
			}
			dReg = Integer.parseInt(dRegString,2);
			for(int i = 0; i < 16;i--){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
		}
		//10
		else if(binaryArray[30] == 1 && binaryArray[31] == 0){
			
			for(int i = 0; i < 24;i--){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
			
		}
		//11
		else if(binaryArray[30] == 1 && binaryArray[31] == 1){
			
			for(int i = 0; i < 4;i--){
				reg1String = reg1String + binaryArray[count];
				count--;
			}
			reg1 = Integer.parseInt(reg1String,2);
			for(int i = 0; i < 4;i--){
				reg2String = reg2String + binaryArray[count];
				count--;
			}
			reg2 = Integer.parseInt(reg2String,2);
			for(int i = 0; i < 16;i--){
				addressString = addressString + binaryArray[count];
				count--;
			}
			address = Integer.parseInt(addressString,2);
			
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
