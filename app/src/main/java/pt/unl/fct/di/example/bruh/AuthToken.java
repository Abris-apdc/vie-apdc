package pt.unl.fct.di.example.bruh;


public class AuthToken {
    private String username;
    private String tokenID;
    private long creationData;
    private long expirationData;
    private String role;


    public String getUsername() {
        return username;
    }

    public String getTokenID() {
        return tokenID;
    }

    public long getCreationData() {
        return creationData;
    }

    public long getExpirationData() {
        return expirationData;
    }

    public void setExpirationData(long expirationData) {
        this.expirationData = expirationData;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}