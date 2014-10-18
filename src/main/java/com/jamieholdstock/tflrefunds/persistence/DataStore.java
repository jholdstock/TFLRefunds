package com.jamieholdstock.tflrefunds.persistence;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

public class DataStore {

	public DataStore() {
		MVStore s = MVStore.open("storage.mvs");

		// create/get the map named "data"
		MVMap<Integer, String> map = s.openMap("data");

		// add and read some data
		map.put(1, "Hello World");
		System.out.println(map.get(1));

		// close the store (this will persist changes)
		s.close();
	}

}
