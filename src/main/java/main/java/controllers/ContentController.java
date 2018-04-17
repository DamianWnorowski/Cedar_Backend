package main.java.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import main.java.managers.MovieManager;
import main.java.models.Movie;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private MovieManager movieManager;

    @GetMapping("/movie")
    public Movie getMovieInfo(@RequestParam(value="id") int id) {
        try {
        	Movie theMovie = movieManager.findById(id).get();
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}

       return null;
    }
}