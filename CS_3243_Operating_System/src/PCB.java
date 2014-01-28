import java.util.ArrayList;


public class PCB 
{
	private int processId;
	private int processSize;
	private int priority;
	private int status;
	private int address;
	
	private long inQueueTime = 0;
	private long outQueueTime = 0;
	private long cpuStartTime = 0;
	private long cpuEndTIme = 0;
	
	
	private ProcessState state;
	private PCB parent;
	private ArrayList<PCB> children;
	
	PCB(int a, int b, int c, int d)
	{
		this.processId = a;
		this.processSize = b;
		this.priority = c;
		this.address = d;
	
	}
	
	public PCB() {
		children = new ArrayList<PCB>();
	}
	
	public PCB(PCB parent) {
		this.parent = parent;
	}
}
