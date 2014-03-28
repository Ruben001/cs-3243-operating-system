
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


public class OSDriver{
	
	/*
	 * These two classes are new to Phase1 part-2
	 * 
	 */
	public static Dispatcher dispatcher;
	public static AverageCalculator averageCalculator;
	
	public static Runnable cpu;
	//private static CPU cpu; // CPU field varaible
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
		
		//Does Context switching called by CPU and StScheduler
		dispatcher = new Dispatcher();
		//Calculates the averages between the CPUs
		averageCalculator = new AverageCalculator();
		
		
		// initialize components
		disk = new Disk(); // initiated disk
		memory = new Memory(); // initiated memory
		
		
		//cpu = new CPU(memory,stScheduler); // initiated cpu
		pcbList = new ArrayList<PCB>(); // initiated pcbList
		readyQueue = new ArrayList<PCB>(); // initiated readyQueue
		
		//filename = "DataFile2-Cleaned.txt";//if you don't want to use the GUI, uncomment this line

		/*
		 * Phase 1 part-2 
		 * Creates a new CPU thread using the Runnable interface
		 */
		
		Runnable cpu1 = new CPU(1,memory,dispatcher,averageCalculator);
		Runnable cpu2 = new CPU(2,memory,dispatcher,averageCalculator);
		Runnable cpu3 = new CPU(3,memory,dispatcher,averageCalculator);
		Runnable cpu4 = new CPU(4,memory,dispatcher,averageCalculator);
		
		new Thread(cpu1).start();
		new Thread(cpu2).start();
		new Thread(cpu3).start();
		new Thread(cpu4).start();
		
		
		
		//ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.FCFS); // initiated ltScheduler

		ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.FCFS); // initiated ltScheduler
		
		longtermSchedulingThread = new Thread(ltScheduler);		
		longtermSchedulingThread.start(); 
		
		stScheduler = new ShortTermScheduler(dispatcher,ltScheduler,memory,pcbList,readyQueue,SchedulingAlgorithm.FCFS, averageCalculator); // initiated stScheduler
		// call the loader to load the job file into the Disk
		
		//filename = "DataFile2-Cleaned.txt";
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


	
	
}
