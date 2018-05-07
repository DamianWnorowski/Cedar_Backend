package main.java.models;

import main.java.dto.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	@ManyToMany
	@JoinTable(name = "users_movie_watchlist",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "movie_watchlist_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "movie_watchlist_id"})})
    private Set<Movie> movieWatchlist;
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "users_television_watchlist",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "television_watchlist_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "television_watchlist_id"})})
    private Set<TVShow> televisionWatchlist;
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "users_blacklist",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "blacklist_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "blacklist_id"})})
    private Set<Content> blacklist;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @JsonIgnore
	@ManyToMany
	@JoinTable(name = "users_following",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "following_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "following_id"})})
    private Set<User> following;

    @JsonIgnore
    @OneToMany(targetEntity = Review.class, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
    @JsonIgnore
    private int profileViews;
	
	@JsonIgnore
    @OneToMany(targetEntity = ReviewReport.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewReport> reports;

	@JsonIgnore
    @OneToOne(targetEntity = PwResetToken.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private PwResetToken pwResetToken;


    public User() {
        this.roles = new ArrayList<>();
    }

    public User(int id) {
        this.id = id;
    }

    public PwResetToken getPwResetToken() {
        return pwResetToken;
    }

    public void setPwResetToken(PwResetToken pwResetToken) {
        this.pwResetToken = pwResetToken;
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

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public ErrorCode addToWatchlist(Content content) {
		if (content instanceof Movie) {
			if (movieWatchlist.contains((Movie)content)) {
				return ErrorCode.ALREADYINLIST;
			}
			movieWatchlist.add((Movie)content);
		}
		else if (content instanceof TVShow) {
			if (televisionWatchlist.contains((TVShow)content)) {
				return ErrorCode.ALREADYINLIST;
			}
			televisionWatchlist.add((TVShow)content);
		}
		return ErrorCode.SUCCESS;
	}

    public ErrorCode addToBlacklist(Content content) {
		if (blacklist.contains(content)) {
			return ErrorCode.ALREADYINLIST;
		}
		blacklist.add(content);
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode removeFromWatchlist(Content theContent) {
		if (theContent instanceof Movie) {
			if (!movieWatchlist.contains((Movie)theContent)) {
				return ErrorCode.NOTINLIST;
			}
			movieWatchlist.remove((Movie)theContent);
		}
		if (theContent instanceof TVShow) {
			if (!televisionWatchlist.contains((TVShow)theContent)) {
				return ErrorCode.NOTINLIST;
			}
			televisionWatchlist.remove((TVShow)theContent);
		}
        return ErrorCode.SUCCESS;
    }


	public ErrorCode removeFromBlacklist(Content theContent) {
		if (!blacklist.contains(theContent)) {
			return ErrorCode.NOTINLIST;
		}
		blacklist.remove(theContent);
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

	public Set<Movie> getMovieWatchlist() {
		return movieWatchlist;
	}

	public void setMovieWatchlist(Set<Movie> movieWatchlist) {
		this.movieWatchlist = movieWatchlist;
	}

    public Set<TVShow> getTelevisionWatchlist() {
        return televisionWatchlist;
    }

    public void setTelevisionWatchlist(Set<TVShow> televisionWatchlist) {
        this.televisionWatchlist = televisionWatchlist;
    }

    public Set<Content> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Set<Content> blacklist) {
        this.blacklist = blacklist;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getProfileViews() {
        return profileViews;
    }

    public void setProfileViews(int profileViews) {
        this.profileViews = profileViews;
    }

	public List<ReviewReport> getReports() {
		return reports;
	}

	public void setReports(List<ReviewReport> reports) {
		this.reports = reports;
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

    public void addProfileView() {
        profileViews++;
    }

}
