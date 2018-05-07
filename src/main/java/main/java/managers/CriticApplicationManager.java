package main.java.managers;

import main.java.models.CriticApplication;
import org.springframework.data.repository.CrudRepository;


public interface CriticApplicationManager extends CrudRepository<CriticApplication, Integer>{
    
}
