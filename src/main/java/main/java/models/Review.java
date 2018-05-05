package main.java.models;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
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
	private int rating;
	private LocalDate date;
	private String body;
	@OneToMany(targetEntity=ReviewReport.class, mappedBy="report_id")
	private List<ReviewReport> reports;

	public Review() {
		reports = new ArrayList<>();
	}
	
	public Review(Content content, User author, int rating, LocalDate date, String body) {
		reports = new ArrayList<>();
		this.content = content;
		this.author = author;
		this.rating = rating;
		this.date = date;
		this.body = body;
	}

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<ReviewReport> getReports() {
		return reports;
	}

	public void setReports(List<ReviewReport> reports) {
		this.reports = reports;
	}
	
	public void addReport(ReviewReport r) {
		reports.add(r);
	}
	
	
	
}