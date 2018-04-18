package main.java.managers;

import main.java.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewManager extends CrudRepository<Review, Integer> {
	
}
