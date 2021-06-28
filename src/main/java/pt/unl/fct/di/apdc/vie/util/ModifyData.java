package pt.unl.fct.di.apdc.vie.util;


public class ModifyData { 
	
	private String firstName;
	private String lastName;
	private String newEmail;
	private String gender;
	private String phoneNumber;
	private String address;
	private String nationality;
	private String firstLanguage;
	private String description;
	private String perfil;
	private String tokenID;
	
	
	public ModifyData() {
	}
	
	public ModifyData( String firstName,
	 String lastName,
	 String email,
	 String gender,
	 String phoneNumber,
	 String address,
	 String nationality,
	 String firstLanguage,
	 String description,
	 String perfil, 
	 String tokenID) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.newEmail = email;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.nationality = nationality;
		this.firstLanguage = firstLanguage;
		this.description = description;
		this.perfil = perfil;
		this.tokenID = tokenID;
		
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
	public String getEmail() {
		return newEmail;
	}
	public void setEmail(String email) {
		this.newEmail = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getFirstLanguage() {
		return firstLanguage;
	}
	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTokenID() {
		return tokenID;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
