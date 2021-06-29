package pt.unl.fct.di.apdc.vie.util;

public class LocationData {

	private String coordinates;
	private String address;
	private String owner;
	private String info;
	private String schedule;
	private String tokenID;

	public LocationData() {

	}

	public LocationData(String coordinates, String address, String owner, String info, String schedule, String tokenID) {
		this.coordinates = coordinates;
		this.address = address;
		this.owner = owner;
		this.info = info;
		this.schedule = schedule;
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

	public String getOwner() {
		return owner;	
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getInfo() {
		return info;	
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public String getSchedule() {
		return schedule;	
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getTokenID() {
		return tokenID;	
	}
	public void setTokenID (String tokenID) {
		this.tokenID = tokenID;
	}

}
