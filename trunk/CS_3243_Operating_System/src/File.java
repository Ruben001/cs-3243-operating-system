public class File {
	private int address;
	private int length;
	
	public File(int address, int length) {
		this.address = address;
		this.length = length;
	}
	
	public int getAddress() { return address; }
	
	public int getFileLength() { return length; }
}
