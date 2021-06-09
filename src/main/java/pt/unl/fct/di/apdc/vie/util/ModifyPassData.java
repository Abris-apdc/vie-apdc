package pt.unl.fct.di.apdc.vie.util;

public class ModifyPassData {
	
	private String oldPass;
	private String newPass;
	private String tokenID;

	
	
	public ModifyPassData(){
	}
	
	public ModifyPassData(String oldPass,
			String newPass, 
			String tokenID) {
		this.oldPass = oldPass;
		this.newPass = newPass;
		this.tokenID = tokenID;
	}
	
	
	public String getOldPass() {
		return oldPass;
	}
	
	public String getNewPass() {
		return newPass;
	}
	
	public String getTokenID() {
		return tokenID;
	}
}
