package com.elefher.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Service implements Serializable {
	private long id;
	private String enable, governor, minFreq, maxFreq;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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
	
	public String getGovernor() {
		return governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() { return null; }
}