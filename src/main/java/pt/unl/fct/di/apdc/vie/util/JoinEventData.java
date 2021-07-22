package pt.unl.fct.di.apdc.vie.util;

public class JoinEventData {
	
	private String event;
	private String tokenID;
	
	public JoinEventData() {
		
	}
	
	public JoinEventData(String event, String tokenID) {
		this.event = event;
		this.tokenID = tokenID;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}


	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	
	
	
	

}
