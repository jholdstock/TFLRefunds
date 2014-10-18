package com.jamieholdstock.tflrefunds;

import com.jamieholdstock.tflrefunds.pages.oysterhistory.IncorrectLoginDetailsException;
import com.jamieholdstock.tflrefunds.persistence.DataStore;
import com.jamieholdstock.tflrefunds.webdrivers.HeadlessDriver;

public class Main {

	private static String username;
	private static String password;
	
	public static void main(String[] args) {
		
		new DataStore();
		
		//VisibleDriver driver = new VisibleDriver();
    	HeadlessDriver driver = new HeadlessDriver();
    	
    	checkArguments(args);
    	
    	try {
    		new App(driver, username, password);
    	}
    	catch (IncorrectLoginDetailsException e) {
    		log(e.getMessage());
    	}
    	catch (Exception e) {
    		log("Runtime exception caught by Main.");
    		e.printStackTrace();
    	}
    	finally {
    		driver.quit();
    	}
	}
	
    private static void checkArguments(String[] args) {
    	if (args.length != 2) {
    		log("Enter username: ");
    		username = System.console().readLine();
    		log("Enter password: ");
    		password = new String(System.console().readPassword());
    	}
    	else {
    		log("Using command line arguments");
    		username = args[0];
    		password = args[1];
    	}
    }
    
	private static void log(Object s) {
		System.out.println(s.toString());
	}    
}
