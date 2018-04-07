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

}