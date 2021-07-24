package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class OrgAllInfoData {

	private String address;
	private String cardID;
	private String email;
	private int followers;
	private List<Value<String>> followersList;
	private int following;
	private List<Value<String>> followingList;
	private String name;
	private String owner;
	private String role;
	private List<Value<String>> routesList;
	private String service;
	private String state;
	private int warnings;
	private String phone;
	private List<Value<String>> requestsList;
	private List<Value<String>> eventsList;
	
	public OrgAllInfoData() {
	}
	
	public OrgAllInfoData(String address, String cardID, String email, int followers,
			List<Value<String>> followersList, int following, List<Value<String>> followingList, 
			String name, String owner, String role, List<Value<String>> routesList,
			String service, String state, int warnings, String phone,
			List<Value<String>> requestsList, List<Value<String>> eventsList) {
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
	}
	
}
