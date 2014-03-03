
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * OSDriver is the main class to run this program. 
 * It can also be run through Gui.java to run with graphical interface.
 * Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
 
 */


public class OSDriver implements Runnable{
	
	private static CPU cpu; // CPU field varaible
	private static Disk disk; // Disk field variable
	private static Memory memory; //Memory field variable
	private static LongTermScheduler ltScheduler; //LongTermScheduler field variable
	private static ShortTermScheduler stScheduler; // ShortTermScheduler field variable
	public static String content;  // this variable is used to return result to GUI interface
	private static String filename; // this variable gets the the filename from GUI interface to run
	// list of all PCB's remaining in the disk
	private static ArrayList<PCB> pcbList; // ArrayList of PCB class, it behaves as list of PCB
	
	// queues
	private static ArrayList<PCB> readyQueue; // ArrayList of readyQueue
	
	private static Thread longtermSchedulingThread; // Thread has not been implemented yet
	
	/**
	 * It is setter method to filename
	 * @param it takes in string value as a filename
	 */
	public static void setFileName(String name){
		
		filename = name;
		
	}
	
	
	/**
	 * Main method for the class OSDriver
	 * 
	 */
	public static void main(String[] args) {
		// initialize components
		disk = new Disk(); // initiated disk
		memory = new Memory(); // initiated memory
		cpu = new CPU(memory,stScheduler); // initiated cpu
		pcbList = new ArrayList<PCB>(); // initiated pcbList
		readyQueue = new ArrayList<PCB>(); // initiated readyQueue
		
		//filename = "DataFile2-Cleaned.txt";//if you don't want to use the GUI, uncomment this line

		ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.FCFS); // initiated ltScheduler
		stScheduler = new ShortTermScheduler(cpu,ltScheduler,memory,pcbList,readyQueue,SchedulingAlgorithm.FCFS); // initiated stScheduler
		// call the loader to load the job file into the Disk
		
		/**
		 * Initiated loader and loads it
		 */
		try{
		Loader loader = new Loader(filename, disk, pcbList);
		loader.load();
		
		}
		
		catch(Exception e){
			
		
			JOptionPane.showMessageDialog(null, "Sorry, the file was not found or a wrong path was entered", "File not found", JOptionPane.ERROR_MESSAGE);
			
		}
		
		
		
		
		ltScheduler.schedule(); // calls schedule method to process a FCFS, Priority, Shortest Job First
		stScheduler.ScheduleAndDispatch(); // it calls the short term scheduler and dispatch jobs
		
		content = "";
		/**
		 * This block of codes get the results from cpu and display on the GUI console
		 */
		try{
			
			File f = new File("results.txt");
			FileReader fr = new FileReader("results.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String lineRead = br.readLine();
			
			content = lineRead;
			
			while(lineRead!=null){
				
				lineRead = br.readLine();
				
				if(lineRead!=null){
				content = content + "\n" + lineRead;
				System.out.println(lineRead);
				}
			}
			
			br.close();
			f.delete();
			
		}
		catch(FileNotFoundException fnfe){
			
			System.out.println("File not found");
			
		}
		catch(IOException ioe){
			
			ioe.printStackTrace();
		}
		return;
	}


	/**
	 * This method is not implemented yet
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
