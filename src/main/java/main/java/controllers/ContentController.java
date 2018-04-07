package main.java.controllers;

import java.util.Date;
import main.java.managers.CelebrityManager;
import main.java.managers.ContentManager;
import main.java.models.Celebrity;
import main.java.models.Content;
import main.java.models.ContentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin("http://localhost:3000")
@RestController
public class ContentController {

	@Autowired
	private ContentManager contentManager;
	
	@Autowired
	private CelebrityManager celebrityManager;


    @RequestMapping("/movie")
    public Content movies(@RequestParam(value="id") int id) {
        System.out.println("Id is " + id);
        try {
        	Content theMovie = contentManager.findById(id).get();
			System.out.println(theMovie.getTitle());
        	return theMovie;
    	}
    	catch (Exception e) {
    		System.out.println("can't get movie");
    	}
//		Date p = new Date(2004, 11, 14);
//		Celebrity s = new Celebrity();
//		s.setCeleb_id(1);
//		s.setName("Stephen Hillenburg");
//		Celebrity d = new Celebrity();
//		d.setCeleb_id(2);
//		d.setName("Derek Drymon");
//		celebrityManager.save(s);
//		celebrityManager.save(d);
//		Content content = new Content(2, ContentType.MOVIE, "The SpongeBob SquarePants Movie", "Animation", p, 80, null, 10, null, "There's trouble brewing in Bikini Bottom. Someone has stolen King Neptune's crown, and it looks like Mr. Krab, SpongeBob's boss, is the culprit. Though he's just been passed over for the promotion of his dreams, SpongeBob stands by his...", s, null, null, 140000000, false, null);
//		content.setWriter(d);
//		content.setStudio("Paramount Pictures");
//		content.setPoster_path("/w600_and_h900_bestv2/9NsjFw5NEBppIb7KUDQa4010jKT.jpg");
//		content.setRuntime("87min");
//		contentManager.save(content);
		
       return null;
    }
}