package pt.unl.fct.di.apdc.vie.util;

public class JoinEventData {
	
	private String event;
	private String username;
	
	public JoinEventData() {
		
	}
	
	public JoinEventData(String event, String username) {
		this.event = event;
		this.username = username;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}
