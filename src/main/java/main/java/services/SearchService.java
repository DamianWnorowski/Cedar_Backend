package main.java.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.managers.MovieManager;
import main.java.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchService {
	private static SearchService instance;
	
//	@Autowired
	private MovieManager movieManager;
	
	private SearchService(MovieManager movieManager) {
		this.movieManager = movieManager;
		instance = this;
	}
	
	public Set searchMovies(String query){
		Set<Movie> movies = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for(String token : tokens){
			List<Movie> movie_results = movieManager.findByTitleContainingIgnoreCase(token);
			if(movie_results != null){
				movies.addAll(movie_results);
			}
		}
		return movies;
	}
	
	public ArrayList<String> tokenizeQuery(String query){
		ArrayList<String> tokens = new ArrayList();
		tokens.addAll(Arrays.asList(query.split(" ")));
		return tokens;
	}
	
	public static SearchService getService(MovieManager movieManager) {
		if (instance == null)
			instance = new SearchService(movieManager);
		return instance;
	}
}
