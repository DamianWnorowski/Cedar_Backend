package main.java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserReview extends Review {
	
	@JsonIgnore
	@ManyToOne(targetEntity=Content.class)
	@JoinColumn(name = "content_id")
	private Content content;

	public UserReview() {
	}
	
	public UserReview(Content content, User author, int rating, LocalDateTime date, String body) {
		super(author, rating, date, body);
		this.content = content;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
	
	
}