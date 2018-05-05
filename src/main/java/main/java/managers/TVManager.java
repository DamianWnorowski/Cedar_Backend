package main.java.managers;

import main.java.models.TVShow;
import org.springframework.data.repository.CrudRepository;

public interface TVManager extends CrudRepository<TVShow, Integer> {


}