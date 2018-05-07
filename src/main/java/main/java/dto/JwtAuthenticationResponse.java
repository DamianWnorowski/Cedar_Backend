package main.java.dto;

import java.util.List;
import main.java.models.Content;

public class JwtAuthenticationResponse {
    private String userToken;
    private String userName;
    private List<Content> userBlacklist;
    private String errorMsg;

    public JwtAuthenticationResponse(String token, String name, List<Content> blacklist, String error){
        this.userToken = token;
        this.userName = name;
        this.userBlacklist = blacklist;
        this.errorMsg = error;
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