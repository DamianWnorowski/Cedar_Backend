package main.java.models;

import java.awt.Image;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

public class Ad {

	private int id;
	private String name;
	private String contactEmail;
	private AdType type;
	private Image image;
	private Date startDate;
	private Date endDate;

}