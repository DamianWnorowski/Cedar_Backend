package main.java.dto;

import javax.validation.constraints.NotNull;

public class ReviewReportForm {
	
	@NotNull
	private String description;
	@NotNull
	private Integer reviewId;

	public ReviewReportForm() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}
	
	
}
