package com.jamieholdstock.tflrefunds;

import com.jamieholdstock.tflrefunds.pages.oysterhistory.TableRow;

import junit.framework.TestCase;

public class TableRowTest extends TestCase
{
	private String testString = "12:58 - 13:18 Kings Cross (Piccadilly, Victoria lines) to East Finchley £2.70 £23.10";
	private String dateTestString = "Monday, 13 October 2014";
	
	private TableRow tr;
	private TableRow dateTr;
	
    public TableRowTest() {
        tr = new TableRow(testString);
        dateTr = new TableRow(dateTestString);
    }

    public void test_destination() {
        String dest = tr.getDestination();
        assertEquals("East Finchley", dest);
    }
    
    public void test_source() {
        String source = tr.getSource();
        assertEquals("Kings Cross (Piccadilly, Victoria lines)", source);
    }
    
    public void test_start() {
        String start = tr.getStart();
        assertEquals("12:58", start);
    }
    
    public void test_cost() {
        String start = tr.getCost();
        assertEquals("£2.70", start);
    }
    
    public void test_end() {
        String end = tr.getEnd();
        assertEquals("13:18", end);
    }
    
    public void test_date_false() {
    	assertFalse(tr.isDate());
    }
    
    public void test_date_true() {
    	assertTrue(dateTr.isDate());
    }
}
