package com.jamieholdstock.tflrefunds;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class App 
{
	//private static WebDriver driver = new FirefoxDriver();
	private static WebDriver driver = new HtmlUnitDriver();
	
	private static String username;
	private static String password;
	
    public static void main(String[] args) {
    	disableSpammyLogs();
    	
    	checkArguments(args);
    	
    	log("Logging in to https://account.tfl.gov.uk... ");
        OysterHistoryPage oysterPage = null;
		
        try {
			oysterPage = new OysterHistoryPage(driver, username, password);
		} catch (IncorrectLoginDetailsException e) {
			log(e.getMessage());
			System.exit(1);
		}     
		        
        log("Getting journey history... ");
        List<Journey> journeys = oysterPage.getJourneys();

        log("Got " + journeys.size() + " journeys:");
        for (Journey j : journeys) {
        	log(j.toString());
        	log("\n");
        }
        
        driver.quit();
    }
    
    private static void disableSpammyLogs() {
    	Logger logger = Logger.getLogger("");
    	logger.setLevel(Level.OFF);
    }
    
    private static void checkArguments(String[] args) {
    	if (args.length != 2) {
    		abnormalEnd("Please pass username and password in command line arguments");
    	}
    	else {
    		username = args[0];
    		password = args[1];
    	}
    }
    
    private static void abnormalEnd(String message) {
    	log(message);
    	System.exit(1);
    }
       
    private static void log(Object s) {
        System.out.println(s.toString());
    }    
}
