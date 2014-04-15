import java.util.ArrayList;
public class MemoryManager {
	
	private static Disk disk;
	private static Memory ram;
	private static int totalPageNumber, totalFrameNumber;
	
	private final int PAGE_SIZE = 16;
	
	private ArrayList<Integer> freePageList, freeFrameList;
	
	
	public MemoryManager(){
		
		freeFrameList = new ArrayList<Integer>();
       
        disk = new Disk();
        ram = new Memory();
        
        totalPageNumber = 2048/PAGE_SIZE;
        totalFrameNumber = 1024/PAGE_SIZE;
        
        
        freePageList = new ArrayList<Integer>();
        
        
        initializeFreePageList();
        initializeFreeFrameList();
        
	}
	
	/**
	 * This method initialize freeFrameList
	 */
	private void initializeFreeFrameList() {
		for(int i =0; i<totalFrameNumber;i++){
			freeFrameList.add(i);
		}
		
	}

	/*
	 * This method initialize page table for the first time
	 */
	
	/**
	 * This method initialize freePageList
	 */
	private void initializeFreePageList(){
		for(int i=0;i<totalPageNumber;i++){
			freePageList.add(i);
		}
	}
	
	public int getFreeFrame(PCB pcb){
		return 0;
		
	}
	
	public int getPhysicalAddress(int offset, PCB pcb){
		int physicalAddress;
		int index = offset/getPAGE_SIZE();
		int remainder = offset%getPAGE_SIZE();
		if(pcb.pageTable[index].isValid()==true){
			physicalAddress = pcb.pageTable[index].getFrameNumber()*getPAGE_SIZE()+ remainder;
		} else {
			pcb.setPageFault(pcb.getPageFault()+1);
			pageFault(index,pcb);
			physicalAddress = -1;
		}
		return physicalAddress;
				
	}
	
	public boolean[] fetchData(int offset, PCB pcb){
		int physicalAddress = getPhysicalAddress(offset,pcb);
		return readRamDataBinary(physicalAddress);
	}
	
	private void pageFault(int index, PCB pcb) {
		int frame = getFreeFrameNumber();
		int page = getFreePageNumber();
		pcb.pageTable[index].setFrameNumber(frame);
		pcb.pageTable[index].setValid(true);
		pcb.pageTable[index].setJobID(pcb.processId);
		
		int remainder = pcb.jobFileLength%getPAGE_SIZE();
		int stop;
		int diskAddress = pcb.diskFileAddress+index*getPAGE_SIZE();
		if(remainder>0){
			stop = diskAddress+remainder;
		} else {
			stop = diskAddress+getPAGE_SIZE();
		}
		
		int memoryAddress = frame * getPAGE_SIZE();
		for(int i =diskAddress;i<stop;i++){
			ram.writeData(memoryAddress, disk.readData(i));
			memoryAddress++;
		}
		
		
	}

	/**
	 * This methods gives the first free page number
	 * returns -1 if pages are not free
	 */
	public int getFreePageNumber(){
		if(freePageList.size()>0){
			return freePageList.remove(0);
		}
		return -1;
	}
	
	public int getPAGE_SIZE() {
		return PAGE_SIZE;
	}

	/**
	 * This methods gives the first free frame number
	 * returns -1 if frames are not free
	 */
	public int getFreeFrameNumber(){
		if(freeFrameList.size()>0){
			return freeFrameList.remove(0);
		}
		return -1;
	}
	
	/**
	 * This methods free page from page table, and also free memory of ram
	 * @param pageNumber that would be free
	 */
	public void freePage(int pageNumber){
		int address = pageNumber*PAGE_SIZE;
		int length = PAGE_SIZE;
		
		
		
		ram.free(address, length);
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
