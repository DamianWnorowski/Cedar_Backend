package main.java.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import main.java.managers.UserManager;
import main.java.models.LoginForm;
import main.java.models.RegistrationForm;
import main.java.models.User;
import main.java.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("http://localhost:3000")
@RestController
public class AccountService {
        @Autowired
        private UserManager um;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @PostMapping("/register")
	public int register(@RequestBody RegistrationForm rf) {
            User user = um.findByEmail(rf.getEmail());
            if(user != null){
                System.out.println("User already exists");
                return 0;
            }
            String encodedPassword = passwordEncoder.encode(rf.getPassword());
            user = new User();
            user.setName(rf.getFirstName() + " " + rf.getLastName());
            System.out.println("Printing the name of user to register: " + rf.getFirstName() + " " + rf.getLastName());
            user.setEmail(rf.getEmail());
            user.setPassword(encodedPassword);
            user.setRole(UserRole.USER);
            user = um.save(user);
            System.out.println("UsreID for " + user.getName() + " to be registered: " + user.getId());
            return 1;
	}

        @PostMapping("/login")
	public User login(@RequestBody LoginForm lf, HttpSession session) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
            User user = (User) session.getAttribute("user");
            if(user != null){
                System.out.println("User attribute is not null in session. Someone already logged in");
                return user;
            }
            
            user = um.findByEmail(lf.getEmail());
            if(user == null){
                System.out.println("User does not exist");
                return null;
            }
            
            if(passwordEncoder.matches(lf.getPassword(), user.getPassword())){
                session.setAttribute("user", user);
            }  

            return user;
	}

	public String encryptPw(String pw) {
            return null;
	}

	public int validateUser(User u, LoginForm lf) {
            return 0;
	}

}