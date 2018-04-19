package main.java.controllers;

import java.util.Set;
import main.java.managers.MovieManager;
import main.java.services.SearchService;
import main.java.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class GeneralController {
    @Autowired
	private MovieManager movieManager;
	
	@GetMapping("/api/search")
    public Set search(@RequestParam(value="search") String search) {
        try {
			SearchService searchService = SearchService.getService(movieManager);
        	Set<Movie> movies = searchService.searchMovies(search);
        	return movies;
    	}
    	catch (Exception e) {
			e.printStackTrace();
    		System.out.println("search failed");
    	}

       return null;
    }
	
	public String displayNewsAndInterviews() {
		return null;
	}

	public String viewAboutUsInfo() {
		return null;
	}

	public String viewProfilePage(int userID) {
		return null;
	}

}