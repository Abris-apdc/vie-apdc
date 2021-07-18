package pt.unl.fct.di.apdc.vie.util;

public class EventData {

	private String name;
	private String coordinates;
	private String address;
	private String org;
	private String duration;
	private String tokenID;

	public EventData() {

	}

	public EventData(String name, String coordinates, String address, String org, String duration, String tokenID) {
		this.name = name;
		this.coordinates = coordinates;
		this.address = address;
		this.org = org;
		this.duration = duration;
		this.tokenID = tokenID;
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

	public String getTokenID() {
		return tokenID;	
	}
	public void setTokenID (String tokenID) {
		this.tokenID = tokenID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
