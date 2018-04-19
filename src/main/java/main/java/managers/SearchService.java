/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.managers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;


public class SearchService {
	@Autowired
	private MovieManager movieManager;
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
}
