package main.java.models;

import java.net.URI;
import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class CriticReview extends Review {
	
	private URI sourceUrl;

	public CriticReview() {
	}

	public CriticReview(URI sourceUrl, Content content, User author, int rating, LocalDateTime date, String body) {
		super(content, author, rating, date, body);
		this.sourceUrl = sourceUrl;
	}
	
}