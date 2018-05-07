package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CriticApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer application_id;
    private LocalDate date;
    @OneToOne
    private User user;
    private String reason;
    private String websiteURL;
    
    
}
