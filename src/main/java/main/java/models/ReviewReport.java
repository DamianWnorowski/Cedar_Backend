package main.java.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReviewReport {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer report_id;
	private LocalDate date;
	@ManyToOne(targetEntity=Review.class)
	private Review review;
	@ManyToOne(targetEntity=User.class)
	private User user;

	public ReviewReport() {
	
	}

	public ReviewReport(LocalDate date, Review review, User user) {
		this.date = date;
		this.review = review;
		this.user = user;
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
	
}
