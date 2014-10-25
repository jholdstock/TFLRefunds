package com.jamieholdstock.tflrefundservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	
	private String time;
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public Time(String time) {
		this.time = time;
	}
	
	public int toMinutes() {
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// Horrible use of the Date library, but it is a horrible library so...
		if (date.getHours() < 5) {
			date.setDate(date.getDate() + 1);
		}
		return (int) (date.getTime()  / (1000 * 60));
	}
	
	@Override
	public String toString() {
		return time;
	}
}
