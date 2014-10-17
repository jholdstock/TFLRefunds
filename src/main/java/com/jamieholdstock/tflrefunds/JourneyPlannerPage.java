package com.jamieholdstock.tflrefunds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JourneyPlannerPage {
	
	private WebDriver driver;
	private DurationCache durationCache;
	
	public JourneyPlannerPage(WebDriver driver) {
        this.driver = driver;
        this.durationCache = new DurationCache();
	}
	
	public Duration getJourneyDuration(String source, String destination) {
		if (durationCache.containsJourney(source, destination)) {
			return durationCache.getJourney(source, destination);
		}
		
		driver.get("http://journeyplanner.tfl.gov.uk/user/XSLT_TRIP_REQUEST2?language=en");
		driver.findElement(By.id("startpoint")).sendKeys(source);
		driver.findElement(By.id("endpoint")).sendKeys(destination);
				
		selectOnlyTrainTransportMethods();
		
		driver.findElement(By.id("endpoint")).submit();
		
		WebElement tableElement = driver.findElement(By.xpath("//table[@class='jpresults']"));
		WebElement durationElement = tableElement.findElement(By.xpath("//td[@class='duration']"));
		
		Duration duration = Duration.fromTflFormat(durationElement.getText());
		
		durationCache.addJourney(source, destination, duration);
		
		return duration;
	}
	
	private void selectOnlyTrainTransportMethods() {
		clickCheckbox("inclMOT_0");
		clickCheckbox("inclMOT_4");
		clickCheckbox("inclMOT_5");
		clickCheckbox("inclMOT_7");
		clickCheckbox("inclMOT_8");
		clickCheckbox("inclMOT_9");
	}
	
	private void clickCheckbox(String name) {
		WebElement checkbox = driver.findElement(By.name(name));
		checkbox.click();
	}
}
