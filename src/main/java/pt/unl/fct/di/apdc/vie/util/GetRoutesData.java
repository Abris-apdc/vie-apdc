package pt.unl.fct.di.apdc.vie.util;

public class GetRoutesData {
	
	private String tokenID;
	private String username;
	
	public GetRoutesData() {
	}
	
	public GetRoutesData(String username, String tokenID) {
		 this.username = username;
		 this.tokenID = tokenID;
	 }


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

}
