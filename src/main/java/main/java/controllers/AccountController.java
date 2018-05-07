package main.java.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.dto.CriticApplicationForm;
import main.java.dto.PasswordResetForm;
import main.java.dto.ImportantReviewsDTO;
import main.java.dto.UserDTO;
import main.java.managers.CriticManagerImpl;
import main.java.managers.ReviewManager;
import main.java.managers.UserManager;
import main.java.dto.ErrorCode;
import main.java.dto.JwtAuthenticationResponse;
import main.java.dto.LoginForm;
import main.java.dto.PwResetToken;
import main.java.dto.RegistrationForm;
import main.java.managers.CriticApplicationManager;
import main.java.models.CriticApplication;
import main.java.models.Content;
import main.java.models.Review;
import main.java.models.User;
import main.java.models.UserRole;
import main.java.services.EmailService;
import main.java.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private JwtTokenProviderService jwtTokenProvider;

    @Autowired
    private ReviewManager reviewManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CriticManagerImpl criticManager;

    @Autowired
    private CriticApplicationManager criticApplicationManager;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationForm rf) {
        User user = um.findByEmail(rf.getEmail());
        if (user != null) {
            System.out.println("ERROR: USER ALREADY EXISTS WITH EMAIL: " + user.getEmail());
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
            // TODO: return type
        }
        String pw = bCryptPasswordEncoder.encode(rf.getPassword().trim());
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
        String emailToken = jwtTokenProvider.generateToken(user.getEmail());
        //String emailToken = bCryptPasswordEncoder.encode(user.getEmail());
        String body = emailService.generateVerificationEmailBody(user.getName(), user.getId(), emailToken);

        emailService.sendEmail(user.getEmail(), "Please verify your Cedar account!", body);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginForm lf, HttpServletRequest req, HttpServletResponse res) {
        String jwt;
        JwtAuthenticationResponse resp;
        User u = um.findByEmail(lf.getEmail());
        if (u != null) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + lf.getPassword());
            if (bCryptPasswordEncoder.matches(lf.getPassword(), u.getPassword())) {
                // checking if user verified their email
                if (u.isVerified()) {
                    jwt = jwtTokenProvider.generateToken(u.getEmail());
                    resp = new JwtAuthenticationResponse(jwt, u.getName(), (List<Content>) u.getBlacklist(), null);
                    return resp;
                } else {
                    //throw new RuntimeException("You must verify your email address before you can login!");
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
    public ResponseEntity verifyEmail(@PathVariable int id, @PathVariable String token, HttpServletResponse res) throws IOException {
        System.out.println("Verifying with token: " + token);
        User u = um.findById(id).get();
        String email = jwtTokenProvider.getEmail(token);
        if (email.equals(u.getEmail())) {
            u.setVerified(true);
            um.save(u);
            res.sendRedirect("http://localhost:3000/verified");
            return ResponseEntity.ok(HttpStatus.OK);
        }
        res.sendRedirect("http://localhost:3000/404");
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam(value = "email") String email) throws IOException {
        System.out.println("User: " + email + " forgot his/her password!");

        User u = um.findByEmail(email);
        if (u == null) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        String resetJwt = jwtTokenProvider.generatePasswordResetToken(u.getPassword().trim());
        PwResetToken pwResetToken = new PwResetToken();
        pwResetToken.setUsed(false);
        pwResetToken.setPwToken(resetJwt);

        u.setPwResetToken(pwResetToken);
        um.save(u);

        String body = emailService.generateForgotPwEmailBody(u.getName(), u.getId(), resetJwt);
        emailService.sendEmail(u.getEmail(), "Forgotten your Cedar password? Look inside!", body);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/forgot/{id}/{token}")
    public void isResetPasswordTokenValid(@PathVariable int id, @PathVariable String token, HttpServletResponse res) throws IOException {
        System.out.println("Resetting password procedures for user: " + id
                + "\nwith token: " + token);

        String oldPasswordHash = jwtTokenProvider.getEmail(token).trim();
        User u = um.findById(id).get();
        if (u == null) {
            return;
        }

        if (!oldPasswordHash.trim().equals(u.getPassword().trim())) {
            System.out.println("User exists but checking token equality with pw failed");
            return;
        }

        PwResetToken pwResetToken = u.getPwResetToken();
        if (pwResetToken.isUsed()) {
            System.out.println("Token was already used");
            return;
        }
        // remove comments for it to work
        pwResetToken.setUsed(true);

        String newToken = jwtTokenProvider.generatePasswordResetToken(u.getEmail());
        pwResetToken.setPwToken(newToken);
        System.out.println("The new token with email subject is: " + newToken);
        um.save(u);
        res.setHeader("token", newToken);
        res.sendRedirect("http://localhost:3000/secure/changepassword/");

    }

    @PostMapping("secure/changepassword")
    public ResponseEntity changePassword(@RequestBody PasswordResetForm prf, HttpServletResponse res) throws IOException {
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Its working if my token is printed: " + prf.getToken());
        String email = jwtTokenProvider.getEmail(prf.getToken());
        String newPassword = prf.getPassword();
        System.out.println("Resetting pw for user: " + email
                + "\nnew password: " + newPassword);
        User u = um.findByEmail(email);

        if (u == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        String encodedPw = bCryptPasswordEncoder.encode(newPassword);
        u.setPassword(encodedPw);
        um.save(u);
        // TODO: Maybe show a "password changed message before redirect?
        res.sendRedirect("/");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("secure/changeemail")
    public ResponseEntity changeEmail(@RequestParam(value = "email") String email, HttpServletResponse res) throws IOException {
        String curEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Replacing " + curEmail + " with: " + email);

        User u = um.findByEmail(curEmail);
        if (u == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        u.setEmail(email);
        um.save(u);
        // TODO: Maybe show a "email changed message before redirect?
        res.sendRedirect("/");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("secure/apply")
    public ResponseEntity criticApplication(@RequestBody CriticApplicationForm caf, HttpServletResponse res) throws IOException {
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = um.findByEmail(curUser);
        System.out.println("User: " + curUser + " is applying to become a Critic!");
        if (u == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        CriticApplication criticApp = new CriticApplication();
        criticApp.setDate(LocalDate.now());
        criticApp.setUser(u);
        criticApp.setReason(caf.getReason());
        if (caf.getWebsiteURL() != null) {
            criticApp.setWebsiteURL(caf.getWebsiteURL());
        }
        criticApplicationManager.save(criticApp);
        return ResponseEntity.ok(HttpStatus.OK);
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

        Set<User> usersFollowed = currentUser.getFollowing();
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

        Set<User> usersFollowed = currentUser.getFollowing();

        if (!usersFollowed.contains(userToRemove)) {
            return ErrorCode.DOESNOTEXIST;
        }
        usersFollowed.remove(userToRemove);
        currentUser.setFollowing(usersFollowed);
        um.save(currentUser);
        return ErrorCode.SUCCESS;
    }

    @GetMapping("/api/profile")
    public UserDTO getUserInfo(@RequestParam(value = "id") int id) {
        try {
            User user = um.findById(id).get();
            user.addProfileView();
            um.save(user);
            UserDTO dto = new UserDTO(user);
            return dto;
        } catch (Exception e) {
            System.out.println("can't get profile");
        }

        return null;
    }

    @GetMapping("/api/getusersreviews")
    public List<Review> getUsersReviews(@RequestParam(value = "id") int id) {
        User author;
        try {
            author = um.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        return reviewManager.findByAuthor(author);
    }

    @GetMapping("/api/getusersimportantreviews")
    public ImportantReviewsDTO getUsersImportantReviews(@RequestParam(value = "id") int id) {
        User author;
        try {
            author = um.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        Review latest = reviewManager.findTop1ByAuthorOrderByDateDesc(author);
        Review best = reviewManager.findTop1ByAuthorOrderByRatingDesc(author);
        Review worst = reviewManager.findTop1ByAuthorOrderByRatingAsc(author);
        return new ImportantReviewsDTO(latest, best, worst);
    }

    @GetMapping("/api/gettopcritics")
    public Iterable<User> getTopCritics() {
        List<Integer> topCriticsIds = criticManager.getTopCriticIds();
        Iterable<User> topCritics = um.findAllById(topCriticsIds);
        return topCritics;
    }

}
