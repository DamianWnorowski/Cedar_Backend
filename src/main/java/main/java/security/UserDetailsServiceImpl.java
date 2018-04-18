package main.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
import main.java.managers.UserManager;
import main.java.models.User;
import main.java.models.UserRole;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserManager um;

    @Override
    //@Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("We are in loadByUsername");

        User user = um.findByEmail(email);
        if (user != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            // TODO: multiple user roles
            if (user.getRole() == UserRole.USER) {
                grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            }
            System.out.println("Leaving loadByUsername and returning UserDetails");

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User was not found");
        }
    }

}
