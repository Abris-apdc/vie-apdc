package pt.unl.fct.di.apdc.vie.util;


public class UserRegisterData {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String year;
	private String month;
	private String day;

	public UserRegisterData() {

	}

	public UserRegisterData(String firstName, String lastName, String username, String email, String password,
			String year, String month, String day) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.year = year;
		this.month = month;
		this.day = day;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getYear() {
		return year;
	}
	public String getMonth() {
		return month;
	}
	public String getDay() {
		return day;
	}

	

}