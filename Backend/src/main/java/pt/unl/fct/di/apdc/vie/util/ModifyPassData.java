package pt.unl.fct.di.apdc.vie.util;

public class ModifyPassData {
	
	private String oldPass;
	private String newPass;
	private String confirmation;
	private String tokenID;

	
	
	public ModifyPassData()
	{}
	
	public ModifyPassData(String oldPass,
			String newPass, 
			String confirmation,
			String tokenID) {
		this.oldPass = oldPass;
		this.newPass = newPass;
		this.confirmation = confirmation;	
		this.tokenID = tokenID;		

	}
	
	
	public String getOldPass() {
		return oldPass;
	}
	
	public String getNewPass() {
		return newPass;
	}
	
	public String getConfirmation() {
		return confirmation;
	}
	
	public String getTokenID() {
		return tokenID;
	}
}
