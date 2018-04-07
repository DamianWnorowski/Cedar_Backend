package main.java.controllers;

import java.util.Optional;
import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000/api")
@RestController
public class HomeController {
	
	@Autowired
	private ContentManager contentManager;
	    
	@RequestMapping("/topboxoffice")
	public Optional displayBoxOffice() {
		System.out.println("entered");
		Content theMovie = contentManager.findById(2).get();
		theMovie.setCurrentlyInTheaters(true);
		contentManager.save(theMovie);
		System.out.println("saved");
		Optional<Content> boxOfficeList = contentManager.currentlyInTheatersTrue();
		return boxOfficeList;
	}

	public String displayMoviesOpeningThisWeek() {
		return null;
	}

	public String getFeaturedMovie() {
		return null;
	}

	public String displayComingSoonToTheaters() {
		return null;
	}

	public String displayNewTVTonight() {
		return null;
	}

	public String displayPopularTV() {
		return null;
	}

}