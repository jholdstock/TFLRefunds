package com.jamieholdstock.tflrefundservice.pages.oysterhistory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jamieholdstock.tflrefundservice.Station;

public class TableRow {

	private String rowText;	
	private String timeRegex = "\\d{2}:\\d{2}";
	
	public TableRow(String rowText) {
		int infoIndex = rowText.indexOf("Information displayed for this day may not be complete");
    	if (infoIndex != -1) {
    		rowText = rowText.substring(0, infoIndex);
    	}
    	
		this.rowText = rowText;
	}
	
	public boolean isIncompleteJourney() {
    	try {
    		getEnd();
    		return false;
    	} catch (IllegalStateException exception) {
    		return true;
    	}
	}
	
	public boolean isDate() {
		try {
    		DateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH);
    		df.parse(rowText);
    		return true;
    	}
    	catch (ParseException e) {
    		return false;
    	}
	}
	
	public Station getSource() {
		String station = selectWithRegex("- " + timeRegex + " ([^\u00A3]*) to ");
		return new Station(station);
	}
	
	public Station getDestination() {
		String station = selectWithRegex(" to ([^\u00A3]*) \u00A3");
		
		int index = station.indexOf("The fare");
		if (index != -1) {
			station = station.substring(0, index);
		}
		
		return new Station(station);
	}
	
	public String getStart() {
		return selectWithRegex("(" + timeRegex + ")" + " -");
	}

	public String getEnd() {
		return selectWithRegex("- (" + timeRegex + ")");
	}
	
	public String getCost() {
		return selectWithRegex("(\u00A3\\S*)");
	}
	
	private String selectWithRegex(String regex) {
		Pattern MY_PATTERN = Pattern.compile(regex);
    	Matcher m = MY_PATTERN.matcher(rowText);
    	m.find();
    	try {
    		return m.group(1);
    	} 
    	catch (IllegalStateException e) {
    		throw new IllegalStateException("No match for regex /" + regex + "/ in string '" + rowText + "'");
    	}
	}
	
	@Override
	public String toString() {
		return rowText;
	}
}
