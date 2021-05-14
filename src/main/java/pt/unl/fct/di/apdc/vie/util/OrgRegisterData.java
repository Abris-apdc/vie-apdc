package pt.unl.fct.di.apdc.vie.util;


public class OrgRegisterData {

	private String name;
	private String secondName;
	private String owner;
	private String email;
	private String password;
	private String confirmation;
	private String info;
	private String ID;
	private String address;
	private String CP;
	private String location;
	private String country;
	private String phone;
	private String mobile;
	private String serviceType;
	

	public OrgRegisterData() {

	}

	public OrgRegisterData(String name, String secondName, String owner, String email, String password,
			String confirmation,String info, String ID, String address, String CP, String location, String country, String phone, String mobile, String serviceType) {
		this.name = name;
		this.secondName = secondName;
		this.owner = owner;
		this.email = email;
		this.password = password;
		this.confirmation = confirmation;
		this.info = info;
		this.ID = ID;
		this.address = address;
		this.CP = CP;
		this.location = location;
		this.country = country;
		this.phone = phone;
		this.mobile = mobile;
		this.serviceType = serviceType;
		
	}

	public String getName() {
		return name;
	}

	
	public String getSecondName() {
		return secondName;
	}

	
	public String getOwner() {
		return owner;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}


	public String getConfirmation() {
		return confirmation;
	}


	public String getInfo() {
		return info;
	}
	
	public String getId() {
		return ID;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getCP() {
		return CP;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	

	

}
