package com.jamieholdstock.tflrefunds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	
	private Date date;
	private String tflTime;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	private Time(String time) {
		try {
			this.date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		this.tflTime = time;
	
		// Horrible use of the Date library, but it is a horrible library so...
		if (date.getHours() < 5) {
			date.setDate(date.getDate() + 1);
		}
	}
	
	public static Time fromTflFormat(String tflFormatted) {
		return new Time(tflFormatted);
	}
	
	public int toMinutes() {
		return (int) (date.getTime()  / (1000 * 60));
	}
	
	@Override
	public String toString() {
		return tflTime;
	}
}
