package main.java.dto;

import java.time.LocalDate;

public class ContentDTO {
	
	private int id;
	private String title;
	private LocalDate date;
	private double userRating;
	private double criticRating;
	private boolean isMovie;

	public ContentDTO() {
	}

	public ContentDTO(int id, String title, LocalDate date, double userRating, double criticRating, boolean isMovie) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.userRating = userRating;
		this.criticRating = criticRating;
		this.isMovie = isMovie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getUserRating() {
		return userRating;
	}

	public void setUserRating(double userRating) {
		this.userRating = userRating;
	}

	public double getCriticRating() {
		return criticRating;
	}

	public void setCriticRating(double criticRating) {
		this.criticRating = criticRating;
	}

	public boolean isIsMovie() {
		return isMovie;
	}

	public void setIsMovie(boolean isMovie) {
		this.isMovie = isMovie;
	}
	
}
