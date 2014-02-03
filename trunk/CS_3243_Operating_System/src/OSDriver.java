
public class OSDriver {

	public static PCB pcb;
	
	public static void main(String[] args) {
		Loader loader = new Loader();
		loader.load();
		
		//Process Control Block
		pcb = new PCB();
		
	}

}
