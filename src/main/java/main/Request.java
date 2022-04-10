package main;
import java.time.LocalDate;

public class Request {
	private int requestID;
	private int submitter_id;
	private EventType event_type_id;
	private Status status;
	private String event_date;
	private double cost;
	private String description;
	private String location;
	private String submitted_at;
	
	//Constructor only used when retrieving data	//getter
	public int getSubmitterId() {
		return submitter_id;
	}
	
	public EventType getEventTypeId() {
		return event_type_id;
	}
	
	public Status getStatusId() {
		return status;
	}
	
	public String getEventDate() {
		return event_date;
	}
	
	public double getCost() {
		return cost;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
	}
	public String getSubmittedAt() {
		return submitted_at;
	}

	//Setter
	public void setSubmitterId(int submitterId) {
		this.submitter_id = submitterId;
	}
	
	public void setEventTypeId(EventType eventTypeId) {
		this.event_type_id = eventTypeId;
	}
	
	public void setStatusId(Status statusId) {
		this.status = statusId;
	}
	
	public void setEventDate(String event_date) {
		this.event_date = event_date;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setSubmittedAt(String submittedAt) {
		this.submitted_at = submittedAt;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}



}
