package main;

public class Status {
	private int status;
	private String statusType;
	
	public Status() {
		this.setStatus(1);
	}
	
	public Status(int status,String statusType) {
		this.setStatus(status);
		this.setStatusType(statusType);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	
}
