package main.java.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.managers.CelebrityManager;
import main.java.managers.MovieManager;
import main.java.managers.TVManager;
import main.java.models.Celebrity;
import main.java.models.Movie;
import main.java.models.TVShow;

public class SearchService {
	private static SearchService instance;
	
	private MovieManager movieManager;
	private CelebrityManager celebrityManager;
	private TVManager tvManager;
	
	private SearchService(MovieManager movieManager, CelebrityManager celebrityManager, TVManager tvManager) {
		this.movieManager = movieManager;
		this.celebrityManager = celebrityManager;
		this.tvManager = tvManager;
		instance = this;
	}
	
	public Set searchMovies(String query) {
		Set<Movie> movies = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for(String token : tokens) {
			List<Movie> movie_results = movieManager.findByTitleContainingIgnoreCase(token);
			if(movie_results != null){
				movies.addAll(movie_results);
			}
		}
		return movies;
	}
	
	public Set searchCelebrities(String query) {
		Set<Celebrity> celebrities = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for(String token : tokens) {
			List<Celebrity> celebrity_results = celebrityManager.findByNameContainingIgnoreCase(token);
			if(celebrity_results != null){
				celebrities.addAll(celebrity_results);
			}
		}
		return celebrities;
	}
	
	public Set searchTVShows(String query) {
		Set<TVShow> shows = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for(String token : tokens) {
			List<TVShow> show_results = tvManager.findByTitleContainingIgnoreCase(token);
			if(show_results != null){
				shows.addAll(show_results);
			}
		}
		return shows;
	}
	
	
	
	
	public ArrayList<String> tokenizeQuery(String query) {
		ArrayList<String> tokens = new ArrayList();
		tokens.addAll(Arrays.asList(query.split(" ")));
		return tokens;
	}
	
	public static SearchService getService(MovieManager movieManager, CelebrityManager celebrityManager, TVManager tvManager) {
		if (instance == null) {
			instance = new SearchService(movieManager, celebrityManager, tvManager);
		}
		return instance;
	}
}
