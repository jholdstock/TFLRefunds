package com.jamieholdstock.tflrefunds.pages.journeyplanner;

import java.util.Arrays;
import java.util.HashMap;

import com.jamieholdstock.tflrefunds.Duration;
import com.jamieholdstock.tflrefunds.Station;

public class DurationCache {

	private HashMap<String, Duration> hash;
	
	public DurationCache() {
		hash = new HashMap<String, Duration>();
	}
	
	public boolean containsJourney(Station source, Station destination) {
		return hash.containsKey(hashKey(source, destination));
	}
	
	public Duration getJourney(Station source, Station destination) {
		return hash.get(hashKey(source, destination));
	}
	
	public void addJourney(Station source, Station destination, Duration duration) {
		hash.put(hashKey(source, destination), duration);
	}
	
	private String hashKey(Station source, Station destination) {
		String[] names = new String[]{source.getName(), destination.getName()};
		Arrays.sort(names);
		return names[0] + names[1];
	}
}
