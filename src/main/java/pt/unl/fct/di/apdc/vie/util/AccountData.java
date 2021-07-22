package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class AccountData {
	
	
	private  String address, birthday, cardID, email, firstName, gender, lastName, name, owner, perfil, phone, role, service, state;
	private List<Value<String>> followers_list, reqs, routes, following_list, events_list;
	private long followers, following, warnings;
	


	public AccountData(String address, String birthday, String cardID, String email, String firstName, long followers, long following, String gender, String lastName, String name, String owner, String perfil, String phone,  String role, String service, String state, long warnings ) {
		this.address = address;
		this.birthday = birthday;
		this.cardID = cardID;
		this.email = email;
		this.firstName = firstName;
		this.followers = followers;
		this.following = following;
		this.lastName = lastName;
		this.name = name;
		this.owner = owner;
		this.perfil = perfil;
		this.phone = phone;
		this.role = role;
		this.service = service;
		this.state = state;
		this.warnings = warnings;

	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getFollowing() {
		return following;
	}

	public void setFollowing(long following) {
		this.following = following;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getWarnings() {
		return warnings;
	}

	public void setWarnings(long warnings) {
		this.warnings = warnings;
	}

	public List<Value<String>> getFollowers_list() {
		return followers_list;
	}

	public void setFollowers_list(List<Value<String>> followers_list) {
		this.followers_list = followers_list;
	}

	public List<Value<String>> getReqs() {
		return reqs;
	}

	public void setReqs(List<Value<String>> reqs) {
		this.reqs = reqs;
	}

	public List<Value<String>> getFollowing_list() {
		return following_list;
	}

	public void setFollowing_list(List<Value<String>> following_list) {
		this.following_list = following_list;
	}

	public List<Value<String>> getRoutes() {
		return routes;
	}


	public void setRoutes(List<Value<String>> routes) {
		// TODO Auto-generated method stub
		this.routes = routes;
		
	}


	public List<Value<String>> getEvents_list() {
		return events_list;
	}


	public void setEvents_list(List<Value<String>> events_list) {
		this.events_list = events_list;
	}
	
	


}
