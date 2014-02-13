import java.util.ArrayList;

public class PCB {
	
	public int processId;
	public int priority;
	public int processSize;
	
	public ProcessState state;
	public int pc;
	
	public PCB parent;
	public ArrayList<PCB> children;
	
	// file information
	public int jobFileAddress;
	public int jobFileLength;
	public int inputBufferAddress;
	public int inputBufferLength;
	public int outputBufferAddress;
	public int outputBufferLength;
	public int tempBufferAddress;
	public int tempBufferLength;


	public PCB() {
		children = new ArrayList<PCB>();
	}
	
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
