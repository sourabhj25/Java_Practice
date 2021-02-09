package com.phoenix.md5reader.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phoenix.md5reader.constants.HttpStatusCodes;
import com.phoenix.md5reader.dto.MD5ExistenceCheckDTO;
import com.phoenix.md5reader.services.MD5Service;
import com.phoenix.md5reader.util.ApplicationUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Vishal Arora
 *
 */
@Slf4j
@RestController
@Api(value = "MD5Controller", description = "File Reader Controller")
public class MD5Controller {

	@Autowired
	ApplicationUtils appUtils;

	@Autowired
	MD5Service md5Service;

	/**
	 * Scans the uploaded file, persists data and sends appropriate status message upon the operation
	 * 
	 * @param md5File : Docx file containing MD5 hashcodes seperated by \n
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ApiOperation(value = "Upload MD5 File")
	public ResponseEntity<?> uploadDocFile(@ApiParam("MD5 doc file") @RequestParam MultipartFile md5File) {
		log.info("Inside upload method");
		String msg;

		if (md5File.isEmpty()) {
			msg = "Empty file obtained. Can't perform any operation";
			return ResponseEntity.ok(appUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR, msg, null));
		} else {
			try {
				msg = md5Service.saveData(md5File);
				return ResponseEntity.ok(appUtils.createResponseEntityDTO(HttpStatusCodes.OK, msg, null));
			} catch (IOException e) {
				msg = e.getMessage();
				return ResponseEntity.ok(appUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR, msg, null));
			}
		}
	}

	/**
	 * Takes in multiple hash codes and checks if relevant data exists in database or not
	 * 
	 * @param md5Texts : comma sperated array of MD5 hash codes
	 * @return
	 */
	@RequestMapping(value = "/checkExisting", method = RequestMethod.POST)
	@ApiOperation(value = "Check for xisting MD5 hash codes", notes = "Enter all values in comma seperated fashion. e.g. -> 123, 456, 789, ...... ")
	public ResponseEntity<?> checkExisting(@ApiParam("Enter texts") @RequestParam String... md5Texts) {
		log.info("inside check existing method");
		if(md5Texts == null || md5Texts.length == 0){
			return ResponseEntity.ok(appUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR, "Invalid input : ", null));
		}else{
			List<MD5ExistenceCheckDTO> existenceCheckList = md5Service.checkForExistence(md5Texts);
			return ResponseEntity.ok(appUtils.createResponseEntityDTO(HttpStatusCodes.OK, "Results : ", existenceCheckList));
		}	
	}

}
