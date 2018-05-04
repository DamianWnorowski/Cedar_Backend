package main.java.managers;

import java.time.LocalDate;
import java.util.List;
import main.java.models.CriticReview;
import main.java.models.Review;
import main.java.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ReviewManager extends CrudRepository<Review, Integer> {
	public List<CriticReview> findTop10ByDateBeforeOrderByDateDesc(LocalDate date);
	public List<Review> findByAuthor(User author);
}
