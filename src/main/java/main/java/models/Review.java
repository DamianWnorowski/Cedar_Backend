package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

	@Id
	private int review_id;
	@OneToOne
	private Content content;
	@OneToOne
	private User author;
	private Double rating;
	private LocalDate date;
	private String body;

	public Review() {
	}
	
	public Review(int review_id, Content content, User author, Double rating, LocalDate date, String body) {
		this.review_id = review_id;
		this.content = content;
		this.author = author;
		this.rating = rating;
		this.date = date;
		this.body = body;
	}
	
	

}