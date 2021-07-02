package pt.unl.fct.di.apdc.vie.util;

public class RouteData {
	private String username, tokenID, routeName, info;
	private String[] locations;

    public RouteData() {
    	
    }
    
    public RouteData(String username, String tokenID, String[] locations, String routeName, String info) {
        this.username = username;
        this.tokenID = tokenID;
        this.routeName = routeName;
        this.info = info;
        this.locations = locations;
    }
    
    
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

}
