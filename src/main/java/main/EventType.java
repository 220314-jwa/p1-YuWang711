package main;

public class EventType {
	private int eventTypeID;
	private String eventType;
	
	public EventType() {
		this.setEventTypeID(1);
	}
	
	public EventType(int eventTypeID, String eventType) {
		this.setEventType(eventType);
		this.setEventTypeID(eventTypeID);
	}
	
	public int getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(int eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	

	
}
