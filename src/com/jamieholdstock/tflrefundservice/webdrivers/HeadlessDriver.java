package com.jamieholdstock.tflrefundservice.webdrivers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HeadlessDriver extends HtmlUnitDriver {

	public HeadlessDriver() {
		setJavascriptEnabled(true);

		// Disable logs - css errors cause lots of spam
		Logger logger = Logger.getLogger("");
    	logger.setLevel(Level.OFF);
	}
}
