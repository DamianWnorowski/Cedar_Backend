package main.java.server;

import main.java.models.Movies;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:3000")
@RestController
public class MovieController {
    @RequestMapping("/movie")
    public Movies movies(@RequestParam(value="id") int id) {
        //search db for movie id and return the movie details
        String overview = "When her father unexpectedly passes away, young Ella finds herself at the mercy of her cruel stepmother and her daughters. Never one to give up hope, Ella's fortunes begin to change after meeting a dashing stranger in the woods.";
        System.out.println("accessed");
        return new Movies(id, "Cinderalla", "/o1F2aloaOUufHHOsV0laA9aw9N0.jpg", overview, "2015-03-12", "Frankster", "120min", "$207 million", "Disney", "Animated", "Steam");
    }
}