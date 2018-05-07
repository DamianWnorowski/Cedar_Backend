package main.java.dto;

import javax.validation.constraints.NotNull;

public class ReviewForm {
	
	@NotNull
	private Integer rating;
	private String body;
	private Integer content_id;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	
}
