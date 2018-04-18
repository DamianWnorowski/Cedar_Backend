package main.java.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.stereotype.Service;

@Service
public class SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    public String findLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(name != null){
            return name;
        }
        return null;
    }

    public boolean login(String username, String password, HttpServletRequest req) {
        System.out.println("We in da login method");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        
        Authentication authResult = authenticationManager.authenticate(authReq);
        
        
        if (authResult.isAuthenticated()) {
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authResult);
            //HttpSession session = req.getSession(true);
            //session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            //Authentication auth = ((SecurityContext)session.getAttribute(SPRING_SECURITY_CONTEXT_KEY)).getAuthentication(); // SecurityContextHolder.getContext().getAuthentication();
            //System.out.println("The name in context: " + auth.getName());

            return true;
        }
        return false;
    }
}