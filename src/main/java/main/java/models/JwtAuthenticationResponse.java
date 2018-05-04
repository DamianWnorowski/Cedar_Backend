package main.java.models;

import java.util.List;

public class JwtAuthenticationResponse {
    private String userToken;
    private String userName;
    private List<Content> userBlacklist;

    public JwtAuthenticationResponse(String token, String name, List<Content> blacklist){
        this.userToken = token;
        this.userName = name;
        this.userBlacklist = blacklist;
    }
    
    public String getUserToken() {
        return userToken;
    }

    public String getUserName() {
        return userName;
    }

    public List<Content> getUserBlacklist() {
        return userBlacklist;
    }
    
}