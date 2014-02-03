public class Disk {
	
	private int[] data;
	
	public Disk() {
		data = new int[2048];
	}
	
	private void writeData(int address, int word) {
		try {
			data[address] = word;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
		}
	}
	
	private int readData(int address) {
		try {
			return data[address];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}
}