package pt.unl.fct.di.apdc.vie.util;

public class ListRoutesData {
	
	private String username;
	private String tokenID;

	public ListRoutesData() {

	}

	public ListRoutesData(String username, String tokenID) {

		this.username = username;
		this.tokenID = tokenID;

	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getTokenID() {
		return this.tokenID;
	}

}
