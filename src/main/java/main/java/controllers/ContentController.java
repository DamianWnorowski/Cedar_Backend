package main.java.controllers;

import main.java.models.Movies;
import main.java.managers.ContentManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private ContentManager contentManager;

    @RequestMapping("/movie")
    public Movies movies(@RequestParam(value="id") int id) {
    	//String overview = "When her father unexpectedly passes away, young Ella finds herself at the mercy of her cruel stepmother and her daughters. Never one to give up hope, Ella's fortunes begin to change after meeting a dashing stranger in the woods.";
        //Movies toSave = new Movies(id, "Cinderella", "/o1F2aloaOUufHHOsV0laA9aw9N0.jpg", overview, "2015-03-12", "Frankster", "120min", "$207 million", "Disney", "Animated", "Steam");
        //search db for movie id and return the movie details
        //contentManager.save(toSave);
        System.out.println("Id is " + id);
        try {
        	Movies theMovie = contentManager.findById(id).get();
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}
       return null;
    }
}