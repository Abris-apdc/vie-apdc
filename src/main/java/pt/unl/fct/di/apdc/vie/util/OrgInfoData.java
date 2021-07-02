package pt.unl.fct.di.apdc.vie.util;

public class OrgInfoData {

	
	private String name, address, service, role, email;

    public OrgInfoData() {
    	
    }
    
    public OrgInfoData(String role, String name, String address, String service, String email) {
        this.name = name;
        this.address = address;
        this.service = service;
        this.role = role;
        this.email = email;
    }
    

}
