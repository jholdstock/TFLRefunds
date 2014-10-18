package com.jamieholdstock.tflrefunds.pages.journeyplanner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jamieholdstock.tflrefunds.Duration;
import com.jamieholdstock.tflrefunds.Journey;
import com.jamieholdstock.tflrefunds.Station;

public class JourneyPlannerPage {
	
	private WebDriver driver;
	private DurationCache durationCache;
	
	public JourneyPlannerPage(WebDriver driver) {
        this.driver = driver;
        this.durationCache = new DurationCache();
	}
	
	public List<Journey> getJourneyDurations(List<Journey> journeys) {
		for (Journey j : journeys) {
        	try {
        		Duration duration = getJourneyDuration(j);
        		j.setExpectedDuration(duration);
        	}
        	catch (NoSuchElementException exception) {
        		throw exception;
        	}
        }
		return journeys;
	}
	
	public Duration getJourneyDuration(Journey journey) {
		Station source = journey.getSource();
		Station destination = journey.getDestination();
		
		if (durationCache.containsJourney(source, destination)) {
			return durationCache.getJourney(source, destination);
		}
		
		driver.get("http://journeyplanner.tfl.gov.uk/user/XSLT_TRIP_REQUEST2?language=en");
		driver.findElement(By.id("startpoint")).sendKeys(source.getName());
		driver.findElement(By.id("endpoint")).sendKeys(destination.getName());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 2);
		Date tomorrow = calendar.getTime();
	
		if (driver.getClass().getName().contains("Visible")) {
			driver.findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "a"), dateFormat.format(tomorrow));
			driver.findElement(By.id("jp-time")).sendKeys(Keys.chord(Keys.CONTROL, "a"), journey.getStart());
		}

		selectOnlyTrainTransportMethods();
		
		driver.findElement(By.xpath("//div[@class='submit-btn']/input")).click();
		
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
