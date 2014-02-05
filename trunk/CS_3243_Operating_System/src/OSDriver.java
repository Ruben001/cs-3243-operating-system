
public class OSDriver {
	
	private static Disk disk;
	
	
	public static void main(String[] args) {
		disk = new Disk();
		Loader loader = new Loader(disk);
		loader.load();
		
		
		
	}

}
