package com.jamieholdstock.tflrefundservice.persistence;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

import com.google.gson.GsonBuilder;
import com.jamieholdstock.tflrefundservice.Journey;


public class DataStore {

	private String path = System.getProperty("user.home") + File.separator + "storage.mvs";
	
	public DataStore() {
		
	}
	
	public void storeDate(Date d) {
		
		MVStore s = MVStore.open(path);
		
		MVMap<Integer, Date> map = s.openMap("date");
		
		map.put(1, d);

		s.close();		
	}
	
	public Date getDate() {
		MVStore s = MVStore.open(path);
		
		MVMap<Integer, Date> map = s.openMap("date");
		
		s.close();
		return map.get(1);
	}
	
	
	public void store(List<Journey> journeys) {
		MVStore s = MVStore.open(path);
		
		MVMap<Integer, String> map = s.openMap("data");
		String st = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create().toJson(journeys);
		map.put(1, st);
		
		
		s.close();
		storeDate(new Date());
	}

	public List<Journey> retreive() {
		MVStore s = MVStore.open(path);
		
		MVMap<Integer, String> map = s.openMap("data");
		String st = map.get(1);
		List<Journey> journeys = null;
		journeys = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create().fromJson(st, ArrayList.class); 

		s.close();
		
		return journeys;
	}
}
