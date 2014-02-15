import java.util.ArrayList;


public class OSDriver {
	
	private static CPU cpu;
	private static Disk disk;
	private static ArrayList<PCB> pcbList;
	
	
	public static void main(String[] args) {
		cpu = new CPU();
		disk = new Disk();
		pcbList = new ArrayList<PCB>();
		Loader loader = new Loader(disk, pcbList);
		loader.load();
		
		
		//test
		int[] ar = disk.readBinaryData(11);
		int[] new1 = new int[32];
		int newCount = 31;
		for(int i = 0; i<32;i++){
			new1[newCount] = ar[i];
			newCount--;
		}
		
		cpu.decode(new1);
		//Test
		/*
		int[] ar = disk.readBinaryData(0);
		for(int i = 0; i < ar.length;i++ )
		{
		System.out.print(ar[i]);
		}
		*/
		return;
	}

}
