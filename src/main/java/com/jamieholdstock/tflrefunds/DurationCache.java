package com.jamieholdstock.tflrefunds;

import java.util.Arrays;
import java.util.HashMap;

public class DurationCache {

	private HashMap<String, Duration> hash;
	
	public DurationCache() {
		hash = new HashMap<String, Duration>();
	}
	
	public boolean containsJourney(String source, String destination) {
		return hash.containsKey(hashKey(source, destination));
	}
	
	public Duration getJourney(String source, String destination) {
		return hash.get(hashKey(source, destination));
	}
	
	public void addJourney(String source, String destination, Duration duration) {
		hash.put(hashKey(source, destination), duration);
	}
	
	private String hashKey(String source, String destination) {
		String[] names = new String[]{source, destination};
		Arrays.sort(names);
		return names[0] + names[1];
	}
}
