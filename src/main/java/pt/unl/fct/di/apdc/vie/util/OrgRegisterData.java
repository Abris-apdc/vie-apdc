package pt.unl.fct.di.apdc.vie.util;


public class OrgRegisterData {

	private String name;
	private String owner;
	private String email;
	private String password;
	private String id;
	private String address;
	private String serviceType;
	

	public OrgRegisterData() {

	}

	public OrgRegisterData(String name, String owner, String email, String password,
			String id, String address, String serviceType) {
		this.name = name;
		this.owner = owner;
		this.email = email;
		this.password = password;
		this.id = id;
		this.address = address;
		this.serviceType = serviceType;
		
	}

	public String getName() {
		return name;
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
	
	
	public String getid() {
		return id;
	}
	
	
	public String getAddress() {
		return address;
	}
	
	
	public String getServiceType() {
		return serviceType;
	}
	

	

}
