/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import main.java.dto.CriticApplicationForm;
import main.java.dto.ErrorCode;
import main.java.dto.PasswordChangeForm;
import main.java.dto.PasswordResetForm;
import main.java.dto.UserDTO;
import main.java.dto.UserReportForm;
import main.java.managers.CriticApplicationManager;
import main.java.managers.CriticManagerImpl;
import main.java.managers.ReviewManager;
import main.java.managers.UserManager;
import main.java.managers.UserReportManager;
import main.java.models.CriticApplication;
import main.java.models.User;
import main.java.models.UserReport;
import main.java.services.EmailService;
import main.java.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class SecureController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserReportManager userReportManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CriticApplicationManager criticApplicationManager;
    
    @GetMapping("/secure/resetpassword")
    public boolean resetPassword(@RequestParam(name="p") String pw){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return false;
        }
        User currentUser = userManager.findByEmail(email);
        System.out.println("Encoding plaintext pw: " + pw.trim());
        String encodedPw = bCryptPasswordEncoder.encode(pw.trim());
        currentUser.setPassword(encodedPw);
        userManager.save(currentUser);
        System.out.println("Password successfully reset to: " + encodedPw);
        SecurityContextHolder.clearContext();
        return true;
    }
    
    @PostMapping("secure/reportuser")
    public ResponseEntity reportUser(@RequestBody UserReportForm urf) throws IOException {
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userManager.findById(urf.getUserId()).get();
        if (u == null) {
            throw new RuntimeException("You are not allowed to access this page");
        }

        UserReport report = new UserReport();
        report.setUser(u);
        report.setDate(LocalDate.now());
        report.setDescription(urf.getDescription());

        userReportManager.save(report);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("secure/changepassword")
    public ResponseEntity changePassword(@RequestBody PasswordChangeForm pcf, HttpServletResponse res) throws IOException {
        String curUser, oldPassword, newPassword;
        curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        oldPassword = pcf.getOldPassword();
        newPassword = pcf.getNewPassword();
        System.out.println("Resetting pw for user: " + curUser
                + "\nnew password: " + newPassword);
        User u = userManager.findByEmail(curUser);

        if (u == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        if (!bCryptPasswordEncoder.matches(oldPassword.trim(), u.getPassword().trim())) {
            throw new RuntimeException("Old password is invalid");
        }

        String encodedPw = bCryptPasswordEncoder.encode(newPassword);
        u.setPassword(encodedPw);
        userManager.save(u);
        // TODO: Maybe show a "password changed message before redirect?
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("secure/changeemail")
    public ResponseEntity changeEmail(@RequestParam(value = "email") String email, HttpServletResponse res) throws IOException {
        String curEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Replacing " + curEmail + " with: " + email);

        User u = userManager.findByEmail(curEmail);
        if (u == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        u.setEmail(email);
        userManager.save(u);
        // TODO: Maybe show a "email changed message before redirect?
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("secure/apply")
    public ResponseEntity criticApplication(@RequestBody CriticApplicationForm caf, HttpServletResponse res) throws IOException {
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userManager.findByEmail(curUser);
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

    @GetMapping("/secure/verifyadmin")
    public boolean verifyadmin() {
        String curUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userManager.findByEmail(curUserEmail);
        if (user != null) {
            if (user.getRoles().contains("ROLE_ADMIN")) {
                System.out.println("User has admin role");
                return true;
            } else {
                System.out.println("User does not have admin role");
            }
        }
        throw new RuntimeException("You are not allowed to access this page");
    }

    @GetMapping("/secure/getuser")
    public UserDTO getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("user: " + email);
        try {
            User user = userManager.findByEmail(email);
            UserDTO dto = new UserDTO(user);
            return dto;
        } catch (Exception e) {
            System.out.println("can't get current logged in profile");
        }
        return null;
    }
    
    @GetMapping("/secure/follow")
    public boolean followUser(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return false;
        }
        User currentUser = userManager.findByEmail(email);
        User userToFollow;
        try {
            userToFollow = userManager.findById(id).get();
        } catch (NoSuchElementException e) {
            return false;
        }
        currentUser.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(currentUser);
    
        userManager.save(currentUser);
        userManager.save(userToFollow);
        return true;
    }
    
    @GetMapping("/secure/unfollow")
    public boolean unfollowUser(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return false;
        }
        User currentUser = userManager.findByEmail(email);
        User userToUnfollow;
        try {
            userToUnfollow = userManager.findById(id).get();
        } catch (NoSuchElementException e) {
            return false;
        }
        currentUser.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(currentUser);
    
        userManager.save(currentUser);
        userManager.save(userToUnfollow);
        return true;
    }
    
    @GetMapping("/secure/followers")
    public Set<User> getFollowers(@RequestParam(value = "id") int id) {
        User user = userManager.findById(id).get();
        if(user == null){
            throw new RuntimeException("wrong user");
        }
        return user.getFollowers();
    }
    
    @GetMapping("/secure/following")
    public Set<User> getFollowing(@RequestParam(value = "id") int id) {
        User user = userManager.findById(id).get();
        if(user == null){
            throw new RuntimeException("wrong user");
        }
        return user.getFollowing();
    }
}
