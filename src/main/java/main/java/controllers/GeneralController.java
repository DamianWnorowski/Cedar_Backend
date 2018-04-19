package main.java.controllers;

import java.util.Set;
import main.java.managers.SearchService;
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
	private SearchService searchService;
	
    @GetMapping("/search")
    public Set getMovieInfo(@RequestParam(value="query") String query) {
        try {
        	Set<Movie> movies = searchService.searchMovies(query);
        	return movies;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
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