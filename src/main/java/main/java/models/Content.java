package main.java.models;

import java.time.LocalDate;
import java.util.List;
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
	private int content_id;
//	private ContentType type;
	private String title;
	private String genre;
	private LocalDate date;
	private double userRating;
	@OneToMany(targetEntity=UserReview.class, mappedBy="review_id")
	private List<UserReview> userReview;
	private double criticRating;
	@OneToMany(targetEntity=CriticReview.class, mappedBy="review_id")
	private List<CriticReview> criticReview;
	private String description;
//	@ManyToMany(targetEntity=Celebrity.class, mappedBy="celeb_id")
//	private List<Celebrity> celebrities;
	// private List<Image> snapshots; unsure of how we'll do mapping
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity director;
	private String trailerPath;
//	@OneToMany(targetEntity=TVShowSeason.class, mappedBy="season_id")
//	private List<TVShowSeason> seasonsList;
	private String poster_path;
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity writer;
	private String runtime;
	private String studio;

	public Content() {
	}

	public Content(int content_id, String title, String genre, LocalDate date, double userRating, List<UserReview> userReview, double criticRating, List<CriticReview> criticReview, String description, Celebrity director, String trailerPath, String poster_path, Celebrity writer, String runtime, String studio) {
		this.content_id = content_id;
		this.title = title;
		this.genre = genre;
		this.date = date;
		this.userRating = userRating;
		this.userReview = userReview;
		this.criticRating = criticRating;
		this.criticReview = criticReview;
		this.description = description;
		this.director = director;
		this.trailerPath = trailerPath;
		this.poster_path = poster_path;
		this.writer = writer;
		this.runtime = runtime;
		this.studio = studio;
	}
	
	

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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
		return userReview;
	}

	public void setUserReview(List<UserReview> userReview) {
		this.userReview = userReview;
	}

	public double getCriticRating() {
		return criticRating;
	}

	public void setCriticRating(double criticRating) {
		this.criticRating = criticRating;
	}

	public List<CriticReview> getCriticReview() {
		return criticReview;
	}

	public void setCriticReview(List<CriticReview> criticReview) {
		this.criticReview = criticReview;
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
	
	public List<Double> addReview(int contentID, double rating, String body, int userID) {
		return null;
	}
	
}