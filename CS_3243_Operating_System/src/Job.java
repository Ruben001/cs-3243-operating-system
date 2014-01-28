import java.util.ArrayList;


public class Job 
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
	private Job parent;
	private ArrayList<Job> children;
	
	Job(int a, int b, int c, int d)
	{
		this.processId = a;
		this.processSize = b;
		this.priority = c;
		this.address = d;
	
	}
	
	public Job() {
		children = new ArrayList<Job>();
	}
	
	public Job(Job parent) {
		this.parent = parent;
	}
	
	
}
