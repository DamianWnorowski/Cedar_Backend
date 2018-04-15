package main.java.models;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Article {
	@Id
	private int article_id;
	private String description;
	private String author;
	private String body;
	@OneToMany(targetEntity=Content.class, mappedBy="content_id")
	private List<Content> contentList;
	private LocalDate postDate;
//	private List<Image> thumbnails;
//	private List<URI> videos;

}