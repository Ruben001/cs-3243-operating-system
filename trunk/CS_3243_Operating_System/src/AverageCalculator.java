import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Each CPU adds its process's times and uses to Arrays in
 * the AverageCalculator. Once all jobs are completed the short term scheduler 
 * calls the method to calculate the averages.
 * 
 * 
 *Ruben Munive
 *
 */

public class AverageCalculator {
	
	//Holds Times
	private ArrayList<Long> completionTimeList = new ArrayList<Long>();
	private ArrayList<Long> ramUsageList = new ArrayList<Long>();
	private ArrayList<Long> cacheUsageList = new ArrayList<Long>();
	private ArrayList<Long> waitTimeList = new ArrayList<Long>();
	private ArrayList<Long> numberIOList = new ArrayList<Long>();
	
	DecimalFormat form = new DecimalFormat("#.00");
	
	//Constructor
	public AverageCalculator(){
		
	}
	
	
	public synchronized void addToCompletionTimeList(Long completionTime){
		completionTimeList.add(completionTime);
	}
	
	public synchronized void addToWaitTimeList(Long waitTime){
		waitTimeList.add(waitTime);
	}
	
	public synchronized void addToNumberIOList(Long numberIO){
		numberIOList.add(numberIO);
	}
	
	public synchronized void addToRamUsageListt(Long ramUsage){
		ramUsageList.add(ramUsage);
	}
	
	public synchronized void addToCacheUsageList(Long cacheUsage){
		cacheUsageList.add(cacheUsage);
	}
	
	
	//Calculates the average completion time
		public void averagecompletionTime(){
			double averagecompletionTime = 0;
			double numberOfProcesses = completionTimeList.size();
			double answer = 0;
			while(0 < completionTimeList.size()){
				averagecompletionTime += completionTimeList.remove(0);
			}
			answer = Double.valueOf(form.format(averagecompletionTime/numberOfProcesses));
			
			try{
				
				FileOutputStream fos = new FileOutputStream("results.txt",true);
				PrintWriter pw = new PrintWriter( fos );
				
				pw.print("\nAverage completion time: ");
				pw.print(answer);
				pw.println();
				pw.close();
			}
			catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
				
				
			}
			//System.out.println("\nAverage completion time: " + answer);
		}
		//Calculates the average wait time
		public void averageWaitTime(){
			
			double averageWaitTime = 0;
			double numberOfProcesses = waitTimeList.size();
			double answer = 0;
			while(0 < waitTimeList.size()){
				averageWaitTime += waitTimeList.remove(0);
			}
			answer = Double.valueOf(form.format(averageWaitTime/numberOfProcesses));
			try{
				
				FileOutputStream fos = new FileOutputStream("results.txt",true);
				PrintWriter pw = new PrintWriter( fos );
				
				pw.print("\nAverage wait time: ");
				pw.print(answer);
				pw.println();
				pw.close();
			}
			catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
				
				
			}
			//System.out.println("Average wait time: " + answer);

		}
		//Calculates the average number of IO requests
		public void averageNumberOfIORequests(){
			double averageNumberOfIO = 0;
			double numberOfIO = numberIOList.size();
			double answer = 0;
			while(0 < numberIOList.size()){
				averageNumberOfIO += numberIOList.remove(0);
			}	
			answer = Double.valueOf(form.format(averageNumberOfIO/numberOfIO));
			try{
				
				FileOutputStream fos = new FileOutputStream("results.txt",true);
				PrintWriter pw = new PrintWriter( fos );
				
				pw.print("\nAverage IO requests: ");
				pw.print(answer);
				pw.println();
				pw.close();
			}
			catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
				
				
			}
			//System.out.println("Average IO requests: " + answer);

			}
		
		//Calculates the average Ram Usage time
			public void averageRamUsageTime(){
				double averageRamUsageTime = 0;
				double numberOfProcesses = ramUsageList.size();
				double answer = 0;
				while(0 < ramUsageList.size()){
					averageRamUsageTime += ramUsageList.remove(0);
				}
				answer = Double.valueOf(form.format(averageRamUsageTime/numberOfProcesses));
				try{
					
					FileOutputStream fos = new FileOutputStream("results.txt",true);
					PrintWriter pw = new PrintWriter( fos );
					
					pw.print("\nAverage Ram usage time: ");
					pw.print(answer);
					pw.println();
					pw.close();
				}
				catch(FileNotFoundException fnfe){
					
					fnfe.printStackTrace();
					
					
				}
				//System.out.println("\nAverage completion time: " + answer);
			}
		
			//Calculates the average Cache Usage time
				public void averageCacheUsageTime(){
					double averageCacheUsageTime = 0;
					double numberOfProcesses = cacheUsageList.size();
					double answer = 0;
					while(0 < cacheUsageList.size()){
						averageCacheUsageTime += cacheUsageList.remove(0);
					}
					
					answer = Double.valueOf(form.format(averageCacheUsageTime/numberOfProcesses));
					try{
						
						FileOutputStream fos = new FileOutputStream("results.txt",true);
						PrintWriter pw = new PrintWriter( fos );
						
						pw.print("\nAverage Cache usage time: ");
						pw.print(answer);
						pw.println();
						pw.close();
					}
					catch(FileNotFoundException fnfe){
						
						fnfe.printStackTrace();
						
						
					}
					//System.out.println("\nAverage completion time: " + answer);
				}
			
			

}
