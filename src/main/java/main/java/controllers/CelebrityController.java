package main.java.controllers;

import main.java.managers.CelebrityManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin("http://localhost:3000")
@RestController
public class CelebrityController {

	@Autowired
	private CelebrityManager celebrityManager;
	
}