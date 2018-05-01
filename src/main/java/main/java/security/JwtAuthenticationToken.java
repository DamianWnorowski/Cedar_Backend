package main.java.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    User principal;
    String token;

    public JwtAuthenticationToken(String token, Object principal, Object credentials) {
        super(principal, credentials);
        this.token = token;
        this.principal = (User) principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
