package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class AccountData {
	
	
	public String address, birthday, cardID, creation_time, email, firstName, followers, following, gender, lastName, name, owner, perfil, phone, pwd, role, service, state, warnings;
	public List<Value<String>> followers_list, reqs, routes, following_list;
	
	public AccountData(String address, String birthday, String cardID, String creation_time, String email, String firstName, String followers, String following, String gender, String lastName, String name, String owner, String perfil, String phone, String pwd, String role, String service, String state, String warnings ) {
		this.address = address;
		this.birthday = birthday;
		this.cardID = cardID;
		this.creation_time = creation_time;
		this.email = email;
		this.firstName = firstName;
		this.followers = followers;
		this.following = following;
		this.lastName = lastName;
		this.name = name;
		this.owner = owner;
		this.perfil = perfil;
		this.phone = phone;
		this.pwd = pwd;
		this.role = role;
		this.service = service;
		this.state = state;
		this.warnings = warnings;

	}

	public void setFollowers(List<Value<String>> followers1) {
		// TODO Auto-generated method stub
		this.followers_list = followers1;
		
	}

	public void setRequests(List<Value<String>> reqs) {
		// TODO Auto-generated method stub
		this.reqs = reqs;
	}

	public void setRoutes(List<Value<String>> routes) {
		// TODO Auto-generated method stub
		this.routes = routes;
		
	}

	public void setFollowing(List<Value<String>> following1) {
		// TODO Auto-generated method stub
		this.following_list = following1;
	}

}
