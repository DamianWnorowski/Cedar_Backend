package main.java.models;

import main.java.dto.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Content {
	@Id
	private int id;
	private String title;
	@ElementCollection
	private Set<Genre> genres;
	private LocalDate date;
	private double userRating;
	@OneToMany(targetEntity=UserReview.class, mappedBy="content")
	private List<UserReview> userReviews;
	private double criticRating;
	@OneToMany(targetEntity=CriticReview.class, mappedBy="content")
	private List<CriticReview> criticReviews;
	private String description;
	/*
	@ManyToMany(targetEntity=Celebrity.class, mappedBy="celeb_id")
	private List<Celebrity> celebrities;
	private List<Image> snapshots;
	*/
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity director;
	private String trailerPath;
	private String poster_path;
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity writer;
	private String runtime;
	private String studio;

	public Content() {
	}

	public Content(int id, String title, Set genres, LocalDate date, double userRating, List<UserReview> userReviews, double criticRating, List<CriticReview> criticReviews, String description, Celebrity director, String trailerPath, String poster_path, Celebrity writer, String runtime, String studio) {
		this.id = id;
		this.title = title;
		this.genres = genres;
		this.date = date;
		this.userRating = userRating;
		this.userReviews = userReviews;
		this.criticRating = criticRating;
		this.criticReviews = criticReviews;
		this.description = description;
		this.director = director;
		this.trailerPath = trailerPath;
		this.poster_path = poster_path;
		this.writer = writer;
		this.runtime = runtime;
		this.studio = studio;
	}
	
	public int getContent_id() {
		return id;
	}

	public void setContent_id(int id) {
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

	public List<UserReview> getUserReview() {
		return userReviews;
	}

	public void setUserReview(List<UserReview> userReviews) {
		this.userReviews = userReviews;
	}

	public double getCriticRating() {
		return criticRating;
	}

	public void setCriticRating(double criticRating) {
		this.criticRating = criticRating;
	}

	public List<CriticReview> getCriticReview() {
		return criticReviews;
	}

	public void setCriticReview(List<CriticReview> criticReviews) {
		this.criticReviews = criticReviews;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Celebrity getDirector() {
		return director;
	}

	public void setDirector(Celebrity director) {
		this.director = director;
	}

	public String getTrailerPath() {
		return trailerPath;
	}

	public void setTrailerPath(String trailerPath) {
		this.trailerPath = trailerPath;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public Celebrity getWriter() {
		return writer;
	}

	public void setWriter(Celebrity writer) {
		this.writer = writer;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public List<UserReview> getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(List<UserReview> userReviews) {
		this.userReviews = userReviews;
	}

	public List<CriticReview> getCriticReviews() {
		return criticReviews;
	}

	public void setCriticReviews(List<CriticReview> criticReviews) {
		this.criticReviews = criticReviews;
	}
	
	public void addReview(Review review) {
		if (review instanceof CriticReview) 
			criticReviews.add((CriticReview)review);
		else
			userReviews.add((UserReview)review);
	}
	
	public ErrorCode calculateRatings() {
		return ErrorCode.SUCCESS;
	}
	
}