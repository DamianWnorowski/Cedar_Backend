package main.java.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private int id;
	private UserRole role;
	private String name;
	private transient String password;
	private String email;
	@OneToMany(targetEntity=Content.class, mappedBy="content_id")
	private List<Content> movieWatchlist;
	@OneToMany(targetEntity=TVShowSeason.class, mappedBy="season_id")
	private List<TVShowSeason> televisionWatchlist;

	public int addToWatchlist(int contentID) {
		return 0;
	}

	public int addToBlacklist(int contentID) {
		return 0;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
        
        

}