package main.java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
	@JsonIgnore
    private String password;
	@JsonIgnore
    private String email;
	@JsonIgnore
    private boolean verified;
    private boolean visible;
	@JsonIgnore
	@ElementCollection
    //@OneToMany(targetEntity = Content.class, mappedBy = "id")
    private List<Content> movieWatchlist;
	@JsonIgnore
	@ElementCollection
    //@OneToMany(targetEntity = TVShowSeason.class, mappedBy = "season_id")
    private List<TVShowSeason> televisionWatchlist;
	@JsonIgnore
	@ElementCollection
    private List<Content> blacklist;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @ElementCollection
	@JsonIgnore
    private List<User> following;
	@JsonIgnore
	@OneToMany(targetEntity=UserReview.class, mappedBy="review_id")
	private List<Review> reviews;

    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role.name());
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public ErrorCode addToMovieWatchlist(Movie movie) {
		if (movieWatchlist.contains(movie)) {
			return ErrorCode.ALREADYINLIST;
		}
		movieWatchlist.add(movie);
        return ErrorCode.SUCCESS;
    }

    public ErrorCode addToMovieBlacklist(Content content) {
		if (blacklist.contains(content)) {
			return ErrorCode.ALREADYINLIST;
		}
		blacklist.add(content);
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode addReview(Review review) {
		reviews.add(review);
		return ErrorCode.SUCCESS;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Content> getMovieWatchlist() {
        return movieWatchlist;
    }

    public void setMovieWatchlist(List<Content> movieWatchlist) {
        this.movieWatchlist = movieWatchlist;
    }

    public List<TVShowSeason> getTelevisionWatchlist() {
        return televisionWatchlist;
    }

    public void setTelevisionWatchlist(List<TVShowSeason> televisionWatchlist) {
        this.televisionWatchlist = televisionWatchlist;
    }

	public List<Content> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<Content> blacklist) {
		this.blacklist = blacklist;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
