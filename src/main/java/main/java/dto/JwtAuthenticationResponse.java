package main.java.dto;

import java.util.List;
import main.java.models.Content;

public class JwtAuthenticationResponse {
    private String userToken;
    private String userName;
    private List<Content> userBlacklist;
    private int userId;

    public JwtAuthenticationResponse(String token, String name, List<Content> blacklist, int userId){
        this.userToken = token;
        this.userName = name;
        this.userBlacklist = blacklist;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }   
}