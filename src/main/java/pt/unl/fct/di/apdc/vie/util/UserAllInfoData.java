package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class UserAllInfoData {

	private String address;
	private String birthDay;
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private String perfil;
	private String role;
	private String state;
	private String gender;
	private String phone;
	private long following;
	private List<Value<String>> followingList;
	private long followers;
	private List<Value<String>> followersList;
	private List<Value<String>> routesList;	
	private long warnings;
	private List<Value<String>> requestsList;
	private String nationality;
	private List<Value<String>> eventsList;
	private String description;
	
	public UserAllInfoData() {
	}

	public UserAllInfoData(String address, String birthDay, String name,
			String firstName, String lastName, String email, String perfil,
			String role, String state, String gender, String phone,
			long following, List<Value<String>> followingList, long followers,
			List<Value<String>> followersList, List<Value<String>> routesList,
			long warnings, List<Value<String>> requestsList, String nationality,
			List<Value<String>> eventsList, String description) {
		this.address = address;
		this.birthDay = birthDay;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.perfil = perfil;
		this.role = role;
		this.state = state;
		this.gender = gender;
		this.phone = phone;
		this.following = following;
		this.followingList = followingList;
		this.followers = followers;
		this.followersList = followersList;
		this.routesList = routesList;
		this.warnings = warnings;
		this.requestsList = requestsList;
		this.nationality = nationality;
		this.eventsList = eventsList;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public List<Value<String>> getEventsList() {
		return eventsList;
	}
	public String getNationality() {
		return nationality;
	}
	public List<Value<String>> getRequestsList() {
		return requestsList;
	}
	public long getWarnings() {
		return warnings;
	}
	public List<Value<String>> getRoutesList() {
		return routesList;
	}
	public List<Value<String>> getFollowersList() {
		return followersList;
	}
	public long getFollowers() {
		return followers;
	}
	public List<Value<String>> getFollowingList() {
		return followingList;
	}
	public long getFollowing() {
		return following;
	}
	public String getPhone() {
		return phone;
	}
	public String getGender() {
		return gender;
	}
	public String getState() {
		return state;
	}
	public String getRole() {
		return role;
	}
	public String getPerfil() {
		return perfil;
	}
	public String getEmail() {
		return email;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getname() {
		return name;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public String getAddress() {
		return address;
	}
	
}
