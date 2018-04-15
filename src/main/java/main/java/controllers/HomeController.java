package main.java.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class HomeController {
	
	@Autowired
	private ContentManager contentManager;
	    
	@GetMapping("/api/topboxoffice")
	public List<Content> displayBoxOffice() {
		List<Content> boxOfficeList = contentManager.findTop10ByCurrentlyInTheatersTrueOrderByBoxOffice();
		return boxOfficeList;
	}

	@GetMapping("/api/moviesopeningthisweek")
	public List<Content> displayMoviesOpeningThisWeek() {
		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
		int currentDayOfWeekValue = currentDayOfWeek.getValue();
		if (currentDayOfWeek == DayOfWeek.SUNDAY) // Sunday is the first day of the week
			currentDayOfWeekValue = 0; // magic number to change
		int daysToSubtract = currentDayOfWeekValue + 1; // the 1 is to account for zero-indexing of days
		int daysToAdd = 7 - currentDayOfWeekValue; // 7 is magic number to change
		LocalDate lastDayOfLastWeek = LocalDate.now().minusDays(daysToSubtract);
		LocalDate firstDayOfNextWeek = LocalDate.now().plusDays(daysToAdd);
		List<Content> moviesForThisWeek = contentManager.findTop10ByDateAfterAndDateBefore(lastDayOfLastWeek, firstDayOfNextWeek);
		return moviesForThisWeek;
	}

	@GetMapping("/api/featuredmovie")
	public Content getFeaturedMovie() {
		return null;
	}

	@GetMapping("/api/comingsoontotheaters")
	public List<Content> displayComingSoonToTheaters() {
		return null;
	}

	public String displayNewTVTonight() {
		return null;
	}

	public String displayPopularTV() {
		return null;
	}

}