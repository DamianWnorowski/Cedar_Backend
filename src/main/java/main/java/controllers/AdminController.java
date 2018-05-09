package main.java.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import main.java.dto.ErrorCode;
import main.java.dto.RegistrationForm;
import main.java.managers.ContentManager;
import main.java.managers.CriticApplicationManager;
import main.java.managers.CriticManagerImpl;
import main.java.managers.ReviewManager;
import main.java.managers.ReviewReportManager;
import main.java.managers.UserManager;
import main.java.managers.UserReportManager;
import main.java.models.Content;
import main.java.models.ReviewReport;
import main.java.models.User;
import main.java.models.UserReport;
import main.java.models.UserRole;
import main.java.services.EmailService;
import main.java.services.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminController {
    @Autowired
    private UserManager um;
    
    @Autowired
    private UserReportManager userReportManager;
    
    @Autowired
    private ContentManager contentManager;
    
    @Autowired ReviewReportManager reviewReportManager;
    
    @PostMapping("/admin/createcontent")
    public void createContentPage(@RequestBody RegistrationForm rf) {
        
    }
    
    @PostMapping("/admin/updatecontent")
    public void updateContentPage(@RequestBody RegistrationForm rf) {
        
    }
    
    @GetMapping("/admin/getuserreports")
    public List<UserReport> getUserReports() {
        Iterable itr = userReportManager.findAll();
        List userReportList = new ArrayList();
        itr.forEach(userReportList::add);
        return userReportList;
    }
    
    @GetMapping("/admin/getreviewreports")
    public List<ReviewReport> getReviewReports() {
        Iterable itr = reviewReportManager.findAll();
        List reviewReportList = new ArrayList();
        itr.forEach(reviewReportList::add);
        return reviewReportList;
    }
    
    @GetMapping("admin/deleteuser")
    public boolean deleteAccount(@RequestParam("id") int id) throws IOException {
        User userToDelete = um.findById(id).get();
        User currentUser = um.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.getRoles().contains("ROLE_ADMIN")){
            System.out.println("User has admin role");
        } else {
            System.out.println("User does not have admin role!");
        }
        
        if(userToDelete == null || currentUser == null){
            throw new RuntimeException("Something went wrong, some user is null");
        }
        
        System.out.println("Deleting account: " + userToDelete.getEmail());
        um.delete(userToDelete);
        return true;
    }
    
    @GetMapping("/admin/deletecontent")
    public ErrorCode deleteContent(@RequestParam(value = "id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email.equals("anonymousUser")) {
            return ErrorCode.NOTLOGGEDIN;
        }
        User currentUser = um.findByEmail(email);
        Content contentToDelete;
        try {
            contentToDelete = contentManager.findById(id).get();
        } catch (NoSuchElementException e) {
            return ErrorCode.DOESNOTEXIST;
        }

        if (!currentUser.hasRole(UserRole.ROLE_ADMIN)) {
            return ErrorCode.INVALIDPERMISSIONS;
        }
        System.out.println("Deleting movie with id: " + contentToDelete.getId());
        contentManager.delete(contentToDelete);
        return ErrorCode.SUCCESS;
    }

}
