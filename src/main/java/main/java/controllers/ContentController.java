package main.java.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import main.java.managers.ContentManager;
import main.java.models.Content;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private ContentManager contentManager;

    @GetMapping("/movie")
    public Content getMovieInfo(@RequestParam(value="id") int id, HttpServletRequest req) {
        try {
            System.out.println("Getting movie...");
            //Authentication auth = ((SecurityContext)req.getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY)).getAuthentication();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("logged in user stored in context: " + auth.getName());
            
        	Content theMovie = contentManager.findById(id).get();
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}

       return null;
    }
}