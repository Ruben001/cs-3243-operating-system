import java.util.ArrayList;


public class PCB 
{
	private int processId;
	private int priority;
	private ProcessState state;
	private PCB parent;
	private ArrayList<PCB> children;
	
	public PCB() {
		children = new ArrayList<PCB>();
	}
	
	public PCB(PCB parent) {
		this.parent = parent;
	}
}
