package com.jamieholdstock.tflrefunds;

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

public class OysterHistoryPage {
	
	private WebDriver driver;
	
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
        
        WebElement tableElement = fluentWait("//table[@class='journeyhistory']");
        
        List<Journey> journeys = new ArrayList<Journey>();
        String date = "NOT SET";
        for (WebElement row : tableElement.findElements(By.xpath("//tr[@class='reveal-table-row'] | //td[@class='day-date status-1']"))) {
        	TableRow tr = new TableRow(row.getText());
        	
        	if (tr.isDate()) {
        		date = tr.toString();
        		continue;
        	}
        	
        	Journey j = new Journey(tr.getSource(), tr.getDestination(), date, tr.getStart(), tr.getEnd());
        	
        	journeys.add(j);
        	j.getDuration();
        }
        return journeys;
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
