package main.java.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.dto.CriticApplicationForm;
import main.java.dto.DeleteAccountForm;
import main.java.dto.PasswordResetForm;
import main.java.dto.ImportantReviewsDTO;
import main.java.dto.UserDTO;
import main.java.managers.CriticManagerImpl;
import main.java.managers.ReviewManager;
import main.java.managers.UserManager;
import main.java.dto.ErrorCode;
import main.java.dto.JwtAuthenticationResponse;
import main.java.dto.LoginForm;
import main.java.dto.PasswordChangeForm;
import main.java.models.PwResetToken;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/uploadFile")
    public void uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Uploading file: " + file.getOriginalFilename() + " with type: " + file.getContentType());
        if (!file.getContentType().contains("image")) {
            throw new RuntimeException("Must be an image file");
        }
        User currentUser = um.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser == null){
            throw new RuntimeException("Must be logged in");
        }
        currentUser.setPhoto(file.getBytes());
        um.save(currentUser);

    }

    @GetMapping(value = "api/getPhoto")
    public byte[] getPoster(@RequestParam(value = "id") int id) {
        User u = um.findById(id).get();
        if(u == null){
            throw new RuntimeException("invalid user");
        }
        return u.getPhoto();
    }

    public boolean verifyCaptcha(String captchaResponse) {
        boolean verified = false;
        String googleURL = "https://www.google.com/recaptcha/api/siteverify";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("secret", "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe");
        map.add("response", captchaResponse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(googleURL, request, String.class);
        String json = response.getBody();

        if (json.contains("true") && json.contains("success")) {
            System.out.println("### USED VERIFIED AS HUMAN ###");
            return true;
        }

        return false;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationForm rf) {
        verifyCaptcha(rf.getRecaptchaResponse());
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
        String emailToken = jwtTokenProvider.generateToken(user);
        String body = emailService.generateVerificationEmailBody(user.getName(), user.getId(), emailToken);

        emailService.sendEmail(user.getEmail(), "Please verify your Cedar account!", body);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/resendemail")
    public void resendEmail(@RequestParam(name = "id") int id) {
        User user = um.findById(id).get();
        if (user == null) {
            throw new RuntimeException("User doe snot exist");
        }
        //Sending email verification
        String emailToken = jwtTokenProvider.generateToken(user);
        String body = emailService.generateVerificationEmailBody(user.getName(), user.getId(), emailToken);

        emailService.sendEmail(user.getEmail(), "Please verify your Cedar account!", body);
        System.out.println("Verification email resent to " + user.getEmail());
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginForm lf) {
        String jwt;
        JwtAuthenticationResponse resp;
        User u = um.findByEmail(lf.getEmail().trim());
        if (u != null) {
            System.out.println("logging in with: " + lf.getEmail() + " and pw: " + u.getPassword());
            if (bCryptPasswordEncoder.matches(lf.getPassword().trim(), u.getPassword().trim())) {
                // checking if user verified their email
                if (u.isVerified()) {
                    jwt = jwtTokenProvider.generateToken(u);
                    List<Content> blackList = new ArrayList(u.getBlacklist());
                    resp = new JwtAuthenticationResponse(jwt, u.getEmail(), blackList, u.getId());
                    return resp;
                } else {
                    throw new RuntimeException(u.getId() + "unverified");
                }
            }
        }
        System.out.println("Such user does not exist");
        throw new RuntimeException("User does not exist or password does not match!");
    }
    
    @GetMapping("/secure/resetpassword")
    public boolean resetPassword(@RequestParam(name="p") String pw){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return false;
        }
        User currentUser = um.findByEmail(email);
        System.out.println("Encoding plaintext pw: " + pw.trim());
        String encodedPw = bCryptPasswordEncoder.encode(pw.trim());
        currentUser.setPassword(encodedPw);
        um.save(currentUser);
        System.out.println("Password successfully reset to: " + encodedPw);
        SecurityContextHolder.clearContext();
        return true;
    }

    @GetMapping("/userlogout")
    public ResponseEntity logout() {
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
            res.sendRedirect("http://localhost:3000/");
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
    public String isResetPasswordTokenValid(@PathVariable int id, @PathVariable String token, HttpServletResponse res) throws IOException {
        System.out.println("Resetting password procedures for user: " + id
                + "\nwith token: " + token);

        String oldPasswordHash = jwtTokenProvider.getEmail(token).trim();
        User u = um.findById(id).get();
        if (u == null) {
            System.out.println("User not found");
            return null;
        }

        if (!oldPasswordHash.trim().equals(u.getPassword().trim())) {
            System.out.println("User exists but checking token equality with pw failed");
            return null;
        }

        PwResetToken pwResetToken = u.getPwResetToken();
        if (pwResetToken.isUsed()) {
            System.out.println("Token was already used");
            throw new RuntimeException("Token was already used");
        }
        // remove comments for it to work
        pwResetToken.setUsed(true);

        String newToken = jwtTokenProvider.generatePasswordResetToken(u.getEmail());
        pwResetToken.setPwToken(newToken);
        System.out.println("The new token with email subject is: " + newToken);
        um.save(u);
        //res.setHeader("token", newToken);
        //res.sendRedirect("http://localhost:3000/secure/resetpassword/");
        return newToken;
    }

    @PostMapping("/api/deleteaccount")
    public ErrorCode deleteAccount(@RequestBody DeleteAccountForm daf, HttpServletResponse res) throws IOException {
        User userToDelete = um.findById(daf.getId()).get();
        User currentUser = um.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userToDelete == null || currentUser == null) {
            throw new RuntimeException("You must be logged in to delete an account");
        }

        if (!userToDelete.equals(currentUser)) {
            throw new RuntimeException("Invalid accounts");
        }

        if (!bCryptPasswordEncoder.matches(daf.getPassword(), userToDelete.getPassword())) {
            throw new RuntimeException("Invalid accounts");
        }
        System.out.println("Deleting account: " + userToDelete.getEmail());
        um.delete(userToDelete);
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
            throw new RuntimeException("404");
        }
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
