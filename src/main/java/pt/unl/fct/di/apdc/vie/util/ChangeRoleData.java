package pt.unl.fct.di.apdc.vie.util;

public class ChangeRoleData {

	private String tokenID;
	private String newRole;
	private String username;
	
	public ChangeRoleData() {
	}
	
	public ChangeRoleData(String username, String tokenID, String newRole) {
		 this.username = username;
		 this.tokenID = tokenID;
		 this.newRole = newRole;
	 }
	
	public String getTokenID() {
		return tokenID;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getNewRole() {
		return this.newRole;
	}
	
}
