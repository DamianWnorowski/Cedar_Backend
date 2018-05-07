package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ReviewReport {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer report_id;
	private LocalDate date;
	@ManyToOne(targetEntity=Review.class)
	private Review review;
	@OneToOne
	private User user;
	private String description;

	public ReviewReport() {
	
	}

	public ReviewReport(LocalDate date, Review review, User user, String description) {
		this.date = date;
		this.review = review;
		this.user = user;
		this.description = description;
	}

	public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}
