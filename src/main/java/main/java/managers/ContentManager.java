package main.java.managers;

import java.time.LocalDate;
import java.util.List;
import main.java.models.Content;
import main.java.models.Movie;
import main.java.models.TVShow;
import org.springframework.data.repository.CrudRepository;

public interface ContentManager extends CrudRepository<Content, Integer> {
	
	public List<Movie> findTop10ByCurrentlyInTheatersTrueOrderByBoxOffice();
	public List<Movie> findTop10ByDateAfterAndDateBefore(LocalDate start, LocalDate end);
	public List<Movie> findByTitleContainingIgnoreCase(String token);
	public List<Movie> findTop10ByOrderByCriticRatingDesc();
	public List<TVShow> findTop10ByNextAirDate(LocalDate date);
	
	/*
	public List<Content> findTop4ByCelebrityOrderByCriticRating(Celebrity c);
	public List<Content> findByCelebrity(Celebrity c);
	
	public List<Content> getMoviesOpeningThisWeek() {
		return null;
	}

	public List<Content> searchForTVShows(String query) {
		return null;
	}

	public List<Content> searchForMovies(String query) {
		return null;
	}

	public Content getFeaturedMovie() {
		return null;
	}

	public List<Content> findTop10ByNextAirDate(Date currentDate) {
		return null;
	}

	public List<Content> getPopularTV() {
		return null;
	}

	public List<Content>  getRecommendations(int contentID) {
		return null;
	}

	*/
}