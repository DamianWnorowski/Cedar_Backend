package main.java.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.java.managers.CelebrityManager;
import main.java.managers.TVManager;
import main.java.models.Celebrity;
import main.java.models.Movie;
import main.java.models.TVShow;
import main.java.managers.ContentManager;

public class SearchService {

	private static SearchService instance;

	private ContentManager movieManager;
	private CelebrityManager celebrityManager;
	private TVManager tvManager;

	private SearchService(ContentManager movieManager, CelebrityManager celebrityManager, TVManager tvManager) {
		this.movieManager = movieManager;
		this.celebrityManager = celebrityManager;
		this.tvManager = tvManager;
		instance = this;
	}

	public ArrayList<Movie> searchMovies(String query) {
		Set<Movie> movies = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for (String token : tokens) {
			List<Movie> movie_results = movieManager.findByTitleContainingIgnoreCase(token);
			if (movie_results != null) {
				movies.addAll(movie_results);
			}
		}
		ArrayList<Movie> moviesToOrder = new ArrayList<Movie>(movies);
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<ArrayList> moviesAndRanks = new ArrayList<ArrayList>();
		for(Movie movie : moviesToOrder){
			int rank = calculateRank(movie, tokens);
			ranks.add(rank);
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(movie);
			temp.add((Integer)rank);
			moviesAndRanks.add(temp);
		}
		
		
		Comparator<ArrayList> rankSorter = new Comparator<ArrayList>() {
			@Override
			public int compare(ArrayList left, ArrayList right) {
				return (Integer) right.get(1) - (Integer) left.get(1); // use your logic
			}
		};
		
		Collections.sort(moviesAndRanks, rankSorter);
		
		ArrayList<Movie> orderedMovies = new ArrayList<Movie>();
		for(ArrayList lists : moviesAndRanks){
			orderedMovies.add((Movie)lists.get(0));
		}
		return orderedMovies;
	}

	public ArrayList searchCelebrities(String query) {
		Set<Celebrity> celebrities = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for (String token : tokens) {
			List<Celebrity> celebrity_results = celebrityManager.findByNameContainingIgnoreCase(token);
			if (celebrity_results != null) {
				celebrities.addAll(celebrity_results);
			}
		}
		ArrayList<Celebrity> celebsToOrder = new ArrayList<Celebrity>(celebrities);
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<ArrayList> celebsAndRanks = new ArrayList<ArrayList>();
		for(Celebrity celebrity : celebsToOrder){
			int rank = calculateRank(celebrity, tokens);
			ranks.add(rank);
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(celebrity);
			temp.add((Integer)rank);
			celebsAndRanks.add(temp);
		}
		
		
		Comparator<ArrayList> rankSorter = new Comparator<ArrayList>() {
			@Override
			public int compare(ArrayList left, ArrayList right) {
				return (Integer) right.get(1) - (Integer) left.get(1); // use your logic
			}
		};
		
		Collections.sort(celebsAndRanks, rankSorter);
		
		ArrayList<Celebrity> orderedCelebrities = new ArrayList<Celebrity>();
		for(ArrayList lists : celebsAndRanks){
			orderedCelebrities.add((Celebrity)lists.get(0));
		}
		return orderedCelebrities;
	}

	public ArrayList searchTVShows(String query) {
		Set<TVShow> shows = new HashSet();
		ArrayList<String> tokens = tokenizeQuery(query);
		for (String token : tokens) {
			List<TVShow> show_results = tvManager.findByTitleContainingIgnoreCase(token);
			if (show_results != null) {
				shows.addAll(show_results);
			}
		}
		ArrayList<TVShow> showsToOrder = new ArrayList<TVShow>(shows);
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<ArrayList> showsAndRanks = new ArrayList<ArrayList>();
		for(TVShow show : showsToOrder){
			int rank = calculateRank(show, tokens);
			ranks.add(rank);
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(show);
			temp.add((Integer)rank);
			showsAndRanks.add(temp);
		}
		
		
		Comparator<ArrayList> rankSorter = new Comparator<ArrayList>() {
			@Override
			public int compare(ArrayList left, ArrayList right) {
				return (Integer) right.get(1) - (Integer) left.get(1); // use your logic
			}
		};
		
		Collections.sort(showsAndRanks, rankSorter);
		
		ArrayList<TVShow> orderedShows = new ArrayList<TVShow>();
		for(ArrayList lists : showsAndRanks){
			orderedShows.add((TVShow)lists.get(0));
		}
		return orderedShows;
	}

	public ArrayList<String> tokenizeQuery(String query) {
		ArrayList<String> tokens = new ArrayList();
		tokens.addAll(Arrays.asList(query.split(" ")));
		return tokens;
	}

	public static SearchService getService(ContentManager movieManager, CelebrityManager celebrityManager, TVManager tvManager) {
		if (instance == null) {
			instance = new SearchService(movieManager, celebrityManager, tvManager);
		}
		return instance;
	}

	public int calculateRank(Movie m, ArrayList<String> tokens) {
		int rank = 0;
		String title = m.getTitle();
		for(String token : tokens) {
			if (title.toLowerCase().contains(token.toLowerCase())) {
				rank += 1;
			}
		}
		if (m.getPoster_path()== null){
			rank = -1;
		}
		System.out.println("title: "+title+", rank: "+ rank);
		return rank;
	}
	public int calculateRank(Celebrity m, ArrayList<String> tokens) {
		int rank = 0;
		String title = m.getName();
		for(String token : tokens) {
			if (title.toLowerCase().contains(token.toLowerCase())) {
				rank += 1;
			}
		}
		if (m.getPicture() == null){
			rank = -1;
		}
		System.out.println("title: "+title+", rank: "+ rank);
		return rank;
	}
	public int calculateRank(TVShow m, ArrayList<String> tokens) {
		int rank = 0;
		String title = m.getTitle();
		for(String token : tokens) {
			if (title.toLowerCase().contains(token.toLowerCase())) {
				rank += 1;
			}
		}
		if (m.getPoster_path() == null){
			rank = -1;
		}
		System.out.println("title: "+title+", rank: "+ rank);
		return rank;
	}
}
