package main.java.managers;

import main.java.models.Ad;
import org.springframework.data.repository.CrudRepository;


public interface AdManager extends CrudRepository<Ad, Integer> {



}