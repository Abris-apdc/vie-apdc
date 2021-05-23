package pt.unl.fct.di.apdc.vie.util;

public class ModifyOrgData {

	private String name;
	private String secondName;
	private String phoneNumber;
	private String mobilePhone;
	private String address;
	private String city;
	private String country;
	private String cp;
	private String description;
	private String serviceType;
	private String cardID;
	private String owner;
	private String tokenID;
	
	
	public ModifyOrgData() {
	}
	
	public ModifyOrgData( String name,
	 String secondName,
	 String phoneNumber,
	 String mobilePhone,
	 String address,
	 String city,
	 String country,
	 String cp,
	 String description,
	 String serviceType,
	 String cardID,
	 String owner,
	 String tokenID) {
		
		this.name = name;
		this.secondName = secondName;
		this.phoneNumber = phoneNumber;
		this.mobilePhone = mobilePhone;
		this.address = address;
		this.city = city;
		this.country = country;
		this.cp = cp;
		this.description = description;
		this.serviceType = serviceType;
		this.cardID = cardID;
		this.owner = owner;
		this.tokenID = tokenID;
		
	}
	
	public String getName() {
		return name;
	}
	public String getSecondName() {
		return secondName;
	}
/*	public String getPassword (){
		return password;
	}
	public String getNewPass() {
		return newPassword;
	}
	public String getNewConfirmation() {
		return newConfirmation;
	}*/
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getCP() {
		return cp;
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
