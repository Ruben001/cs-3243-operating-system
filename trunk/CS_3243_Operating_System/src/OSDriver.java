import java.util.ArrayList;


public class OSDriver {
	
	private static Disk disk;
	private static ArrayList<PCB> pcbList;
	
	
	public static void main(String[] args) {
		disk = new Disk();
		pcbList = new ArrayList<PCB>();
		Loader loader = new Loader(disk, pcbList);
		loader.load();
		
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
