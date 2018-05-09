package main.java.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ad {

	@Id
	private int id;
	private String imagePath;

	public Ad() {
	}

	public Ad(int id, String imagePath) {
		this.id = id;
		this.imagePath = imagePath;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

}