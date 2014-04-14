import java.util.ArrayList;
public class MemoryManager {
	private Disk disk;
	private Memory ram;
	private int totalPageNumber, totalFrameNumber, pageNumber, frameNumber;
	private ArrayList<Integer> pages;
	private final int PAGE_SIZE = 16;
	
	public MemoryManager(){
		pages = new ArrayList<Integer>();
       
        disk = new Disk();
        ram = new Memory();
        
        totalPageNumber = 1024/PAGE_SIZE;
        totalFrameNumber = 2048/PAGE_SIZE;
	}
	
	public int getFrame(int initial, PCB pcb){
		
		return 0;
	}
	
	/****************************************************************************************
	* Reading and writing data to the disk and ram methods									*
	* 																						*
	*****************************************************************************************/
	public void writeDiskData(int address, long data){
		disk.writeData(address, data);
	}
	
	public void writeRamData(int address, long data){
		ram.writeData(address, data);
	}
	
	public void writeDiskDataBinary(int address, boolean[] data){
		disk.writeBinaryData(address, data);
	}
	
	public void writeRamDataBinary(int address, boolean[] data){
		ram.writeBinaryData(address, data);
	}
	
	public boolean[] readRamDataBinary(int address){
		return ram.readBinaryData(address);
	}
	
	public boolean[] readDiskDataBinary(int address){
		return disk.readBinaryData(address);
	}
	
	public long readRamData(int address){
		return ram.readData(address);
	}
	
	public long readDiskData(int address){
		return disk.readData(address);
	}
	/****************************************************************
	 * Finished with reading and writing to disk and ram methods	*
	 ****************************************************************/
}
