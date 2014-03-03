
/**
 * The Memory/Ram of the virtual computer.
 *Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
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
	 * Boolean array to keep track of the segments of data that are in use. 
	 */
	private boolean[] useIndex;
	
	/**
	 * Contracts a new Memory, initializing the internal data array. 
	 */
	public Memory() {
		data = new long[DISK_SIZE];
		useIndex = new boolean[DISK_SIZE];
	}
	
	/**
	 * Write a word of data to an addressed location of the Memory. 
	 * @param address the address of the memory where the datum will be written 
	 * @param word the datum to be written
	 */
	public void writeData(int address, long word) {
		try {
			data[address] = word;
			useIndex[address] = true;
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
			if(word[i]==true) {
				longWord = (long) (longWord + Math.pow(2, i));
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
			bitArray[i] = (binaryString.charAt(31-i) == '1' ? true : false);
		
		return bitArray;
	}
	
	/**
	 * Release a location in memory.
	 * @param address the address of memory to free
	 */
	public void free(int address,int length) {
		try {
			for(int i = 0 ; i <length;i++){
				useIndex[address] = false;
				data[address] = 0;
				address++;
			}
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * Returns the largest piece of free memory in the following format: { address, size }.
	 * @return largest piece of free memory
	 */
	public int[] GetLargestMemoryChunk() {
		int longestAddress = 0, currentAddress = 0, longestLength = 0, currentLength = 0;
		for (int i = 0; i < DISK_SIZE; ++i) {
			if (!useIndex[i]) {
				currentAddress = i;
				currentLength = 1;
				for (int j = 1; (j + i) < DISK_SIZE; ++j) {
					if (!useIndex[i + j]) {
						++currentLength;
					}
				}
				if (currentLength > longestLength) {
					longestAddress = currentAddress;
					longestLength = currentLength;
				}
			}
		}
		return new int[]{ longestAddress, longestLength };
	}
}
