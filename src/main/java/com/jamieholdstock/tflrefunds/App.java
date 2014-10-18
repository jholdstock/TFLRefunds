package com.jamieholdstock.tflrefunds;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.jamieholdstock.tflrefunds.pages.journeyplanner.JourneyPlannerPage;
import com.jamieholdstock.tflrefunds.pages.oysterhistory.IncorrectLoginDetailsException;
import com.jamieholdstock.tflrefunds.pages.oysterhistory.OysterHistoryPage;

public class App {

    public App(WebDriver driver, String username, String password) throws IncorrectLoginDetailsException {
    	log("Logging in to https://account.tfl.gov.uk... ");
        OysterHistoryPage oysterPage = null;
		
        oysterPage = new OysterHistoryPage(driver, username, password);
		
		        
        log("Getting journey history... ");
        
        List<Journey> journeys = oysterPage.getJourneys();
        
        JourneyPlannerPage jpPage = new JourneyPlannerPage(driver);
        
        for (Journey j : journeys) {
        	log("\n");
        	log(j.toString());
        	Duration expectedDuration = null;
        	try {
        		 expectedDuration = jpPage.getJourneyDuration(j);
        	}
        	catch (NoSuchElementException exception) {
        		log("Couldn't lookup expected duration");
        		continue;
        	}
        
    		log("EXPECTED: " + expectedDuration);
    		log("DIFF: " + (j.getDuration().toInt() - expectedDuration.toInt()));
        }
    }
    
    private static void log(Object s) {
        System.out.println(s.toString());
    }    
}
