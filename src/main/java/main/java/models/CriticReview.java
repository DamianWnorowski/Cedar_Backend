package main.java.models;

import java.net.URI;
import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class CriticReview extends Review {
	
	private URI sourceUrl;

	public CriticReview(URI sourceUrl, int review_id, Content content, User author, Double rating, LocalDate date, String body) {
		super(review_id, content, author, rating, date, body);
		this.sourceUrl = sourceUrl;
	}
	
}