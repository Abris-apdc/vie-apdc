package pt.unl.fct.di.apdc.vie.util;


public class ModifyData { 
	
	private String firstName;
	private String lastName;
	private String newEmail;
	//private String password;
	//private String newPassword;
	//private String newConfirmation;
	private String gender;
	private String phoneNumber;
	private String landLine;
	private String address;
	private String secondAddress;
	private String city;
	private String country;
	private String cp;
	private String nationality;
	private String firstLanguage;
	private String secondLanguage;
	private String description;
	private String educationLevel;
	private String perfil;
	private String tokenID;
	
	
	public ModifyData() {
	}
	
	public ModifyData( String firstName,
	 String lastName,
	 String email,
	 //String password,
	 //String newPassword,
	 //String newConfirmation,
	 String gender,
	 String phoneNumber,
	 String landLine,
	 String address,
	 String secondAddress,
	 String city,
	 String country,
	 String cp,
	 String nationality,
	 String firstLanguage,
	 String secondLanguage,
	 String description,
	 String educationLevel, 
	 String perfil, String tokenID) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.newEmail = email;
		//this.password = password;
		//this.newPassword = newPassword;
		//this.newConfirmation = newConfirmation;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.landLine = landLine;
		this.address = address;
		this.secondAddress = secondAddress;
		this.city = city;
		this.country = country;
		this.cp = cp;
		this.nationality = nationality;
		this.firstLanguage = firstLanguage;
		this.secondLanguage = secondLanguage;
		this.description = description;
		this.educationLevel = educationLevel;
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
	/*public String getPassword (){
		return password;
	}
	public void setPassword (String password){
		this.password = password;
	}
	public String getNewPass() {
		return newPassword;
	}
	public void setNewPass(String newPass) {
		this.newPassword = newPass;
	}
	public String getNewConfirmation() {
		return newConfirmation;
	}
	public void setNewConfirmation(String newConfirmation) {
		this.newConfirmation = newConfirmation;
	}*/
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
	public String getLandLine() {
		return landLine;
	}
	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSecondAddress() {
		return secondAddress;
	}
	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCP() {
		return cp;
	}
	public void setCP(String cp) {
		this.cp = cp;
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
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
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
