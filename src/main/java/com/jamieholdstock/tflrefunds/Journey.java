package com.jamieholdstock.tflrefunds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Journey {
	private String source;
	private String destination;
	private String date;
	private String start;
	private String end;
	
	public Journey(String source, String destination, String date, String start, String end) {
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.start = start;
		this.end = end;
	}
	
	public int getDuration() {
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	    Date dateObj1 = null;
	    Date dateObj2 = null;
		try {
			dateObj1 = sdf.parse(start);
			dateObj2 = sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Horrible use of the Date library, but it is a horrible library so...
		if (dateObj1.getHours() < 5) {
			dateObj1.setDate(dateObj1.getDate() + 1);
		}
		
		if (dateObj2.getHours() < 5) {
			dateObj2.setDate(dateObj2.getDate() + 1);
		}
		
	    long diff = Math.abs(dateObj2.getTime() - dateObj1.getTime());
	    double diffInMinutes = diff / ((double) 1000 * 60);
 
		return (int) diffInMinutes;
	}
	
	@Override
	public String toString() {
		return "DATE: " + date + "\n"
				+ "SOURCE: " + source + "\n"
				+ "DEST: " + destination + "\n"
				+ "START: " + start + " END: " + end + "\n"
				+ "DURATION: " + getDuration();
	}
}
