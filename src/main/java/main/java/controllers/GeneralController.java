package main.java.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
public class GeneralController {

	public String displayNewsAndInterviews() {
		return null;
	}

	public String viewAboutUsInfo() {
		return null;
	}

	public String viewProfilePage(int userID) {
		return null;
	}

}