package main.java.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.managers.UserManager;
import main.java.models.JwtAuthenticationResponse;
import main.java.models.LoginForm;
import main.java.models.RegistrationForm;
import main.java.models.User;
import main.java.models.UserRole;
import main.java.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class AccountController {

    @Autowired
    private UserManager um;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProviderService jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationForm rf) {
        // TODO: Return type maybe String?
        User user = um.findByEmail(rf.getEmail());
        if (user != null) {
            System.out.println("ERROR: USER ALREADY EXISTS WITH EMAIL: " + user.getEmail());
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
            // TODO: return type
        }
        String pw = bCryptPasswordEncoder.encode(rf.getPassword());
        user = new User();
        user.setName(rf.getFirstName() + " " + rf.getLastName());
        user.setEmail(rf.getEmail());
        user.setPassword(pw);
        user.getRoles().add(UserRole.ROLE_USER.name());
        user.setVisible(true);
        user.setVerified(false);
        System.out.println("Saving user with email: " + user.getEmail() + " and pw: " + user.getPassword());
        user = um.save(user);
        System.out.println("UserID for the user to be registered: " + user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginForm lf, HttpServletRequest req, HttpServletResponse res) {
        String jwt;
        JwtAuthenticationResponse resp;
        User u;

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        lf.getEmail(),
//                        lf.getPassword()
//                )
//        );
        u = um.findByEmail(lf.getEmail());
        if (u != null) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
            if (bCryptPasswordEncoder.matches(lf.getPassword(), u.getPassword())) {
                jwt = jwtTokenProvider.generateToken(u.getEmail());
                resp = new JwtAuthenticationResponse(jwt, u.getEmail(), u.getBlackList());
                return resp;
            }
        }
        System.out.println("Such user does not exist");
        return null;

    }

    public String encryptPassword(String password) {
        return null;
    }

    public int validateUser(User u, LoginForm lf) {
        return 0;
    }

}
