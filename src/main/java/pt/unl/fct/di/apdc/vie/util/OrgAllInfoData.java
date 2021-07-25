package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class OrgAllInfoData {

	private String address;
	private String cardID;
	private String email;
	private long followers;
	private List<Value<String>> followersList;
	private long following;
	private List<Value<String>> followingList;
	private String name;
	private String owner;
	private String role;
	private List<Value<String>> routesList;
	private String service;
	private String state;
	private long warnings;
	private String phone;
	private List<Value<String>> requestsList;
	private List<Value<String>> eventsList;
	private String description;
	
	public OrgAllInfoData() {
	}
	
	public OrgAllInfoData(String address, String cardID, String email, long followers,
			List<Value<String>> followersList, long following, List<Value<String>> followingList, 
			String name, String owner, String role, List<Value<String>> routesList,
			String service, String state, long warnings, String phone,
			List<Value<String>> requestsList, List<Value<String>> eventsList, String description) {
		this.address = address;
		this.cardID = cardID;
		this.email = email;
		this.followers = followers;
		this.followersList = followersList;
		this.following = following;
		this.followingList = followingList;
		this.name = name;
		this.owner = owner;
		this.role = role;
		this.routesList = routesList;
		this.service = service;
		this.state = state;
		this.warnings = warnings;
		this.phone = phone;
		this.requestsList = requestsList;
		this.eventsList = eventsList;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public List<Value<String>> getEventsList() {
		return eventsList;
	}
	public List<Value<String>> getRequestsList() {
		return requestsList;
	}
	public String getPhone() {
		return phone;
	}
	public long getWarnings() {
		return warnings;
	}
	public String getState() {
		return state;
	}
	public String getService() {
		return service;
	}
	public List<Value<String>> getRoutesList() {
		return routesList;
	}
	public String getRole() {
		return role;
	}
	public String getOwner() {
		return owner;
	}
	public String getName() {
		return name;
	}
	public List<Value<String>> getFollowingList() {
		return followingList;
	}
	public long getFollowing() {
		return following;
	}
	public List<Value<String>> getFollowersList() {
		return followersList;
	}
	public long getFollowers() {
		return followers;
	}
	public String getEmails() {
		return email;
	}
	public String getcardID() {
		return cardID;
	}
	public String getAddress() {
		return address;
	}
	
	
}
