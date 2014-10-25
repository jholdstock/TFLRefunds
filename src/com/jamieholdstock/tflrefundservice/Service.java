package com.jamieholdstock.tflrefundservice;

import java.lang.reflect.Modifier;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.GsonBuilder;
import com.jamieholdstock.tflrefundservice.pages.journeyplanner.JourneyPlannerPage;
import com.jamieholdstock.tflrefundservice.pages.oysterhistory.IncorrectLoginDetailsException;
import com.jamieholdstock.tflrefundservice.pages.oysterhistory.OysterHistoryPage;
import com.jamieholdstock.tflrefundservice.webdrivers.HeadlessDriver;

@Path("/")
public class Service {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String sayHello() {
		return "<html><title>Tfl Refund Service</title><body><h1>Service up and running</h1></body></html>";
	}
	
	@GET
	@Path("/journeyLength")
	@Produces(MediaType.TEXT_PLAIN)
	public String journeyLength(@QueryParam("source") String source, @QueryParam("destination") String destination, @QueryParam("destination") String start) {
    	HeadlessDriver driver = new HeadlessDriver();
    	
    	Duration duration = null;
    	try {
            log("Getting expected journey durations...");
            JourneyPlannerPage jpPage = new JourneyPlannerPage(driver);
            duration = jpPage.getJourneyDuration(source, destination, start);
    	}
    	catch (Exception e) {
    		return ("Runtime exception caught by Main.\n" + e.getLocalizedMessage());
    	}
    	finally {
    		driver.quit();
    	}
    	String s = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create().toJson(duration.toInt());
    	log(s);
    	return s;
	}
	
	
	@GET
	@Path("/history")
	@Produces(MediaType.TEXT_PLAIN)
	public String journeyHistory(@QueryParam("username") String username, @QueryParam("password") String password) {
		
		username = "jam.jar69@gmail.com";
		
		//VisibleDriver driver = new VisibleDriver();
    	HeadlessDriver driver = new HeadlessDriver();
    	
    	List<Journey> journeys = null;
    	try {
        	log("Logging in to https://account.tfl.gov.uk...");
            OysterHistoryPage oysterPage = null;
    		
            oysterPage = new OysterHistoryPage(driver, username, password);
    		
            log("Getting journey history...");
            journeys = oysterPage.getJourneys();
            
//            log("Getting expected journey durations...");
//            JourneyPlannerPage jpPage = new JourneyPlannerPage(driver);
//            journeys = jpPage.getJourneyDurations(journeys);
    	}
    	catch (IncorrectLoginDetailsException e) {
    		return (e.getLocalizedMessage());
    	}
    	catch (Exception e) {
    		return ("Runtime exception caught by Main.\n" + e.getLocalizedMessage());
    	}
    	finally {
    		driver.quit();
    	}
    	String s =new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create().toJson(journeys);
    	log(s);
    	return s;
	}
	
	private void log(String s) {
		System.out.println("S: " + s);
	}

}
