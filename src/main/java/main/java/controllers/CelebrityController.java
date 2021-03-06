package main.java.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import main.java.dto.ContentDTO;
import main.java.managers.CelebrityManager;
import main.java.models.Celebrity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("http://localhost:3000")
@RestController
public class CelebrityController {

	@Autowired
	private CelebrityManager celebrityManager;
	
	@GetMapping("/celebrity")
    public Celebrity getCelebrityInfo(@RequestParam(value="id") int id) {
        try {
        	Celebrity theCelebrity = celebrityManager.findById(id).get();
        	return theCelebrity;
    	}
    	catch (Exception e) {
    		System.out.println("can't get celebrity");
    	}
       return null;
    }
	
	@GetMapping("/api/filmography")
    public List<ContentDTO> getFilmography(@RequestParam(value="id") int id) {
        try {
        	Celebrity theCelebrity = celebrityManager.findById(id).get();
        	return theCelebrity.getFilmographyDTO();
    	}
    	catch (Exception e) {
    		return null;
    	}
    }
	
}