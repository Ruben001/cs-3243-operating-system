
public class PageTable {
	private int frameNumber, jobID;
	private boolean valid;
	
	public PageTable(){
		this.frameNumber=0;
		this.jobID=0;
		this.valid=false;
	}
	
	public void updatePage(int frame, int jobID, boolean valid){
		this.frameNumber=frame;
		this.jobID=jobID;
		this.valid=valid;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}
