import java.util.ArrayList;

public class PCB {
	
	
	
	//variables
	private int processId;
	private int priority;
	private int processSize;
	
	private int inputBuffer;
	private int outputBuffer;
	private int tempBuffer;
	
	private int address;
	
	private ProcessState state;
	private int pc;
	
	public PCB parent;
	public ArrayList<PCB> children;
	public ArrayList<PcbFile> files;
	
	/**
	 * This is the Constructor
	*/
	public PCB(int ProcessId, int ProcessSize, int Priority, int Address
			,int InputBuffer, int OutputBuffer, int TempBuffer) {
		this.processId = ProcessId;
		this.processSize = ProcessSize;
		this.priority = Priority;
		this.address = Address;
		this.inputBuffer = InputBuffer;
		this.outputBuffer = OutputBuffer;
		this.tempBuffer = TempBuffer;
		
		
		
	}
	
	
	public int getProcessId(){
		return processId;
	}
	public int getProcessSize()
	{
		return processSize;
	};
	public int getPriority()
	{
		return priority;
	};
	public int getPc()
	{
		return pc;
	};
	public int getAddress()
	{
		return address;
	};
	
}
