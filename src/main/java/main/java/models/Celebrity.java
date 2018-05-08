package main.java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import main.java.dto.ContentDTO;

@Entity
public class Celebrity {

	@Id
	private int celeb_id;
	@Column(length = 4096)
	private String description;
	private String name;
	private LocalDate birthday;
	private String birthplace;
	private String picture;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "filmography",
	joinColumns = @JoinColumn(name = "celeb_id"),
	inverseJoinColumns = @JoinColumn(name = "content_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames={"celeb_id", "content_id"})})
	private Set<Content> filmography;

	public Celebrity() {
	}
	
	public Celebrity(int celeb_id, String description, String name, LocalDate birthday, String birthplace, String picture, Set<Content> filmography) {
		this.celeb_id = celeb_id;
		this.description = description;
		this.name = name;
		this.birthday = birthday;
		this.birthplace = birthplace;
		this.picture = picture;
		this.filmography = filmography;
	}
	
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
	
	public Set<Content> getFilmography() {
		return filmography;
	}

	public void setFilmography(Set<Content> filmography) {
		this.filmography = filmography;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@JsonIgnore
	public List<ContentDTO> getFilmographyDTO() {
		ArrayList<ContentDTO> filmographyDTO = new ArrayList<>();
		for (Content c:this.filmography) {
			filmographyDTO.add(new ContentDTO(c.getId(), c.getTitle(), c.getDate(),
				c.getUserRating(), c.getCriticRating(), c instanceof Movie));
		}
		
		return filmographyDTO;
	}
	
}
