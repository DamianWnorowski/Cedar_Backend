package main.java.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Episode {
	@Id
	private int episode_id;
	private String name;
}
