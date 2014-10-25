package com.jamieholdstock.tflrefundservice;

public class Main {

	private static String username;
	private static String password;
	
	public static void main(String[] args) {
		checkArguments(args);
		log(new Service().journeyHistory(username, password));
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
		System.out.println("M: " + s.toString());
	}    
}
