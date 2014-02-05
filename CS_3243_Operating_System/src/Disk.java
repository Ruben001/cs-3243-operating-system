public class Disk {
	
	private long[] data;
	
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
}