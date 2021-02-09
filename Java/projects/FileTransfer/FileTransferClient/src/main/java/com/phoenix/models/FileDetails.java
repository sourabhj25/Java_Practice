package com.phoenix.models;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import lombok.Data;

/**
 * 
 * @author nilesh
 * This class used to defined file details
 * @data : used to generated all getter and setter and default constructor
 */
@SuppressWarnings("serial")
@Data
public class FileDetails implements Serializable {
	private String fileName;
	private InputStream inputStream;
	private byte[] fileData;
	private String md5;

	@Override
	public String toString() {
		return "FileDetails [fileName=" + fileName + ", inputStream=" + inputStream + ", fileData="
				+ Arrays.toString(fileData) + ", md5=" + md5 + "]";
	}

}
