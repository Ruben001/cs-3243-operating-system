import java.util.LinkedList;


public class JobFrame {
	private int jobID;
	private LinkedList<Integer> frameList;
	
	public JobFrame(){
		jobID=0;
		frameList = new LinkedList<Integer>();
		
	}
	
	public void addFrame(int frameNumber){
		frameList.add(frameNumber);
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public LinkedList<Integer> getFrameList() {
		return frameList;
	}

	public void setFrameList(LinkedList<Integer> frameList) {
		this.frameList = frameList;
	}

}
