package main.java.controllers;

import java.util.List;
import java.util.Optional;
import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class HomeController {
	
	@Autowired
	private ContentManager contentManager;
	    
	@RequestMapping("/api/topboxoffice")
	public List displayBoxOffice() {
		System.out.println("entered");
		List<Content> boxOfficeList = contentManager.findTop10ByCurrentlyInTheatersTrueOrderByBoxOffice();
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