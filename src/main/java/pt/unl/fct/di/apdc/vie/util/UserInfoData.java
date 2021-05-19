package pt.unl.fct.di.apdc.vie.util;

public class UserInfoData {
	
    private String firstName, lastName, role, username;

    public UserInfoData() {
    	
    }
    
    public UserInfoData(String firstName, String lastName, String role, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
    }
    
    /*public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}*/

}