package main.java.models;

import java.awt.Image;
import java.net.URI;
import java.util.Date;
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
	private Date postDate;
//	private List<Image> thumbnails;
//	private List<URI> videos;

}