public class PcbFile {
	private int address;
	private int length;
	private PcbFileType type;
	
	public PcbFile(int address, int length, PcbFileType type) {
		this.address = address;
		this.length = length;
		this.type = type;
	}
	
	public int getAddress() { return address; }
	
	public int getFileLength() { return length; }
	
	public PcbFileType getType() { return type; }
}
