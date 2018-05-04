package main.java.models;

import java.util.List;

public class JwtAuthenticationResponse {
    private String userToken;
    private String userEmail;
    private List<Content> userBlacklist;

    public JwtAuthenticationResponse(String token, String email, List<Content> blacklist){
        this.userToken = token;
        this.userEmail = email;
        this.userBlacklist = blacklist;
    }
    
    public String getUserToken() {
        return userToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<Content> getUserBlacklist() {
        return userBlacklist;
    }
    
}