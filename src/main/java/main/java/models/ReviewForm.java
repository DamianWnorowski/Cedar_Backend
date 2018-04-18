package main.java.models;

import javax.validation.constraints.NotNull;

public class ReviewForm {
	
	@NotNull
	private Double rating;
	private String body;
	private int content_id;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
