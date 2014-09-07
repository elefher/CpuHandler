package com.elefher.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Profile implements Serializable {
	private long id;
	private String profile, minFreq, maxFreq;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMinFreq() {
		return minFreq;
	}

	public void setMinFreq(String minFreq) {
		this.minFreq = minFreq;
	}

	public String getMaxFreq() {
		return maxFreq;
	}

	public void setMaxFreq(String maxFreq) {
		this.maxFreq = maxFreq;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return profile;
	}
}