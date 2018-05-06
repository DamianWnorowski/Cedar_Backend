package main.java.dto;

import main.java.models.Review;

public class ImportantReviewsDTO {
	
	private Review latest;
	private Review best;
	private Review worst;

	public ImportantReviewsDTO(Review latest, Review best, Review worst) {
		this.latest = latest;
		this.best = best;
		this.worst = worst;
	}

	public Review getLatest() {
		return latest;
	}

	public void setLatest(Review latest) {
		this.latest = latest;
	}

	public Review getBest() {
		return best;
	}

	public void setBest(Review best) {
		this.best = best;
	}

	public Review getWorst() {
		return worst;
	}

	public void setWorst(Review worst) {
		this.worst = worst;
	}
	
}
