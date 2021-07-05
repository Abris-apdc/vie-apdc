package pt.unl.fct.di.apdc.vie.util;

public class DeleteRouteData {

	private String username, tokenID, route;

    public DeleteRouteData() {
    	
    }
    
    public DeleteRouteData(String username, String tokenID, String route) {
        this.username = username;
        this.tokenID = tokenID;
        this.route = route;
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

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
}
