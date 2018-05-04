
package main.java.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.managers.UserManager;
import main.java.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserManager um;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final User user = um.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User '" + email + "' not found");
    }
    
    List<GrantedAuthority> authorities
      = new ArrayList<>();
    for (String role: user.getRoles()){
        authorities.add(new SimpleGrantedAuthority(role));
    }
    
    return org.springframework.security.core.userdetails.User
        .withUsername(email)
        .password(user.getPassword())
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }

}