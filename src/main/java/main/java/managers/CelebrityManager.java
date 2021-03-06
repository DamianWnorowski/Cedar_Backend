package main.java.managers;

import java.util.List;
import main.java.models.Celebrity;
import org.springframework.data.repository.CrudRepository;

public interface CelebrityManager extends CrudRepository<Celebrity, Integer> {
	public List<Celebrity> findByNameContainingIgnoreCase(String token);
	/* public List<Celebrity> findByContent(Content c);
	public List<Content> search(String query) {
		return null;
	}
	*/

}