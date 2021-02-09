package com.phoenix.common;

/**
 * @author nilesh
 * This class used to define constant 
 */
public class Constants {

	public static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
	public static final String DIRECTORY_SEPERATOR = OPERATING_SYSTEM.contains("win") ? "\\" : "/";
}
