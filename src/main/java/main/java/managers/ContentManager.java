package main.java.managers;

import org.springframework.data.repository.CrudRepository;
import main.java.models.Movies;

public interface ContentManager extends CrudRepository<Movies, Integer> {


}