package main.java.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import main.java.managers.MovieManager;
import main.java.models.CriticReview;
import main.java.models.Review;
import main.java.models.ReviewForm;
import main.java.models.User;
import main.java.models.UserReview;
import main.java.models.UserRole;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import main.java.managers.ReviewManager;
import main.java.managers.TVManager;
import main.java.managers.UserManager;
import main.java.models.ErrorCode;
import main.java.models.Movie;
import main.java.models.TVShow;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	MovieManager movieManager;
	@Autowired
	ReviewManager reviewManager;
	@Autowired
	UserManager userManager;
	@Autowired
	TVManager tvManager;

	
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
	
	@GetMapping("/show")
    public TVShow getTVShowInfo(@RequestParam(value="id") int id) {
        try {
            TVShow show = tvManager.findById(id).get();
            return show;
        }
    	catch (Exception e) {
            System.out.println("can't get show");
    	}

       return null;
    }
	
	@PostMapping("/api/ratecontent")
	public ErrorCode rateContent(@RequestBody ReviewForm form) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User postingUser = userManager.findByEmail(email);
		Movie movieToRate = movieManager.findById(form.getContent_id()).get();
		Review reviewToPost;
		
		if (form.getRating() == 0) {
			form.setRating(1);
		}
		
		if (form.getRating() < 1 || form.getRating() > 5) {
			return ErrorCode.INVALIDRATING;
		}
		
		if (postingUser.hasRole(UserRole.ROLE_CRITIC)) {
			reviewToPost = new CriticReview(null, movieToRate, postingUser,
				form.getRating(), LocalDate.now(), form.getBody());
		}
		else {
			reviewToPost = new UserReview(movieToRate, postingUser,
				form.getRating(), LocalDate.now(), form.getBody());
		}
		reviewManager.save(reviewToPost);
		movieToRate.addReview(reviewToPost);
		ErrorCode status = movieToRate.calculateRatings();
		Movie editedMovie = movieManager.save(movieToRate);
		if (editedMovie == null) {
			return ErrorCode.DATABASEERROR;
		}
		return status;
	}
	
	
	@PostMapping("/api/editreview")
	public ErrorCode editReview(@RequestParam(value="id") int id, @RequestBody ReviewForm form) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		Review reviewToEdit;
		try {
			reviewToEdit = reviewManager.findById(id).get();
		}
		catch (NoSuchElementException e) {
			System.out.println("failed");
			return ErrorCode.DOESNOTEXIST;
		}
	
		if (!currentUser.hasRole(UserRole.ROLE_ADMIN) && 
			!reviewToEdit.getAuthor().equals(currentUser)) {
			return ErrorCode.INVALIDPERMISSIONS;
		}
		reviewManager.save(reviewToEdit);
		return ErrorCode.SUCCESS;
	}
	
	@GetMapping("/api/deletereview")
	public ErrorCode deleteReview(@RequestParam(value="id") int id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(id);
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		Review reviewToDelete = null;
		try {
			reviewToDelete = reviewManager.findById(id).get();
		}
		catch (NoSuchElementException e) {
			System.out.println("failed");
			return ErrorCode.DOESNOTEXIST;
		}
	
		if (!currentUser.hasRole(UserRole.ROLE_ADMIN) && 
			!reviewToDelete.getAuthor().equals(currentUser)) {
			return ErrorCode.INVALIDPERMISSIONS;
		}
		reviewManager.delete(reviewToDelete);
		return ErrorCode.SUCCESS;
	}
	
	@GetMapping("/api/highestratedmovies")
	public List<Movie> displayHighestRatedMovies() {
		return movieManager.findTop10ByOrderByCriticRatingDesc();
	}
	
	@GetMapping("/api/latestcriticreviews")
	public List<CriticReview> displayLatestCriticReviews() {
		return reviewManager.findTop10ByDateBeforeOrderByDateDesc(LocalDate.now().plusDays(1));
	}
	
	@PostMapping("/api/deletemovie")
    public ErrorCode deleteMovie(@RequestParam(value="id") int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		Movie movieToDelete;
		try {
			movieToDelete = movieManager.findById(id).get();
		}
		catch (NoSuchElementException e) {
			return ErrorCode.DOESNOTEXIST;
		}
		
		if (!currentUser.hasRole(UserRole.ROLE_ADMIN)){
			return ErrorCode.INVALIDPERMISSIONS;
		}
		
		movieManager.delete(movieToDelete);
        return ErrorCode.SUCCESS;
    }
	
	@PostMapping("/api/addmovie")
    public ErrorCode addMovie(@RequestBody Movie movie) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Movie temp = movieManager.findById(movie.getId()).get();
        if (temp != null) {
            return ErrorCode.DATABASEERROR;
        }
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		
		if (!currentUser.hasRole(UserRole.ROLE_ADMIN)){
			return ErrorCode.INVALIDPERMISSIONS;
		}
		
		movieManager.save(movie);
        return ErrorCode.SUCCESS;
    }
	
	@PostMapping("/api/editmovie")
    public ErrorCode editmovie(@RequestBody Movie movie) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Movie temp = movieManager.findById(movie.getId()).get();
        if (temp == null) {
            return ErrorCode.DATABASEERROR;
        }
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		
		if (!currentUser.hasRole(UserRole.ROLE_ADMIN)){
			return ErrorCode.INVALIDPERMISSIONS;
		}
		
		movieManager.save(movie);
        return ErrorCode.SUCCESS;
    }
	
	@GetMapping("/api/playtrailer")
    public StreamingResponseBody playTrailer(@RequestParam(value = "id") int id) {
        String trailerPath = "";
        try {
            trailerPath = movieManager.findById(id).get().getTrailerPath();
            if (trailerPath.equals("")) {
                return null;
            }
        } catch (Exception e) {
            System.out.println("can't get movie");
        }
        String trailerLocation = System.getProperty("user.dir") + "/trailers" + trailerPath;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(trailerLocation);
        } catch (FileNotFoundException f) {
            return null;
        }
 
        int bufsize = 8388608;
        byte[] buffer = new byte[bufsize];
       
        return (OutputStream out) -> {
            int i;
            try {
                while ((i = inputStream.read(buffer))!=-1) {
                    out.write(buffer);
                    out.flush();
                }
            } catch(Exception e)  {
               
            }
        };
 
    }
	
//	@GetMapping("/api/playtrailer")
//	public byte[] playTrailer(@RequestParam(value="id") int id, @RequestParam(value="nextByte") int nextByte) {
//		String trailerPath = "";
//		try {
//            trailerPath = movieManager.findById(id).get().getTrailerPath();
//            if (trailerPath.equals("")) {
//				return null;
//			}
//        }
//    	catch (Exception e) {
//            System.out.println("can't get movie");
//    	}		
//		String trailerLocation = System.getProperty("user.dir") + "/trailers" + trailerPath;
//		FileInputStream inputStream;
//		try {
//			inputStream = new FileInputStream(trailerLocation);
//		}
//		catch (FileNotFoundException f) {
//			return null;
//		}
//		byte[] videoPart = new byte[8388608]; // 8 MB
//		try {
//			long numBytesSkipped = inputStream.skip(nextByte);
//			if (numBytesSkipped == -1) {
//				return null;
//			}
//			inputStream.read(videoPart);
//		}
//		catch (IOException e) {
//			return videoPart;
//		}
//		return videoPart;
//	}
	
	@GetMapping("/api/addtolist")
	public ErrorCode addToList(@RequestParam(value="id") int id, @RequestParam(value="wantToSee") boolean wantToSee) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		Movie theMovie = null;
		try {
			theMovie = movieManager.findById(id).get();
		}
		catch (NoSuchElementException e) {
			return ErrorCode.DOESNOTEXIST;
		}
		ErrorCode successfulAddition;
		if (wantToSee) {
			successfulAddition = currentUser.addToMovieWatchlist(theMovie);
		}
		else {
			successfulAddition = currentUser.addToMovieBlacklist(theMovie);
		}
		if (!successfulAddition.equals(ErrorCode.SUCCESS)) {
			return successfulAddition;
		}
		if (userManager.save(currentUser) == null) {
			return ErrorCode.DATABASEERROR;
		}
		return ErrorCode.SUCCESS;
	}
	
	@GetMapping("/api/removefromlist")
	public ErrorCode removeFromList(@RequestParam(value="id") int id, @RequestParam(value="wantToSee") boolean wantToSee) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (email.equals("anonymousUser")) {
			return ErrorCode.NOTLOGGEDIN;
		}
		User currentUser = userManager.findByEmail(email);
		Movie theMovie = null;
		try {
			theMovie = movieManager.findById(id).get();
		}
		catch (NoSuchElementException e) {
			return ErrorCode.DOESNOTEXIST;
		}
		ErrorCode successfulRemoval;
		if (wantToSee) {
			successfulRemoval = currentUser.removeFromMovieWatchlist(theMovie);
		}
		else {
			successfulRemoval = currentUser.removeFromMovieBlacklist(theMovie);
		}
		
		if (!successfulRemoval.equals(ErrorCode.SUCCESS)) {
			return successfulRemoval;
		}

		if (userManager.save(currentUser) == null) {
			return ErrorCode.DATABASEERROR;
		}
		return ErrorCode.SUCCESS;
	}
	
}
