package com.jamieholdstock.tflrefunds.pages.oysterhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.jamieholdstock.tflrefunds.Journey;
import com.jamieholdstock.tflrefunds.Time;

public class OysterHistoryPage {
	
	private WebDriver driver;
	private boolean allPages = false;
	
	public OysterHistoryPage(WebDriver driver, String username, String password) throws IncorrectLoginDetailsException {
    	driver.get("https://account.tfl.gov.uk/oyster/login");

        driver.findElement(By.name("UserName")).sendKeys(username);
        driver.findElement(By.name("Password")).sendKeys(password);
        driver.findElement(By.name("Password")).submit();
  
        this.driver = driver;
        
        if (errorMessageIsShown()) {
        	String message = driver.findElement(By.id("errormessage")).getText();
        	throw new IncorrectLoginDetailsException(message);
        };
	}
	
	public List<Journey> getJourneys() {
		driver.findElement(By.linkText("Journey history")).click();
        
		waitForPageToLoad();
		
		if (allPages) {
			selectAllHistory();
		}
			
        List<Journey> journeys = new ArrayList<Journey>();
        boolean endLoop = false;
        
        do {
	        String date = "NOT SET";
	        WebElement tableElement = driver.findElement(By.xpath("//table[@class='journeyhistory']"));
	        for (WebElement row : tableElement.findElements(By.xpath("//tr[@class='reveal-table-row'] | //td[@class='day-date status-1']"))) {
	        	TableRow tr = new TableRow(row.getText());
	        	
	        	if (tr.isDate()) {
	        		date = tr.toString();
	        		continue;
	        	}
	        	
	        	if (tr.isIncompleteJourney()) {
	        		continue;
	        	}
	        	
	        	Time start = Time.fromTflFormat(tr.getStart());
	        	Time end = Time.fromTflFormat(tr.getEnd());
	        	
	        	Journey j = new Journey(tr.getSource(), tr.getDestination(), date, start, end, tr.getCost());
	        	
	        	journeys.add(j);
	        }
	        
	        if (anotherPage()) {
	        	driver.findElement(By.linkText("Next »")).click();
	        	waitForPageToLoad();
	        }
	        else {
	        	endLoop = true;
	        }
        }
        while (!endLoop);
        
        System.out.println("Got journeys: " + journeys.size());
        
        return journeys;
	}
	
	private void selectAllHistory() {
		WebElement select = driver.findElement(By.id("date-range"));
		select.click();
		select.findElement(By.xpath("//option[text()='custom date range']")).click();
		
        fluentWait("//input[@id='from']");
        
        driver.findElement(By.id("from")).click();
        driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//a[1]")).click();
        
        driver.findElement(By.id("to")).click();
        
        Object[] list = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-last']//a")).toArray();
        ((WebElement)list[list.length-1]).click();
        
        driver.findElement(By.id("date-range-button")).click();
        waitForPageToLoad();
	}
	
	private boolean errorMessageIsShown() {
		try {
        	driver.findElement(By.id("errormessage"));
        	return true;
        }
        catch (NoSuchElementException exception) {
        	return false;
        }    
	}
	
	private boolean anotherPage() {
		try {
			driver.findElement(By.linkText("Next »"));
        	return true;
        }
        catch (NoSuchElementException exception) {
        	return false;
        }    
	}
	
	private void waitForPageToLoad() { 
		fluentWait("//table[@class='journeyhistory']");
	}
	
	private WebElement fluentWait(final String locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(30, TimeUnit.SECONDS)
			.pollingEvery(1, TimeUnit.SECONDS)
			.ignoring(NoSuchElementException.class);
		
		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(By.xpath(locator));
				if (element.getText().contains("Please wait while we return your request")) {
					throw new NoSuchElementException(null);
				}
				else return element;
			}
		});
		
		return foo;
	};
}
