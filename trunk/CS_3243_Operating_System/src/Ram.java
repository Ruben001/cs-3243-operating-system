/**
 * This class behaves as a Ram of 1024 words
 * It consist of two dimension array of type int
 * @author Mohil Patel
 * 
 */
public class Ram {
	private int[][] ram; // array of int which will behave as a ram.
	/**
	 * Constructor for the Class Ram
	 * It initialize ram as two dimension array of int[1024][32]
	 */
	public Ram() {
		this.ram=new int[1024][32];
	}
	public int[][] getRam(){
		return ram;
	}
	/**
	 * This method can be use to retrieve a particular bit from the ram.
	 * @param line is a position of row in the ram. It should range from 0 to 1023.
	 * @param bitPostion is a position of column in the ram. It should range from 0 to 31.
	 * @return it returns the particular bit from the ram of give line and bit position.
	 */
	public int readRamBit(int line, int bitPosition){
		return ram[line][bitPosition];
		
	}
	/**
	 * This method gives an array of a row in the ram.
	 * @param line is position of row in the ram to retrieve the date.
	 * @return it returns an array of int from the ram.
	 */
	public int[] readRamLine(int line){
		return ram[line];
		
	}
	/**
	 * This method clear whole ram. Be careful when using this method.
	 */
	public void clearRam(){
		ram = new int[1024][31];
	}
	
	/**
	 * This method can add data to the ram if provide line and bit position to the ram. 
	 * @param line is a position of row in the ram. It should range from 0 to 1023.
	 * @param bitPosition is a position of column in the ram. It should range from 0 to 31.
	 * @param data is used to add a data to the ram. It should range from 0 to 1.
	 */
	public void writeBitToRam(int line, int bitPosition, int data){
		if(line<=1023 && line>=0){
			if (bitPosition<=31 && bitPosition>=0){
				if(data==0 || data ==1){
					ram[line][bitPosition] = data;
				}
				else {
					throw new IllegalArgumentException("Invalid data. It should be 0 or 1"); 
				}
			} else {
				throw new IndexOutOfBoundsException("Bit posistion is out of bound. It should be 0 to 31");
			}
		} else{
			throw new IndexOutOfBoundsException("Line posistion is out of bound. It should be 0 to 1023");
		}
		
	}
	/**
	 * This method writes the a line in the ram
	 * @param line is a position of row in the ram. It should range from 0 to 1023. 
	 * @param data is an array of int[] it should be size of 32
	 */
	public void writeLineToRam(int line, int[] data){
		if(line<=1023 && line>=0){
			
			ram[line] = data;
		} else{
			throw new IndexOutOfBoundsException("Line posistion is out of bound. It should be 0 to 1023");
		}
	}
}
