package com.jamieholdstock.tflrefundservice;


public class Journey {
	private String source;
	private String destination;
	private String date;
	private String start;
	private String end;
	private String cost;
	private int expectedDuration; 
	
	public Journey(String source, String destination, String date, String start, String end, String cost) {
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.start = start;
	    this.end = end;
	    this.cost = cost;
	}
	
	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}
	
	public String getStart() {
		return start.toString();
	}
	
	public void setExpectedDuration(int d) {
		this.expectedDuration = d;
	}

	public int getExpectedDuration() {
		return this.expectedDuration;
	}
	
	public Duration getDuration() {
	    return Duration.betweenTimes(new Time(start), new Time(end));
	}
	
	@Override
	public String toString() {
		String s = "DATE: " + date + "\n"
				+"COST: " + cost + "\n"
				+ "------------------\n"
				+ "FROM: " + source + "\n"
				+ "  TO: " + destination + "\n"
				+ "------------------\n"
				+ "START: " + start + "\n"
				+ "  END: " + end + "\n"
				+ "------------------\n"
				+ "DURATION: " + getDuration() + "\n";
		if (expectedDuration != 0) { 
			s += "EXPECTED: " + expectedDuration + "\n"
				+ "DELAY: " + (getDuration().toInt() - expectedDuration);
		}
		else {
			s+= "EXPECTED: null";
		}
		return s;
	}
}
