package main.java.controllers;

import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private ContentManager contentManager;

    @GetMapping("/movie")
    public Content getMovieInfo(@RequestParam(value="id") int id) {
        try {
        	Content theMovie = contentManager.findById(id).get();
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}

       return null;
    }
}