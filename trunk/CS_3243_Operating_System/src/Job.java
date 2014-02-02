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
	private long cpuEndTime = 0;
	
	
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
	
	//Set methods
	public void setProcessId(int a)
	{
		processId = a;
	};
	public void setProcessSize(int a)
	{
		processSize = a;
	};
	public void setPriority(int a)
	{
		priority = a;
	};
	public void setStatus(int a)
	{
		status = a;
	};
	public void setAddress(int a)
	{
		address = a;
	};
	public void setInQueueTime(long a)
	{
		inQueueTime = a;
	};
	public void setOutQueueTime(long a)
	{
		outQueueTime = a;
	};
	public void setCpuStartTime(long a)
	{
		cpuStartTime = a;
	};
	public void setCpuEndTime(long a)
	{
		cpuEndTime = a;
	};
	
	
	//Get methods
	public int getProcessId()
	{
		return processId;
	};
	public int getProcessSize()
	{
		return processSize;
	};
	public int getPriority()
	{
		return priority;
	};
	public int getStatus()
	{
		return status;
	};
	public int getAddress()
	{
		return address;
	};
	public long getInQueueTime()
	{
		return inQueueTime;
	};
	public long getOutQueueTime()
	{
		return outQueueTime;
	};
	public long getCpuStartTime()
	{
		return cpuStartTime;
	};
	public long getCpuEndTIme()
	{
		return cpuEndTime;
	};
	
	
	
	
	public Job() {
		children = new ArrayList<Job>();
	}
	
	public Job(Job parent) {
		this.parent = parent;
	}
	
	
}
