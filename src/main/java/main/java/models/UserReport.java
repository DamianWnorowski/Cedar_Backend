package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_report_id;
    private LocalDate date;
    @ManyToOne(targetEntity = User.class)
    private User user;
    private String description;
    
    public UserReport(){
        
    }
    public UserReport(Integer user_report_id, LocalDate date, User user, String description) {
        this.user_report_id = user_report_id;
        this.date = date;
        this.user = user;
        this.description = description;
    }

    public Integer getUser_report_id() {
        return user_report_id;
    }

    public void setUser_report_id(Integer user_report_id) {
        this.user_report_id = user_report_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
