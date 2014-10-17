package com.jamieholdstock.tflrefunds;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public String getSource() {
		return selectWithRegex("- " + timeRegex + " ([^£]*) to ");
	}
	
	public String getDestination() {
		return selectWithRegex(" to ([^£]*) £");
	}
	
	public String getStart() {
		return selectWithRegex("(" + timeRegex + ")" + " -");
	}

	public String getEnd() {
		return selectWithRegex("- (" + timeRegex + ")");
	}
	
	public String getCost() {
		return selectWithRegex("(£\\S*)");
	}
	
	private String selectWithRegex(String regex) {
		Pattern MY_PATTERN = Pattern.compile(regex);
    	Matcher m = MY_PATTERN.matcher(rowText);
    	m.find();
    	return m.group(1);
	}
	
	@Override
	public String toString() {
		return rowText;
	}
}
