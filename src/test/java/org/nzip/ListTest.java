package org.nzip;

import org.junit.Before;
import org.junit.Test;
public class ListTest {
List list;

@Before
public void setup() {
	list = new List();
	list.setZipExtensions(new String[] {"zip"});
}

@Test
public void testNonNested() {
	
	testForException("readme.zip");
	
}

@Test
public void testDoubleNested() {
	
	testForException("readmeDoubleNested.zip");
	
}

@Test
public void testNested() {
	
	testForException("readmeNested.zip");
	
}

@Test
public void testNestedWithFolder() {
	
	testForException("readmeNestedWithFolder.zip");
	
}

@Test
public void testWithFolder() {
	
	testForException("readmeWithFolder.zip");
	
}


private void testForException(String zipName) {
	list.setSourceFileName("target/test-classes/"+zipName);
	list.list();
}
}
