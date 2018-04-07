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

	public List<Double> addReview(int contentID, double rating, String body, int userID) {
		return null;
	}

}