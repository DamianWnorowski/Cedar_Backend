package main.java.models;

import java.awt.Image;
import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Content {
	@Id
	private int content_id;
	private ContentType type;
	private String title;
	private String genre;
	private Date date;
	private double userRating;
	@OneToMany(targetEntity=UserReview.class, mappedBy="user_review_id")
	private List<UserReview> userReview;
	private double criticRating;
	@OneToMany(targetEntity=CriticReview.class, mappedBy="critic_review_id")
	private List<CriticReview> criticReview;
	private String description;
//	@ManyToMany(targetEntity=Celebrity.class, mappedBy="celeb_id")
//	private List<Celebrity> celebrities;
	// private List<Image> snapshots; unsure of how we'll do mapping
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity director;
	private URI trailerPath;
	@OneToMany(targetEntity=TVShowSeason.class, mappedBy="season_id")
	private List<TVShowSeason> seasonsList;
	private double boxOffice;
	private boolean currentlyInTheaters;
	private Date nextAirDate;
	private String poster_path;
	@ManyToOne(targetEntity=Celebrity.class)
	private Celebrity writer;
	private String runtime;
	private String studio;

	public Content() {
	}
	
	public Content(int content_id, ContentType type, String title, String genre, Date date, double userRating, List<UserReview> userReview, double criticRating, List<CriticReview> criticReview, String description, Celebrity director, URI trailerPath, List<TVShowSeason> seasonsList, double boxOffice, boolean currentlyInTheaters, Date nextAirDate) {
		this.content_id = content_id;
		this.type = type;
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
		this.seasonsList = seasonsList;
		this.boxOffice = boxOffice;
		this.currentlyInTheaters = currentlyInTheaters;
		this.nextAirDate = nextAirDate;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public URI getTrailerPath() {
		return trailerPath;
	}

	public void setTrailerPath(URI trailerPath) {
		this.trailerPath = trailerPath;
	}

	public List<TVShowSeason> getSeasonsList() {
		return seasonsList;
	}

	public void setSeasonsList(List<TVShowSeason> seasonsList) {
		this.seasonsList = seasonsList;
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

	public Date getNextAirDate() {
		return nextAirDate;
	}

	public void setNextAirDate(Date nextAirDate) {
		this.nextAirDate = nextAirDate;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Content other = (Content) obj;
		if (this.content_id != other.content_id) {
			return false;
		}
		return true;
	}

	
	
}