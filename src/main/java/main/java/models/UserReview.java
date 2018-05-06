package main.java.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class UserReview extends Review {

	public UserReview() {
	}
	
	public UserReview(Content content, User author, int rating, LocalDateTime date, String body) {
		super(content, author, rating, date, body);
	}
	
}