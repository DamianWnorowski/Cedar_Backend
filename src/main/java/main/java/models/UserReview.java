package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class UserReview extends Review {

	public UserReview(int review_id, Content content, User author, Double rating, LocalDate date, String body) {
		super(review_id, content, author, rating, date, body);
	}
	
}