package com.phoenix.md5reader.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phoenix.md5reader.dto.MD5ExistenceCheckDTO;

/**
 * @author Vishal Arora
 *
 */
@Service
public interface MD5Service  {

	/**
	 * 1. Takes the multipart file uploaded.
	 * 2. Parses the same and filters md5 hash codes
	 * 3. Inserts the same in database if found unique
	 * 4. Sends appropriate status
	 * 
	 * @param md5File
	 * @return
	 */
	String saveData(MultipartFile md5File) throws IOException;
	
	/**
	 * 1. Takes in all the md5 codes one has to check for existence
	 * 2. checks if the same is present in database
	 * 3. returns boolean value against each record
	 * 
	 * @param md5Codes
	 * @return
	 */

	List<MD5ExistenceCheckDTO> checkForExistence(String[] md5Codes) ;
}
