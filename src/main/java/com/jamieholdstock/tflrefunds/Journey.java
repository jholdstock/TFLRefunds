package com.jamieholdstock.tflrefunds;


public class Journey {
	private Station source;
	private Station destination;
	private String date;
	private Time start;
	private Time end;
	private String cost;
	private Duration expectedDuration; 
	
	public Journey(Station source, Station destination, String date, Time start, Time end, String cost) {
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.start = start;
	    this.end = end;
	    this.cost = cost;
	}
	
	public Station getSource() {
		return source;
	}

	public Station getDestination() {
		return destination;
	}
	
	public String getStart() {
		return start.toString();
	}
	
	public void setExpectedDuration(Duration d) {
		this.expectedDuration = d;
	}

	public Duration getExpectedDuration() {
		return this.expectedDuration;
	}
	
	public Duration getDuration() {
	    return Duration.betweenTimes(start, end);
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
		if (expectedDuration != null) { 
			s += "EXPECTED: " + expectedDuration + "\n"
				+ "DELAY: " + (getDuration().toInt() - expectedDuration.toInt());
		}
		else {
			s+= "EXPECTED: null";
		}
		return s;
	}
}
