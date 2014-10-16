package com.jamieholdstock.tflrefunds;

import junit.framework.TestCase;

public class JourneyTest extends TestCase {

	public void test_duration_afternoon() {
		Journey j = new Journey("", "", "", "13:01", "15:00");
		assertEquals(119, j.getDuration());
	}
	
	public void test_duration_morning() {
		Journey j = new Journey("", "", "", "05:30", "07:45");
		assertEquals(135, j.getDuration());
	}
	
	public void test_duration_overMidnight() {
		Journey j = new Journey("", "", "", "23:45", "01:01");
		assertEquals(76, j.getDuration());
	}
	
	public void test_duration_afterMidnight() {
		Journey j = new Journey("", "", "", "02:00", "04:01");
		assertEquals(121, j.getDuration());
	}
	
}
