package main.java.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

	@Id
	protected int review_id;
	protected int contentId;
	@OneToOne
	protected User author;
	protected Double rating;
	protected Date date;
	protected String body;

}