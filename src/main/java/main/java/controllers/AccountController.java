package main.java.controllers;

import javax.servlet.http.HttpServletRequest;
import main.java.managers.UserManager;
import main.java.models.LoginForm;
import main.java.models.RegistrationForm;
import main.java.models.User;
import main.java.models.UserRole;
import main.java.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private SecurityService securityService;

        @PostMapping("/register")
	public ResponseEntity register(@RequestBody RegistrationForm rf) {
            // TODO: Return type maybe String?
            User user = um.findByEmail(rf.getEmail());
            if(user != null){
                System.out.println("ERROR: USER ALREADY EXISTS WITH EMAIL: " + user.getEmail());
                return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
                // TODO: return type
            }
            String pw = bCryptPasswordEncoder.encode(rf.getPassword());
            user = new User();
            user.setName(rf.getFirstName() + " " + rf.getLastName());
            user.setEmail(rf.getEmail());
            user.setPassword(pw);
            user.setRole(UserRole.USER);
            user = um.save(user);
            System.out.println("UsreID for the user to be registered: " + user.getId());
            return ResponseEntity.ok(HttpStatus.OK);
	}
        
        @PostMapping("/login")
	public User login(@RequestBody LoginForm lf, HttpServletRequest req) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
            if(req == null){
                System.out.println("Req is null");
            }
            if(securityService.login(lf.getEmail(), lf.getPassword(), req)){
                System.out.println("We logged in boys");
                return um.findByEmail(lf.getEmail());
            }
            System.out.println("not logged in");
            // TODO: Return types
            return null;
	}

//	public String encryptPw(String pw) {
//            
//		return bCryptPasswordEncoder.encode(pw);
//	}

	public int validateUser(User u, LoginForm lf) {
		return 0;
	}

}