package main.java.managers;

import main.java.models.Backdrop;
import main.java.models.Content;
import org.springframework.data.repository.CrudRepository;

public interface BackdropManager extends CrudRepository<Backdrop, Integer> {
	
	public Backdrop findByContent(Content c);
	
}
