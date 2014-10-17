package com.jamieholdstock.tflrefunds;

public class Journey {
	private String source;
	private String destination;
	private String date;
	private Time start;
	private Time end;
	
	public Journey(String source, String destination, String date, Time start, Time end) {
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.start = start;
	    this.end = end;
	}
	
	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public Duration getDuration() {
	    return Duration.fromTimes(start, end);
	}
	
	@Override
	public String toString() {
		return "DATE: " + date + "\n"
				+ "------------------\n"
				+ "FROM: " + source + "\n"
				+ "  TO: " + destination + "\n"
				+ "------------------\n"
				+ "START: " + start + "\n"
				+ "  END: " + end + "\n"
				+ "------------------\n"
				+ "DURATION: " + getDuration();
	}
}
