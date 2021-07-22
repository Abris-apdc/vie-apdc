package pt.unl.fct.di.apdc.vie.util;

public class DeleteEventData {

	private String tokenID, name;
	
	public DeleteEventData() {	}
	
	public DeleteEventData(String tokenID, String name) {
		this.tokenID = tokenID;
		this.name = name;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
