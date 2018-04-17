package main.java.models;

import main.java.managers.CelebrityManager;
import java.util.List;
import main.java.managers.MovieManager;

public class SearchResults {

	private transient MovieManager cm;
	private transient CelebrityManager celebManager;
	private List<Content> movieResults;
	private List<Celebrity> celebrityResults;
	private List<Content> tvShowResults;

	public int performSearch(String query) {
		return 0;
	}

}