import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class OSDriver {
	
	private static CPU cpu;
	private static Disk disk;
	private static Memory memory;
	private static LongTermScheduler ltScheduler;
	private static ShortTermScheduler stScheduler;
	public static String content;
	private static String filename;
	// list of all PCB's remaining in the disk
	private static ArrayList<PCB> pcbList;
	
	// queues
	private static ArrayList<PCB> readyQueue;
	
	
	public static void setFileName(String name){
		
		filename = name;
		
	}
	
	
	
	public static void main(String[] args) {
		// initialize components
		disk = new Disk();
		memory = new Memory();
		cpu = new CPU(memory,stScheduler);
		pcbList = new ArrayList<PCB>();
		readyQueue = new ArrayList<PCB>();
		
		//filename = "DataFile2-Cleaned.txt";//if you don't want to use the GUI, uncomment this line

		ltScheduler = new LongTermScheduler(disk, memory, pcbList, readyQueue, SchedulingAlgorithm.FCFS);
		stScheduler = new ShortTermScheduler(cpu,ltScheduler,memory,pcbList,readyQueue,SchedulingAlgorithm.FCFS);
		// call the loader to load the job file into the Disk
		
		try{
		Loader loader = new Loader(filename, disk, pcbList);
		loader.load();
		
		}
		
		catch(Exception e){
			
		
			JOptionPane.showMessageDialog(null, "Sorry, the file was not found or a wrong path was entered", "File not found", JOptionPane.ERROR_MESSAGE);
			
		}
		
		
		
		
		ltScheduler.schedule();
		stScheduler.ScheduleAndDispatch();
		
		content = "";
		
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
