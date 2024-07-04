package com.harsha.db.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


public class ProfileTest {
	
	@Test //Test annotation.
	public void testLoaddBConfig() {
		var props = Profile.getProperties("db");
		
		assertNotNull("Cannot load db properties", props);
		
		var dbName = props.getProperty("database");
		
		assertEquals("dbName incorrect", "peopletest", dbName);
	}
}