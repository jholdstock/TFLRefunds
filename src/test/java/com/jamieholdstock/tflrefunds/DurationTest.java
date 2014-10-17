package com.jamieholdstock.tflrefunds;

import junit.framework.TestCase;

public class DurationTest extends TestCase {

	private Duration duration;
	
	public void test_fromTflFormat() {
		durationFromString("00:52");
		assertStringAndInt("52m", 52);
	}
	
	public void test_fromTflFormat_hours() {
		durationFromString("02:52");
		assertStringAndInt("2h 52m", 172);
	}
	
	public void test_fromTime_afternoon() {
		durationFromTimes("13:01", "15:00");
		assertStringAndInt("1h 59m", 119);
	}
	
	public void test_fromTime_morning() {
		durationFromTimes("05:30", "07:45");
		assertStringAndInt("2h 15m", 135);
	}
	
	public void test_fromTime_overMidnight() {
		durationFromTimes("23:45", "01:01");
		assertStringAndInt("1h 16m", 76);
	}
	
	public void test_fromTime_afterMidnight() {
		durationFromTimes("02:00", "04:01");
		assertStringAndInt("2h 1m", 121);
	}
	
	private void assertStringAndInt(String sExpected, int iExpected) {
		assertEquals(iExpected, duration.toInt());
		assertEquals(sExpected, duration.toString());
	}
	
	private void durationFromString(String sDuration) {
		duration = Duration.fromTflFormat(sDuration);
	}
	
	private void durationFromTimes(String sStart, String sEnd) {
		Time start = Time.fromTflFormat(sStart);
		Time end = Time.fromTflFormat(sEnd);
		duration = Duration.fromTimes(start, end);
	}
}
