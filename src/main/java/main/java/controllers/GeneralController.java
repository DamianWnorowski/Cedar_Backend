package main.java.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.java.managers.AdManager;
import main.java.managers.CelebrityManager;
import main.java.managers.TVManager;
import main.java.models.Celebrity;
import main.java.services.SearchService;
import main.java.models.Movie;
import main.java.models.TVShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import main.java.managers.ContentManager;
import main.java.managers.UserManager;
import main.java.models.Ad;

@CrossOrigin("http://localhost:3000")
@RestController
public class GeneralController {

    @Autowired
    private ContentManager movieManager;
    @Autowired
    private CelebrityManager celebrityManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private TVManager tvManager;
	@Autowired
	private AdManager adManager;

    @GetMapping("/api/search")
    public Map search(@RequestParam(value = "search") String search) {
        try {
            SearchService searchService = SearchService.getService(movieManager, celebrityManager, tvManager);
            Map<String, ArrayList> results = new HashMap();
            ArrayList<Movie> movies = searchService.searchMovies(search);
            ArrayList<Celebrity> celebrities = searchService.searchCelebrities(search);
            ArrayList<TVShow> shows = searchService.searchTVShows(search);
            results.put("movies", movies);
            results.put("celebrities", celebrities);
            results.put("tvshows", shows);
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("search failed");
        }
        return null;
    }
	
	@GetMapping("/api/getad")
    public Ad search(@RequestParam(value = "id") int id) {
        try {
			return adManager.findById(id).get();
        }
		catch (Exception e) {
            return null;
        }
       
    }

}
