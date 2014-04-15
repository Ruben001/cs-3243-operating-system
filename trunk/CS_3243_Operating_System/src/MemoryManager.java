import java.util.ArrayList;
public class MemoryManager {
	
	private static Disk disk;
	private static Memory ram;
	private static int totalPageNumber, totalFrameNumber, pageNumber, frameNumber;
	private ArrayList<JobFrame> frames;
	private final int PAGE_SIZE = 16;
	private static PageTable[] pageTable;
	
	public MemoryManager(){
		frames = new ArrayList<JobFrame>();
		
       
        disk = new Disk();
        ram = new Memory();
        
        totalPageNumber = 1024/PAGE_SIZE;
        totalFrameNumber = 2048/PAGE_SIZE;
        
        pageTable = new PageTable[totalPageNumber];
        initializePageTable();
        
	}
	
	/*
	 * This method initialize page table for the first time
	 */
	private void initializePageTable(){
		for(int i =0;i<totalPageNumber;i++){
			pageTable[i]= new PageTable();
		}
	}
	
	public int getFrame(int initial, PCB pcb){
		
		return 0;
	}
	
	public int getPhysicalAddress(int pageNumber, int offset){
		int frameNumber, physicalAddress;
		if(isValidFrame(pageNumber) && offset<PAGE_SIZE){
			frameNumber= pageTable[pageNumber].getFrameNumber();
			physicalAddress= frameNumber*PAGE_SIZE+offset;
			return physicalAddress;
		}
		
		return -1;
	}
	public boolean isValidFrame(int pageNumber){
		return pageTable[pageNumber].isValid();
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
