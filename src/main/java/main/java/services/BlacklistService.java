/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.managers.CelebrityManager;
import main.java.managers.ContentManager;
import main.java.managers.TVManager;
import main.java.models.Celebrity;
import main.java.models.Content;
import main.java.models.Movie;
import main.java.models.TVShow;
import main.java.models.User;

/**
 *
 * @author irk
 */
public class BlacklistService {

	private static BlacklistService instance;

	private BlacklistService() {
		instance = this;
	}

	public static BlacklistService getService() {
		if (instance == null) {
			instance = new BlacklistService();
		}
		return instance;
	}

	public List<Content> filter(List<Content> contentToFilter, User currentUser) {
		Set<Content> blacklist = currentUser.getBlacklist();
		List<Content> filteredContent = new ArrayList<Content>();
		for (Content c : contentToFilter) {
			boolean remove = false;
			for (Content f : blacklist) {
				if (c.getId() == f.getId()) {
					remove = true;
					break;
				} 
			}
			if(!remove){
				filteredContent.add(c);
			}
		}
		return filteredContent;
	}
	
	public List<Movie> filterMovie(List<Movie> contentToFilter, User currentUser) {
		Set<Content> blacklist = currentUser.getBlacklist();
		List<Movie> filteredContent = new ArrayList<Movie>();
		for (Movie c : contentToFilter) {
			boolean remove = false;
			for (Content f : blacklist) {
				if (c.getId() == f.getId()) {
					remove = true;
					break;
				} 
			}
			if(!remove){
				filteredContent.add(c);
			}
		}
		return filteredContent;
	}
	public ArrayList<Movie> filterMovie(ArrayList<Movie> contentToFilter, User currentUser) {
		Set<Content> blacklist = currentUser.getBlacklist();
		ArrayList<Movie> filteredContent = new ArrayList<Movie>();
		for (Movie c : contentToFilter) {
			boolean remove = false;
			for (Content f : blacklist) {
				if (c.getId() == f.getId()) {
					remove = true;
					break;
				} 
			}
			if(!remove){
				filteredContent.add(c);
			}
		}
		return filteredContent;
	}
	
	public List<TVShow> filterTVShow(List<TVShow> contentToFilter, User currentUser) {
		Set<Content> blacklist = currentUser.getBlacklist();
		List<TVShow> filteredContent = new ArrayList<TVShow>();
		for (TVShow c : contentToFilter) {
			boolean remove = false;
			for (Content f : blacklist) {
				if (c.getId() == f.getId()) {
					remove = true;
					break;
				} 
			}
			if(!remove){
				filteredContent.add(c);
			}
		}
		return filteredContent;
	}
	
	public ArrayList<TVShow> filterTVShow(ArrayList<TVShow> contentToFilter, User currentUser) {
		Set<Content> blacklist = currentUser.getBlacklist();
		ArrayList<TVShow> filteredContent = new ArrayList<TVShow>();
		for (TVShow c : contentToFilter) {
			boolean remove = false;
			for (Content f : blacklist) {
				if (c.getId() == f.getId()) {
					remove = true;
					break;
				} 
			}
			if(!remove){
				filteredContent.add(c);
			}
		}
		return filteredContent;
	}
	
}
