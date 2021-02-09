package com.phoenix.md5reader.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phoenix.md5reader.dto.MD5ExistenceCheckDTO;
import com.phoenix.md5reader.model.MD5Entity;
import com.phoenix.md5reader.repo.MD5Repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Vishal Arora
 *
 */
@Component
@Slf4j
public class MD5ServiceImpl implements MD5Service {

	@Autowired
	MD5Repository md5Repo;

	/* (non-Javadoc)
	 * @see com.phoenix.md5reader.services.MD5Service#saveData(org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	@SneakyThrows(IOException.class)
	public String saveData(MultipartFile md5File) {
		String status;

		/** 
		 * 1. Create an input stream by taking in bytes of the received multipart file
		 * 2. Convert the stream in XWPF format since the document will be in .docx extension 
		 * 3. Extract the data and split it by new line character
		 * 
		 * */
		InputStream is = new ByteArrayInputStream(md5File.getBytes());
		XWPFDocument doc = new XWPFDocument(is);
		XWPFWordExtractor we = new XWPFWordExtractor(doc);

		/** 
		 * 1. First level deduplication using HashSet
		 * 
		 * */
		Set<String> md5Codes = new HashSet<>(Arrays.asList(we.getText().split("\n")));

		if (!md5Codes.isEmpty()) {
			Iterator<String> iterator = md5Codes.iterator();
			while (iterator.hasNext()) {
				String currentHashValue = iterator.next();
				if (currentHashValue.contains("\n")) {
					/** 
					 * The last line of document may contain two '\n'. So, skip the needless.
					 * */
					log.info("new line character found at : " + currentHashValue);
					continue;
				} else {
					MD5Entity md5Entity = new MD5Entity(currentHashValue);
					try {
						md5Repo.save(md5Entity);
						log.info("Finished persistence for : " + currentHashValue);
					} catch (DataIntegrityViolationException e) {
						/** 
						 * 2. Second level deduplication by unique key constraint
						 * 
						 * */
						log.error("Caught Exception at : " + currentHashValue);
					}
				}
			}

			status = "Data successfully inserted in database";
		} else {
			status = "Unexpected Data Obtained";
		}
		we.close();
		is.close();

		return status;
	}

	/* (non-Javadoc)
	 * @see com.phoenix.md5reader.services.MD5Service#checkForExistence(java.lang.String[])
	 */
	@Override
	public List<MD5ExistenceCheckDTO> checkForExistence(String[] md5Codes) {
		List<MD5ExistenceCheckDTO> listDto = new ArrayList<>();
		if (md5Codes.length > 0) {
			for (String md5Code : md5Codes) {
				/** 
				 * 1. Check whether the corresponding hash code is present in our database
				 * */
				int result = md5Repo.existing(md5Code);

				boolean existence = false;
				if (result > 0)
					existence = true;
				
				log.info("Found result " + existence + " for " + md5Code);

				MD5ExistenceCheckDTO existenceCheckDTO = new MD5ExistenceCheckDTO(md5Code, existence);
				listDto.add(existenceCheckDTO);
			}
		}
		return listDto;
	}

}
