package main.java.models;

import java.net.URI;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CriticReview extends Review {
	
	private URI sourceUrl;

}