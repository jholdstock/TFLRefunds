package com.jamieholdstock.tflrefunds;

import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JourneyPlannerPage {
	
	private WebDriver driver;
	private HashMap<String, Duration> durationCache;
	
	public JourneyPlannerPage(WebDriver driver) {
        this.driver = driver;
        this.durationCache = new HashMap<String, Duration>();
	}
	
	public Duration getJourneyDuration(String source, String destination) {
		String[] names = new String[]{source, destination};
		Arrays.sort(names);
		String cacheKey = names[0] + names[1];
				
		if (durationCache.containsKey(cacheKey)) {
			return durationCache.get(cacheKey);
		}
		
		driver.get("http://journeyplanner.tfl.gov.uk/user/XSLT_TRIP_REQUEST2?language=en");
		driver.findElement(By.id("startpoint")).sendKeys(source);
		driver.findElement(By.id("endpoint")).sendKeys(destination);
		driver.findElement(By.id("endpoint")).submit();
		
		WebElement tableElement = driver.findElement(By.xpath("//table[@class='jpresults']"));
		WebElement durationElement = tableElement.findElement(By.xpath("//td[@class='duration']"));
		
		Duration duration = Duration.fromTflFormat(durationElement.getText());
		
		durationCache.put(cacheKey, duration);
		
		return duration;
	}
}
