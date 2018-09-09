package org.nzip;

import java.io.File;

public class Test {

	int testvar=2;
public static void main(String[] args) {

	System.out.println(File.separator);
	
	String s = "a\\b";
	System.out.println(s.replaceAll("\\\\", "/"));
}
}
