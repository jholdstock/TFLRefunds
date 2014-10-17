package com.jamieholdstock.tflrefunds;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.jamieholdstock.tflrefunds.webdrivers.HeadlessDriver;

public class App {
	private static String username;
	private static String password;
	
    public static void main(String[] args) {
    	//VisibleDriver driver = new VisibleDriver();
    	HeadlessDriver driver = new HeadlessDriver();	
    	
    	log("");
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
        
        JourneyPlannerPage jpPage = new JourneyPlannerPage(driver);
        
        for (Journey j : journeys) {
        	log("\n");
        	log(j.toString());
        	Duration expectedDuration = null;
        	try {
        		 expectedDuration = jpPage.getJourneyDuration(j.getSource(), j.getDestination());
        	}
        	catch (NoSuchElementException exception) {
        		log("Couldn't lookup expected duration");
        		continue;
        	}
        
    		log("EXPECTED: " + expectedDuration);
    		log("DIFF: " + (j.getDuration().toInt() - expectedDuration.toInt()));
        }
                
        driver.quit();
    }
    
    private static void checkArguments(String[] args) {
    	if (args.length != 2) {
    		log("Enter username: ");
    		username = System.console().readLine();
    		log("Enter password: ");
    		password = new String(System.console().readPassword());
    	}
    	else {
    		log ("Using command line arguments");
    		username = args[0];
    		password = args[1];
    	}
    }
       
    private static void log(Object s) {
        System.out.println(s.toString());
    }    
}
