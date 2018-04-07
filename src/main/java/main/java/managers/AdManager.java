package main.java.managers;

import java.util.List;
import main.java.models.Ad;

public class AdManager {

	private List<Ad> ads;
	private List<Ad> pending;

	public boolean submitAd(Ad ad) {
		return false;
	}

	public boolean approveAd(int adID) {
		return false;
	}

	public boolean declineAd(int adID) {
		return false;
	}

}