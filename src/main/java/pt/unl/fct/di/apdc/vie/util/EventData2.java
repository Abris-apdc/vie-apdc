package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

import com.google.cloud.datastore.Value;

public class EventData2 {
	
	private String eventName, coordinates, address, org, duration;
	private List<Value<String>> participants_list;
	
	public EventData2() {}

	public EventData2(String eventName, String coordinates, String address, String org, String duration) {
		// TODO Auto-generated constructor stub
		this.eventName = eventName;
		this.coordinates = coordinates;
		this.address = address;
		this.org = org;
		this.duration = duration;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<Value<String>> getParticipants_list() {
		return participants_list;
	}

	public void setParticipants_list(List<Value<String>> participants_list) {
		this.participants_list = participants_list;
	}
	
	
	
	
	

}
