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
	
	class Sched{
		
		public int burstTime;
		public int priority;
		public int queueTime;
		public int timeSlice;
		public int remainTime;
		
		public Sched(){
			
		}
		
	class Registers{
			public int[][] register;
			
			public Registers(){
				this.register=new int[16][32];
			}
		}
	
	class Accounts{
		public int cpuTime;
		public int timeLimit;
		public int timeDelay;
		public int startTime;
		public int endTime;
		public int ioTime;
		
		public Accounts(){
			
		}
	}
	
	class Memories{
		
		public Memories(){
			
		}
		
	}
	
	class Resources{
		public int filePointer;
		public int ioDevice;
		public int unitNumber;
		public int openFileTables;
		
		public Resources(){
			
		}
	}
		
		
	}
	
}
