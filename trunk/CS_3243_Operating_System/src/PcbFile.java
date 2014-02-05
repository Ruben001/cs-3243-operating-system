public class PcbFile {
	private int address;
	private int length;
	
	public PcbFile(int address, int length) {
		this.address = address;
		this.length = length;
	}
	
	public int getAddress() { return address; }
	
	public int getFileLength() { return length; }
}
