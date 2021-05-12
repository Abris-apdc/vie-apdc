package pt.unl.fct.di.apdc.vie.util;

public class LoginData {

	private String username;
	private String password;

	public LoginData() {

	}

	public LoginData(String username, String password) {

		this.username = username;
		this.password = password;

	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}