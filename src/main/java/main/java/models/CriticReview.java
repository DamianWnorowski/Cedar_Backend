package main.java.models;

import java.net.URI;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CriticReview extends Review {

	@Id
	private int critic_review_id;
	private URI sourceUrl;

}