import java.util.ArrayList;

public class PCB {
	
	//class variable
	//keeps up with the number of PCBs that have been created
	private static int numberOfPCB = 0;
	
	//instance variables
	private int processId;
	private int priority;
	private int address;
	private int processSize;
	
	
	public PCB parent;
	public ArrayList<PCB> children;
	public ArrayList<File> files;
	
	/**
	 * This is the Constructor
	*/
	public PCB(int ProcessSize, int Priority, int Address) {
		this.processSize = ProcessSize;
		this.priority = Priority;
		this.address = Address;
		//increments numberOfPCB and added the value to processId
		this.processId = ++numberOfPCB;
	}
	
	//instance Method
	public int getProcessId(){
		return processId;
	}
	
	//class Method
	public static int getNumberOfPCB(){
		return numberOfPCB;
	}
	
	
}
