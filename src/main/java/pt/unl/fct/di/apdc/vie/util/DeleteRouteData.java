package pt.unl.fct.di.apdc.vie.util;

public class DeleteRouteData {

	private String tokenID, route;

    public DeleteRouteData() {
    	
    }
    
    public DeleteRouteData( String tokenID, String route) {
        this.tokenID = tokenID;
        this.route = route;
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
