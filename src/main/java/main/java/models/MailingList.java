package main.java.models;

import java.util.List;

public class MailingList {

	private List<String> emails;

	public boolean sendNewsletter() {
		return false;
	}

	public boolean addEmail(String email) {
		return false;
	}

	public String removeEmail(String email) {
		return null;
	}

}