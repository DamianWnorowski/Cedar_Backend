package main.java.controllers;

import java.util.List;
import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class HomeController {
	
	@Autowired
	private ContentManager contentManager;
	    
	@GetMapping("/api/topboxoffice")
	public List<Content> displayBoxOffice() {
		List<Content> boxOfficeList = contentManager.findTop10ByCurrentlyInTheatersTrueOrderByBoxOffice();
		return boxOfficeList;
	}

	@GetMapping("/api/moviesopeningthisweek")
	public String displayMoviesOpeningThisWeek() {
		return null;
	}

	@GetMapping("/api/featuredmovie")
	public Content getFeaturedMovie() {
		return null;
	}

	@GetMapping("/api/comingsoontotheaters")
	public List<Content> displayComingSoonToTheaters() {
		return null;
	}

	public String displayNewTVTonight() {
		return null;
	}

	public String displayPopularTV() {
		return null;
	}

}