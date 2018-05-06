package main.java.services;

import main.java.managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private String subject;
    private String template;
    
    @Autowired
    JavaMailSender emailSender;
    
    @Autowired
    UserManager um;
    
    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        System.out.println("Sending email to: " + to);
    }
    
    public String generateVerificationEmailBody(String name, int id, String token){
        String body = "Hello " + name + ",\n\n";
        body += "Please click the following link to activate your account:\n";
        body += "http://localhost:8080/verify/" + id + "/" + token ;
        body += "\nThank you for using our service!\nTeam Cedar";
        
        return body;
    }
    
    public String generateForgotPwEmailBody(String name, int id, String token){
        String body = "Hello " + name + ",\n\n";
        body += "Please click the following link to reset your password:\n";
        body += "http://localhost:8080/forgot/" + id + "/" + token ;
        body += "\nThank you for using our service!\nTeam Cedar";
        
        return body;
    }
}
