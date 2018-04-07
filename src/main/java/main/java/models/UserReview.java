package main.java.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserReview extends Review {
	@Id
	private int user_review_id;
}