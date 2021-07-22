package pt.unl.fct.di.apdc.vie.util;

public class ModifyRouteData {
	
	private String tokenID;
	private String routeName;
	private String[] routeLocations;
	private String info;
	
	

	public ModifyRouteData() {

	}

	public ModifyRouteData(String tokenID, String routeName, String[] routeLocations, String info) {

		this.tokenID = tokenID;
		this.routeName = routeName;
		this.routeLocations = routeLocations;
		this.info = info;

	}
	
	

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String[] getRouteLocations() {
		return routeLocations;
	}

	public void setRouteLocations(String[] routeLocations) {
		this.routeLocations = routeLocations;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
	public String getTokenID() {
		return this.tokenID;
	}

}
