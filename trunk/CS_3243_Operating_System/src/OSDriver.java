import java.util.ArrayList;


public class OSDriver {
	
	private static CPU cpu;
	private static Disk disk;
	private static Memory memory;
	private static LongTermScheduler ltScheduler;

	// list of all PCB's remaining in the disk
	private static ArrayList<PCB> pcbList;
	
	// queues
	private static ArrayList<PCB> readyQueue;
	
	public static void main(String[] args) {
		// initialize components
		cpu = new CPU();
		disk = new Disk();
		memory = new Memory();
		pcbList = new ArrayList<PCB>();
		readyQueue = new ArrayList<PCB>();

		ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.PRIORITY);
		
		// call the loader to load the job file into the Disk
		Loader loader = new Loader("DataFile2-Cleaned.txt", disk, pcbList);
		loader.load();
		
		
		
		ltScheduler.schedule();
		

		
		
		//test
		//int[] ar = disk.readBinaryData(11);
		/*
		long test2 = 275087360;
		
		long[] ar = new long[]{0,0,0,1,0,0,0,0,0,1,1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		long[] new1 = new long[32];
		int newCount = 31;
		for(int i = 0; i<32;i++){
			new1[newCount] = ar[i];
			newCount--;
		}
		cpu.decode(new1);
		*/
		
		//TEST
		/*
		cpu.decode(disk.readBinaryData(0));
		cpu.decode(disk.readBinaryData(1));
		cpu.decode(disk.readBinaryData(2));
		cpu.decode(disk.readBinaryData(3));
		cpu.decode(disk.readBinaryData(4));
		cpu.decode(disk.readBinaryData(5));
		cpu.decode(disk.readBinaryData(6));
		cpu.decode(disk.readBinaryData(7));
		cpu.decode(disk.readBinaryData(8));
		cpu.decode(disk.readBinaryData(9));
		cpu.decode(disk.readBinaryData(10));
		cpu.decode(disk.readBinaryData(11));
		cpu.decode(disk.readBinaryData(12));
		cpu.decode(disk.readBinaryData(13));
		cpu.decode(disk.readBinaryData(14));
		cpu.decode(disk.readBinaryData(15));
		cpu.decode(disk.readBinaryData(16));
		cpu.decode(disk.readBinaryData(17));
		cpu.decode(disk.readBinaryData(18));
		cpu.decode(disk.readBinaryData(19));
		cpu.decode(disk.readBinaryData(20));
		cpu.decode(disk.readBinaryData(21));
		*/
		
		//TEST
		cpu.begin();
		
		//Test
		/*
		String ch = "c050";
		int conv_int =  Integer.parseInt(ch, 16);
		String bin_char = Integer.toBinaryString(conv_int);
		long bi = Long.parseLong(bin_char);
		System.out.println("HEX TEST: " + bi);
		*/

		//Test
		/*
		boolean[] tt = disk.readBinaryData(0);
		for(int i = 0; i < ar.length;i++ )
		{
		System.out.print(tt[i]);
		}
		*/

		return;
	}

}
