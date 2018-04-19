package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int review_id;
	@OneToOne
	private Content content;
	@OneToOne
	private User author;
	private double rating;
	private LocalDate date;
	private String body;

	public Review() {
	}
	
	public Review(Content content, User author, double rating, LocalDate date, String body) {
		this.content = content;
		this.author = author;
		this.rating = rating;
		this.date = date;
		this.body = body;
	}

}