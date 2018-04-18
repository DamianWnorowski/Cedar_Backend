package main.java.controllers;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import main.java.managers.MovieManager;
import main.java.models.Content;
import main.java.models.CriticReview;
import main.java.models.Movie;
import main.java.models.Review;
import main.java.models.ReviewForm;
import main.java.models.User;
import main.java.models.UserReview;
import main.java.models.UserRole;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private MovieManager movieManager;
	
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
	
	@PostMapping("/api/postrating")
	public Integer rateContent(@RequestBody ReviewForm form) {
		User postingUser = new User(1, UserRole.USER);
		Movie onlySpongebobForNow = movieManager.findById(2).get();
		Review reviewToPost;
		if (postingUser.getRole() == UserRole.CRITIC) { // review id is hardcoded to 1 for now
			reviewToPost = new CriticReview(null, 1, onlySpongebobForNow, postingUser, form.getRating(), LocalDate.now(), form.getBody());
		}
		else {
			reviewToPost = new UserReview(1, onlySpongebobForNow, postingUser, form.getRating(), LocalDate.now(), form.getBody());
		}
		
		onlySpongebobForNow.addReview(reviewToPost);
		int status = onlySpongebobForNow.calculateRatings();
		Movie editedMovie = movieManager.save(onlySpongebobForNow);
		if (editedMovie == null)
			return -1;
		return status;
	}

}