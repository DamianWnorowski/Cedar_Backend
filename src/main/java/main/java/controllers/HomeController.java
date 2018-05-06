package main.java.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import main.java.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import properties.PropertiesManager;
import main.java.models.Movie;
import main.java.managers.ContentManager;

@CrossOrigin("http://localhost:3000")
@RestController
public class HomeController {
	
	@Autowired
	private ContentManager movieManager;
	private final PropertiesManager propertiesManager;

	public HomeController() {
            this.propertiesManager = PropertiesManager.getManager();
	}
	
	@GetMapping("/api/topboxoffice")
	public List<Movie> displayBoxOffice() {
		List<Movie> boxOfficeList
			= movieManager.findTop10ByCurrentlyInTheatersTrueOrderByBoxOffice();
		return boxOfficeList;
	}

	@GetMapping("/api/moviesopeningthisweek")
	public List<Movie> displayMoviesOpeningThisWeek() {
		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
		int currentDayOfWeekValue = currentDayOfWeek.getValue();
		if (currentDayOfWeek == DayOfWeek.SUNDAY) {
			currentDayOfWeekValue = propertiesManager.getProperty("firstDayOfWeekIndex");
		}
		/* The +1 is to account for zero-indexing of days */
		int daysToSubtract = currentDayOfWeekValue + 1;
		int daysToAdd = propertiesManager.getProperty("numDaysInWeek") - currentDayOfWeekValue;
		LocalDate lastDayOfLastWeek = LocalDate.now().minusDays(daysToSubtract);
		LocalDate firstDayOfNextWeek = LocalDate.now().plusDays(daysToAdd);
		List<Movie> moviesForThisWeek
			= movieManager.findTop10ByDateAfterAndDateBefore(lastDayOfLastWeek, firstDayOfNextWeek);
		return moviesForThisWeek;
	}

	@GetMapping("/api/comingsoontotheaters")
	public List<Movie> displayComingSoonToTheaters() {
		LocalDate endDate
			= LocalDate.now().plusWeeks(propertiesManager.getProperty("numWeeksForComingSoon"));
		List<Movie> moviesComingSoon
			= movieManager.findTop10ByDateAfterAndDateBefore(LocalDate.now(), endDate);
		return moviesComingSoon;
	}
	
	@GetMapping("/api/featuredmovie")
	public Content getFeaturedMovie() {
		return null;
	}

	public String displayNewTVTonight() {
		return null;
	}

	public String displayPopularTV() {
		return null;
	}

}