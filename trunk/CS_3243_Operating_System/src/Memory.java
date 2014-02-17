
/**
 * The Memory/Ram of the virtual computer.
 * @author wmasters
 */
public final class Memory {

	/**
	 * Constant size of each word. 32 bits. 
	 */
	private final int WORD_SIZE = 32;
	
	/**
	 * Constant size of the memory. 1024 words. 
	 */
	private final int DISK_SIZE = 1024;
	
	/**
	 * Internal array to hold words. 
	 */
	private long[] data;
	
	/**
	 * Contracts a new Memory, initializing the internal data array. 
	 */
	public Memory() {
		data = new long[DISK_SIZE];
	}
	
	/**
	 * Write a word of data to an addressed location of the Memory. 
	 * @param address the address of the memory where the datum will be written 
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
	 * Read a word from a given address in the memory. 
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
	 * Write a bit-array word of binary data into an addressed location of the memory. 
	 * @param address the address to write the datum 
	 * @param word the word to write
	 */
	public void writeBinaryData(int address, boolean[] word) {
		long longWord = 0;

		for (int i = 0; i < WORD_SIZE; ++i) {
			if(word[i]) {
				longWord = longWord + ((i + 1) * 2);
			}
		}
		
		writeData(address, longWord);
	}
	
	/**
	 * Read a bit-array word from an addressed location of the memory. 
	 * @param address the location of the memory to read 
	 * @return a bit-array of the datum in the addressed location
	 */
	public boolean[] readBinaryData(int address) {
		String binaryString = Long.toBinaryString(data[address]);
		for (int i = binaryString.length(); i < WORD_SIZE; ++i)
			binaryString = "0" + binaryString;
		
		boolean[] bitArray = new boolean[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i)
			bitArray[i] = (binaryString.charAt(i) == '1' ? true : false);
		
		return bitArray;
	}
}
