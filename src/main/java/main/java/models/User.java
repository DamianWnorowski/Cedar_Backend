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

}