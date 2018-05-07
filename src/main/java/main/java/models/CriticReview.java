package main.java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CriticReview extends Review {
	
	private String sourceUrl;
	@JsonIgnore
	@ManyToOne(targetEntity=Content.class)
	@JoinColumn(name = "content_id")
	private Content content;

	public CriticReview() {
	}

	public CriticReview(String sourceUrl, Content content, User author, int rating, LocalDateTime date, String body) {
		super(author, rating, date, body);
		this.sourceUrl = sourceUrl;
		this.content = content;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

}