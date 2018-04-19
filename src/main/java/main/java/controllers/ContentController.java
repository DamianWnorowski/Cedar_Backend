package main.java.controllers;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import main.java.managers.MovieManager;
import main.java.models.CriticReview;
import main.java.models.Movie;
import main.java.models.Review;
import main.java.models.ReviewForm;
import main.java.models.User;
import main.java.models.UserReview;
import main.java.models.UserRole;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import main.java.managers.ReviewManager;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private MovieManager movieManager;
	@Autowired
	ReviewManager reviewManager;
	
    @GetMapping("/movie")
    public Movie getMovieInfo(@RequestParam(value="id") int id) {
        try {
        	Movie theMovie = movieManager.findById(id).get();
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}

       return null;
    }
	
	@PostMapping("/api/ratecontent")
	public Integer rateContent(@RequestBody ReviewForm form) {
		System.out.println("\n\nEntered rate content\n\n" + form.getBody());
		User postingUser = new User(10, UserRole.USER);
		Movie movieToRate = movieManager.findById(form.getContent_id()).get();
		Review reviewToPost;
		if (postingUser.getRole() == UserRole.CRITIC) {
			reviewToPost = new CriticReview(null, movieToRate, postingUser, form.getRating(), LocalDate.now(), form.getBody());
			
		}
		else {
			reviewToPost = new UserReview(movieToRate, postingUser, form.getRating(), LocalDate.now(), form.getBody());
		}
		reviewManager.save(reviewToPost);
		
		movieToRate.addReview(reviewToPost);
		int status = movieToRate.calculateRatings();
		Movie editedMovie = movieManager.save(movieToRate);
		System.out.println("probably saved");
		if (editedMovie == null)
			return -1;
		return status;
	}

}