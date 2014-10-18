package com.jamieholdstock.tflrefunds;


public class Duration {
	
	private int iDuration;
	
	private Duration(int iDuration) {
		this.iDuration = iDuration;
	}
	
	public static Duration fromTflFormat(String tflFormatted) {
		String[] tokens = tflFormatted.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		
		return new Duration((60 * hours) + minutes);
	}
	
	public static Duration betweenTimes(Time start, Time end) {
		int lEnd = end.toMinutes();
		int lStart = start.toMinutes();
		
		int diff = Math.abs(lEnd - lStart);
	    
		return new Duration(diff);
	}
	
	public int toInt() {
		return iDuration;
	}
	
	@Override
	public String toString() {
		int hours = (int) iDuration / 60; 
		int minutes = iDuration % 60;
		
		String sDuration = "";
		if (hours > 0) {
			sDuration += hours + "h ";
		}
		sDuration += minutes + "m";
		
		return sDuration;
	}
}
