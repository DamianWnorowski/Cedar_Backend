package main.java.models;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TVShowSeason {
	@Id
	private int season_id;
	private String description;
	private String title;
	private Date date;
	@OneToMany(targetEntity=Episode.class, mappedBy="episode_id")
	private List<Episode> episodes;

}