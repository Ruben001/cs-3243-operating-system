import java.util.ArrayList;

public class PCB {
	
	public int processId;
	public int priority;
	public int processSize;
	
	public int inputBuffer;
	public int outputBuffer;
	public int tempBuffer;
	
	public int address;
	
	public ProcessState state;
	public int pc;
	
	public PCB parent;
	public ArrayList<PCB> children;
	public ArrayList<PcbFile> files;

	public PCB() {
		children = new ArrayList<PCB>();
		files =new ArrayList<PcbFile>();
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
