package com.jamieholdstock.tflrefundservice;

public class Station {

	private String name;
	
	public Station(String stationName) {
		stationName = stationName.replace("[London Overground]", "");
		stationName = stationName.replace("[National Rail]", "");
		
		if (stationName.contains("Kings Cross")) {
			stationName = "Kings Cross Station";
		}
		
		if (stationName.contains("Shadwell")) {
			stationName = "Shadwell Station";
		}
		if (stationName.equals("Caledonian Road and Barnsbury")) {
			stationName += " Rail Station";
		}
		if (stationName.contains("Paddington")) {
			stationName = "Paddington Station";
		}
		
		this.name = stationName;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
