package pt.unl.fct.di.apdc.vie.util;

public class ModifyOrgData {

	private String email;
	private String phoneNumber;
	private String address;
	private String country;
	private String description;
	private String serviceType;
	private String cardID;
	private String owner;
	private String tokenID;
	
	
	public ModifyOrgData() {
	}
	
	public ModifyOrgData( String email,
	 String phoneNumber,
	 String address,
	 String country,
	 String description,
	 String serviceType,
	 String cardID,
	 String owner,
	 String tokenID) {
		
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.country = country;
		this.description = description;
		this.serviceType = serviceType;
		this.cardID = cardID;
		this.owner = owner;
		this.tokenID = tokenID;
		
	}
	
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getCountry() {
		return country;
	}
	public String getDescription() {
		return description;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getTokenID() {
		return tokenID;
	}
	public String getCardID() {
		return cardID;
	}
	public String getOwner() {
		return owner;
	}
}
