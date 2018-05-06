package main.java.managers;

import java.time.LocalDateTime;
import java.util.List;
import main.java.models.Content;
import main.java.models.CriticReview;
import main.java.models.Review;
import main.java.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ReviewManager extends CrudRepository<Review, Integer> {
	public List<CriticReview> findTop10ByDateBeforeOrderByDateDesc(LocalDateTime date);
	public List<Review> findByAuthor(User author);
	public List<Review> findByContent(Content content);
	public Review findTop1ByAuthorOrderByDateDesc(User author);
	public Review findTop1ByAuthorOrderByRatingAsc(User author);
	public Review findTop1ByAuthorOrderByRatingDesc(User author);

}
