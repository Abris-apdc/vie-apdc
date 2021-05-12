package pt.unl.fct.di.apdc.vie.util;

public class DeleteData {

	private String username;
	private String tokenID;
	
	public DeleteData() {
	}
	
	public DeleteData(String username, String tokenID) {
		this.tokenID = tokenID;
		this.username = username;
	}
	
	public String getTokenID() {
		return tokenID;
	}
	public String getUsername() {
		return username;
	}
}