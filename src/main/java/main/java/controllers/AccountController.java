package main.java.controllers;

import java.util.NoSuchElementException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.managers.UserManager;
import main.java.models.ErrorCode;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        user.setRole(UserRole.ROLE_USER);
        System.out.println("Saving user with email: " + user.getEmail() + " and pw: " + user.getPassword());
        user = um.save(user);
        System.out.println("UserID for the user to be registered: " + user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm lf, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        lf.getEmail(),
                        lf.getPassword()
                )
        );
        
        String jwt = jwtTokenProvider.generateToken(authentication);
        return jwt;
    }

    public String encryptPassword(String password) {
        return null;
    }

    public int validateUser(User u, LoginForm lf) {
        return 0;
    }
	
	@PostMapping("/api/deleteaccount")
    public ErrorCode deleteAccount(@RequestParam(value="id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = um.findByEmail(email);
		User userToDelete;
		try {
			userToDelete = um.findById(id).get();
		}
		catch (NoSuchElementException e) {
			return ErrorCode.DOESNOTEXIST;
		}
		
		if (currentUser.getId() != userToDelete.getId() && currentUser.getRole() != UserRole.ROLE_ADMIN){
			return ErrorCode.INVALIDPERMISSIONS;
		}
		
		um.delete(userToDelete);
        return ErrorCode.SUCCESS;
    }
	
//	@PostMapping("/api/followuser")
//    public ErrorCode followUser(@RequestParam(value="id") int id) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		if (email.equals("anonymousUser")) {
//			return ErrorCode.NOTLOGGEDIN;
//		}
//		User currentUser = um.findByEmail(email);
//		User userToFollow;
//		try {
//			userToFollow = um.findById(id).get();
//		}
//		catch (NoSuchElementException e) {
//			return ErrorCode.DOESNOTEXIST;
//		}
//		
//		Set<User> usersFollowed = currentUser.getFollowers();
//		usersFollowed.add(userToFollow);
//		currentUser.setFollowers(usersFollowed);
//		
//		um.save(currentUser);
//        return ErrorCode.SUCCESS;
//    }
//	
//	@PostMapping("/api/unfollowuser")
//    public ErrorCode followUser(@RequestParam(value="id") int id) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		if (email.equals("anonymousUser")) {
//			return ErrorCode.NOTLOGGEDIN;
//		}
//		User currentUser = um.findByEmail(email);
//		User userToRemove;
//		try {
//			userToRemove = um.findById(id).get();
//		}
//		catch (NoSuchElementException e) {
//			return ErrorCode.DOESNOTEXIST;
//		}
//		
//		Set<User> usersFollowed = currentUser.getFollowers();
//		
//		if(!usersFollowed.contains(userToRemove)){
//			return ErrorCode.DATABASESAVEFAILURE;
//		}
//		usersFollowed.remove(userToRemove);
//		currentUser.setFollowers(usersFollowed);
//		um.save(currentUser);
//        return ErrorCode.SUCCESS;
//    }
	
}
