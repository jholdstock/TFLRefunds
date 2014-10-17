package com.jamieholdstock.tflrefunds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JourneyPlannerPage {
	
	private WebDriver driver;
	
	public JourneyPlannerPage(WebDriver driver) {
        this.driver = driver;
	}
	
	public Duration getJourneyDuration(String source, String destination) {
		driver.get("http://journeyplanner.tfl.gov.uk/user/XSLT_TRIP_REQUEST2?language=en");
		driver.findElement(By.id("startpoint")).sendKeys(source);
		driver.findElement(By.id("endpoint")).sendKeys(destination);
		driver.findElement(By.id("endpoint")).submit();
		
		WebElement tableElement = driver.findElement(By.xpath("//table[@class='jpresults']"));
		WebElement duration = tableElement.findElement(By.xpath("//td[@class='duration']"));
		
		return Duration.fromTflFormat(duration.getText());
	}
}
