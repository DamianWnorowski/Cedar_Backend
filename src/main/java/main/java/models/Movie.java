package main.java.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;

@Entity
public class Movie extends Content {
	
	private double  boxOffice;
	private boolean currentlyInTheaters;

	public Movie() {
	}

	public Movie(double boxOffice, boolean currentlyInTheaters, int content_id, String title, Set<Genre> genres, LocalDate date, double userRating, List<UserReview> userReviews, double criticRating, List<CriticReview> criticReviews, String description, Celebrity director, String trailerPath, String poster_path, Celebrity writer, String runtime, String studio) {
		super(content_id, title, genres, date, userRating, userReviews, criticRating, criticReviews, description, director, trailerPath, poster_path, writer, runtime, studio);
		this.boxOffice = boxOffice;
		this.currentlyInTheaters = currentlyInTheaters;
	}

	public double getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(double boxOffice) {
		this.boxOffice = boxOffice;
	}

	public boolean isCurrentlyInTheaters() {
		return currentlyInTheaters;
	}

	public void setCurrentlyInTheaters(boolean currentlyInTheaters) {
		this.currentlyInTheaters = currentlyInTheaters;
	}
	
	
	
	
}
