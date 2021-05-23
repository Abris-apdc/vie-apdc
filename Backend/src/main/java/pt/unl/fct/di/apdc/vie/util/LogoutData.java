package pt.unl.fct.di.apdc.vie.util;

public class LogoutData {

	private String tokenID;
	
	public LogoutData() {
	}
	
	public LogoutData(String tokenID) {
		this.tokenID = tokenID;
	}
	
	public String getTokenID() {
		return tokenID;
	}
}
