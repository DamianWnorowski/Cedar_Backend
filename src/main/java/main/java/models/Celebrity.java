package main.java.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Celebrity {

	@Id
	private int celeb_id;
	private String description;
	private String name;
	@ManyToOne(targetEntity=Content.class)
	private Content highestRated;
	@ManyToOne(targetEntity=Content.class)
	private Content lowestRated;
//	@ManyToMany(targetEntity=Content.class, mappedBy="content_id") I'm unsure how to do this
//	private List<Content> filmography;

	public int getCeleb_id() {
		return celeb_id;
	}

	public void setCeleb_id(int celeb_id) {
		this.celeb_id = celeb_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Content getHighestRated() {
		return highestRated;
	}

	public void setHighestRated(Content highestRated) {
		this.highestRated = highestRated;
	}

	public Content getLowestRated() {
		return lowestRated;
	}

	public void setLowestRated(Content lowestRated) {
		this.lowestRated = lowestRated;
	}
	
}

