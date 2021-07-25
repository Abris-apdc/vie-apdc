package pt.unl.fct.di.apdc.vie.util;

import java.util.List;

public class RouteInfo {
	
	private String name, owner, info;
	private List<String> events, coords;
	
	public RouteInfo(String name, String owner, String info) {
		this.name = name;
		this.owner = owner;
		this.info = info;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public List<String> getCoords() {
		return coords;
	}

	public void setCoords(List<String> coords) {
		this.coords = coords;
	}
	
	

}
