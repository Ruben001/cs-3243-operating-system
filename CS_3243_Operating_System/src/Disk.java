public class Disk {
	
	private long[] data;
	private final int WORD_SIZE = 32;
	
	public Disk() {
		data = new long[2048];
	}
	
	public void writeData(int address, long word) {
		try {
			data[address] = word;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
		}
	}
	
	public long readData(int address) {
		try {
			return data[address];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}
	
	public void writeBinaryData(int address, int[] word) {
		long longWord = 0;
		
		for(int i = 1; i <= WORD_SIZE; i++) {
			if(word[i-1] == 1) {
				longWord = longWord + (i * 2);
			}
		}
		
		writeData(address, longWord);
	}
	
	public int[] readBinaryData( int address ) {
		String strAddr = Long.toBinaryString(readData(address));
		int[] value = new int[WORD_SIZE];
		
		for(int i = 0; i < WORD_SIZE; i++){
			value[i] = 0;
		}
		
		for(int i = 0; i < strAddr.length(); i++){
			value[i] = strAddr.charAt(i);
		}
		
		return value;
	}
}