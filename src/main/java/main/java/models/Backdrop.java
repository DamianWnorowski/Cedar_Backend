package main.java.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Backdrop {
	
	@Id
	private Integer id;
	private String imageLocation;
	@OneToOne
	private Content content;

	public Backdrop() {
	}
	
	public Backdrop(Integer id, String imageLocation, Content content) {
		this.id = id;
		this.imageLocation = imageLocation;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
}
