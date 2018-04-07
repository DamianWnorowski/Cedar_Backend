package main.java.managers;

import java.util.Optional;
import main.java.models.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentManager extends CrudRepository<Content, Integer> {
	
	public Optional<Content> currentlyInTheatersTrue();
/*
	private Content featuredMovie;
	private List<Content> mostPopularShows;



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

	public List<Content> findByDateAfterAndDateBefore(Date start, Date end) {
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

}
*/
}