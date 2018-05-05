package main.java.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.dto.UserDTO;
import main.java.managers.CriticManagerImpl;
import main.java.managers.ReviewManager;
import main.java.managers.UserManager;
import main.java.models.ErrorCode;
import main.java.models.JwtAuthenticationResponse;
import main.java.models.LoginForm;
import main.java.models.RegistrationForm;
import main.java.models.Review;
import main.java.models.User;
import main.java.models.UserRole;
import main.java.services.EmailService;
import main.java.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;    
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
	
	@Autowired
	private ReviewManager reviewManager;

    @Autowired
    private EmailService emailService;
	
	@Autowired
	private CriticManagerImpl criticManager;

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
        
        //Sending email verification
        String emailToken = bCryptPasswordEncoder.encode(user.getEmail());
        String body = emailService.generateEmailBody(user.getName(), user.getId(), emailToken);
        
        emailService.sendVerificationEmail(user.getEmail(), "Please verify your Cedar account!", body);
        
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
                // checking if user verified their email
                if (u.isVerified()) {
                    jwt = jwtTokenProvider.generateToken(u.getEmail());
                    resp = new JwtAuthenticationResponse(jwt, u.getName(), u.getBlacklist(), null);
                    return resp;
                } else {
                    return new JwtAuthenticationResponse(null, null, null,
                    "You must verify your email address before you can login!");
                }
            }
        }
        System.out.println("Such user does not exist");
        return null;

    }

    @GetMapping("/userlogout")
    public ResponseEntity logout(HttpServletRequest req) {
        System.out.println("logging out...");
        Authentication auth;
        auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        } else {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(HttpStatus.OK);

        }
    }
    
    @GetMapping("/verify/{id}/{token}")
    public ResponseEntity verifyEmail(@PathVariable int id, @PathVariable String token, HttpServletResponse res) throws IOException{
        User u = um.findById(id).get();
        if(bCryptPasswordEncoder.matches(u.getEmail(), token)){
            u.setVerified(true);
            um.save(u);
        }
        res.sendRedirect("http://localhost:3000/verified");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public String encryptPassword(String password) {
        return null;
    }

    public int validateUser(User u, LoginForm lf) {
        return 0;
    }

    @PostMapping("/api/deleteaccount")
    public ErrorCode deleteAccount(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return ErrorCode.NOTLOGGEDIN;
        }
        User currentUser = um.findByEmail(email);
        User userToDelete;
        try {
            userToDelete = um.findById(id).get();
        } catch (NoSuchElementException e) {
            return ErrorCode.DOESNOTEXIST;
        }

        if (currentUser.getId() != userToDelete.getId() && !currentUser.hasRole(UserRole.ROLE_ADMIN)) {
            return ErrorCode.INVALIDPERMISSIONS;
        }

        um.delete(userToDelete);
        return ErrorCode.SUCCESS;
    }

    @PostMapping("/api/followuser")
    public ErrorCode followUser(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return ErrorCode.NOTLOGGEDIN;
        }
        User currentUser = um.findByEmail(email);
        User userToFollow;
        try {
            userToFollow = um.findById(id).get();
        } catch (NoSuchElementException e) {
            return ErrorCode.DOESNOTEXIST;
        }

        List<User> usersFollowed = currentUser.getFollowing();
        usersFollowed.add(userToFollow);
        currentUser.setFollowing(usersFollowed);

        um.save(currentUser);
        return ErrorCode.SUCCESS;
    }

    @PostMapping("/api/unfollowuser")
    public ErrorCode unfollowUser(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return ErrorCode.NOTLOGGEDIN;
        }
        User currentUser = um.findByEmail(email);
        User userToRemove;
        try {
            userToRemove = um.findById(id).get();
        } catch (NoSuchElementException e) {
            return ErrorCode.DOESNOTEXIST;
        }

        List<User> usersFollowed = currentUser.getFollowing();

        if (!usersFollowed.contains(userToRemove)) {
            return ErrorCode.DOESNOTEXIST;
        }
        usersFollowed.remove(userToRemove);
        currentUser.setFollowing(usersFollowed);
        um.save(currentUser);
        return ErrorCode.SUCCESS;
    }

	@GetMapping("/api/profile")
	public UserDTO getUserInfo(@RequestParam(value="id") int id){
		try {
            User user = um.findById(id).get();
			UserDTO dto = new UserDTO(user);
            return dto;
        }
    	catch (Exception e) {
            System.out.println("can't get profile");
    	}

       return null;
	}
	
	@GetMapping("/api/getusersreviews")
	public List<Review> getReviews(@RequestParam(value="id") int id) {
		User author;
		try {
            author = um.findById(id).get();
        }
		catch (Exception e) {
            return null;
    	}
		return reviewManager.findByAuthor(author);
	}
	
	@GetMapping("/api/gettopcritics")
	public Iterable<User> getTopCritics() {
		List<Integer> topCriticsIds = criticManager.getTopCriticIds();
		Iterable<User> topCritics = um.findAllById(topCriticsIds);
		return topCritics;
}
}
