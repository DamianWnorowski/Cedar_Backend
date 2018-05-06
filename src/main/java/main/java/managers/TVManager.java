package main.java.managers;


import java.util.List;
import main.java.models.Content;
import main.java.models.TVShow;
import org.springframework.data.repository.CrudRepository;

public interface TVManager extends CrudRepository<Content, Integer> {
	public List<TVShow> findByTitleContainingIgnoreCase(String token);
}