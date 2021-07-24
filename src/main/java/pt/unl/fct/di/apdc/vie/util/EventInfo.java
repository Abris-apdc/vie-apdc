package pt.unl.fct.di.apdc.vie.util;

public class EventInfo {

	private String event, coordinates;
	
	public EventInfo(String event, String coordinates) {
		this.event = event;
		this.coordinates = coordinates;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
