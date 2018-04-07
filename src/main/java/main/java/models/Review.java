package main.java.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

public abstract class Review {

	@Id
	protected int id;
	protected int contentId;
	protected User author;
	protected Double rating;
	protected Date date;
	protected String body;

}