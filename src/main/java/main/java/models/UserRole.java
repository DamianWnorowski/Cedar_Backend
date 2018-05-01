package main.java.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority{
	ROLE_USER, ROLE_ADMIN, ROLE_CRITIC;

    @Override
    public String getAuthority() {
        return name();
    }
}
