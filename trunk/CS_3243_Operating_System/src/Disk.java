/**
 * Hard drive disk. Holds a fixed array of 2048 words of 32 bits each. 
 * @author wmasters
 */
public class Disk {

	/**
	 * Constant size of each word. 32 bits. 
	 */
	private final int WORD_SIZE = 32;
	
	/**
	 * Constant size of the disk. 2048 words. 
	 */
	private final int DISK_SIZE = 2048;
	
	/**
	 * Internal array to hold words. 
	 */
	private long[] data;
	
	/**
	 * Contracts a new disk, initializing the internal data array. 
	 */
	public Disk() {
		data = new long[DISK_SIZE];
	}
	
	/**
	 * Write a word of data to an addressed location of the Disk. 
	 * @param address the address of the disk where the datum will be written 
	 * @param word the datum to be written
	 */
	public void writeData(int address, long word) {
		try {
			data[address] = word;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
		}
	}
	
	/**
	 * Read a word from a given address in the disk. 
	 * @param address the address of the word to read
	 * @return the datum in the given address
	 */
	public long readData(int address) {
		try {
			return data[address];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}
	
	/**
	 * Write a bit-array word of binary data into an addressed location of the disk. 
	 * @param address the address to write the datum 
	 * @param word the word to write
	 */
	public void writeBinaryData(int address, int[] word) {
		long longWord = 0;
		
		for(int i = 1; i <= WORD_SIZE; i++) {
			if(word[i-1] == 1) {
				longWord = longWord + (i * 2);
			}
		}
		
		writeData(address, longWord);
	}
	
	/**
	 * Read a bit-array word from an addressed location of the disk. 
	 * @param address the location of the disk to read 
	 * @return a bit-array of the datum in the addressed location
	 */
	public int[] readBinaryData(int address) {
		String strAddr = Long.toBinaryString(readData(address));
		int[] value = new int[WORD_SIZE];
		
		for(int i = 0; i < WORD_SIZE; i++){
			value[i] = 0;
		}
		
		for(int i = 0; i < strAddr.length(); i++){
			//change char into int and add to array
			value[i] = Character.getNumericValue(strAddr.charAt(i));
		}
		
		return value;
	}
}