package pt.unl.fct.di.apdc.vie.util;

public class DeleteData {

	private String username;
	private String tokenID;
	private String password;
	
	public DeleteData() {
	}
	
	public DeleteData(String username, String password, String tokenID) {
		this.tokenID = tokenID;
		this.username = username;
		this.password = password;
	}
	
	public String getTokenID() {
		return tokenID;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}