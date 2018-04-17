package main.java.controllers;

import javax.servlet.ServletContext;
import main.java.managers.UserManager;
import main.java.models.LoginForm;
import main.java.models.RegistrationForm;
import main.java.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class AccountService {
    
    
    @Autowired
    UserManager um;

        @PostMapping("/register")
	public ResponseEntity register(@RequestBody RegistrationForm rf) {
            // TODO: Return type maybe String?
            
            User user = um.findByEmail(rf.getEmail());
            if(user != null){
                System.out.println("ERROR: USER ALREADY EXISTS WITH EMAIL: " + user.getEmail());
                // user already exists
                return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
            }
            user = new User();
            //TODO: hash pw
            user.setName(rf.getFirstName() + " " + rf.getLastName());
            user.setEmail(rf.getEmail());
            user.setPassword(rf.getPassword());
            
            // Save user to crudrepo
            um.save(user);
            
            
            return ResponseEntity.ok(HttpStatus.OK);
	}
        
        @PostMapping("/login")
	public int login(@RequestBody LoginForm lf) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
            //UserManager um = (UserManager) sc.getAttribute("userManager");
            User user = um.findByEmail(lf.getEmail());
            if(user == null){
                // TODO: Error message
                System.out.println("User does not exist.");
                return 0;
            }
            String up, ep;
            up = user.getPassword();
            ep = lf.getPassword();
            System.out.println("Comparing: " + up + " with " + ep);
            if(up.equals(ep)){
                System.out.println("We logged in boys");
                return 1;
            }
            System.out.println("not logged in");
            return 0;
	}

	public String encryptPw(String pw) {
		return null;
	}

	public int validateUser(User u, LoginForm lf) {
		return 0;
	}

}