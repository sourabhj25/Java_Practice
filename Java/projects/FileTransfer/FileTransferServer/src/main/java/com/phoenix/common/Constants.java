package com.phoenix.common;
/**
 * 
 * @author nilesh
 * This class used to define constants
 */
public class Constants {
	public static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
	public static final String DIRECTORY_SEPERATOR = OPERATING_SYSTEM.contains("win") ? "\\" : "/";
	public static final int STATUS_SUCEESS = 200;
	public static final int STATUS_FAILED = 0;
}
