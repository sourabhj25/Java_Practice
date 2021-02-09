package com.phoenix.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.phoenix.common.Constants;
import com.phoenix.models.FileDetails;

import lombok.AllArgsConstructor;

/**
 * @author nilesh
 */

/**
 * <h3>This class used to implement file transfer from client to server</h3>
 * <li>Connect available client connection</li>
 * <li>Read file send from client machine and write file on server machine</li>
 * <li>Send back file transfer status for client machine</li>
 **/

@AllArgsConstructor
public class Server extends Thread {

	public static Logger logger = Logger.getLogger(Server.class);

	public final Socket serverCocket;
	public String directoryPath;

	/**
	 * This method read client transfer file and stored on server machine
	 * <li>Check for client sending data</li>
	 * <li>If client sending data read data from client socket stream</li>
	 * <li>Read file transfer from client</li>
	 * <li>Write read file on server</li>
	 * <li>Generate checksum of write file</li>
	 * <li>Compare newly generated file checksum with client send file checksum</li>
	 * <li>Send success response to client if checksum is correct otherwise delete
	 * file from server</li>
	 */
	public void run() {
		try {
			// Check client sending data
			if (serverCocket != null) {
				logger.info("Successfully established connection with client");

				/** Reads the object from socket */
				ObjectInputStream objectInputStream = null;
				objectInputStream = new ObjectInputStream(serverCocket.getInputStream());
				// Read file transfer from client
				FileDetails fileDetails = (FileDetails) objectInputStream.readObject();
				if (fileDetails != null) {
					logger.info("Server : Received file details from client as below: ");
					logger.info("File Name: " + fileDetails.getFileName());
					logger.info("File MD5 checksum: " + fileDetails.getMd5());
					logger.info("File size : " + fileDetails.getFileData().length);

					/** Create directory for file to store and write the file */
					logger.info("Server : File Transfer directory path '" + directoryPath+"'");
					File serverFilePathToSave = new File(directoryPath);
					if (!serverFilePathToSave.exists())
						serverFilePathToSave.mkdir();
					// Get client file data
					byte[] wholeFileDataFromClient = new byte[fileDetails.getFileData().length];
					wholeFileDataFromClient = fileDetails.getFileData();
					// Write read file on server
					FileOutputStream fileOutputStream = new FileOutputStream(
							serverFilePathToSave + Constants.DIRECTORY_SEPERATOR + fileDetails.getFileName());
					fileOutputStream.write(wholeFileDataFromClient);

					OutputStream serverOutputStream = serverCocket.getOutputStream();
					// Generate checksum of write file
					String md5 = checkSumMD5(serverFilePathToSave, fileDetails);
					try {
						// Compare newly generated file checksum with client send file checksum
						if (md5 != null) {
							logger.info("Server: MD5 checksum generated for file " + fileDetails.getFileName() + md5
									+ " Client : MD5 checksum generated for file " + fileDetails.getMd5());
							// Compare md5 and send response to client
							if (fileDetails.getMd5().equals(md5)) {
								logger.info("Received file is correct");
								// Send success response to client
								serverOutputStream.write(Constants.STATUS_SUCEESS);
								logger.info("Send success status to client");
								serverCocket.close();
							} else {
								// Delete transfer file as incorrect file trasfer or file corrupt while
								// transferring
								File fileTodelete = new File(serverFilePathToSave + Constants.DIRECTORY_SEPERATOR
										+ fileDetails.getFileName());
								if (fileTodelete.exists())
									fileTodelete.delete();
								else
									logger.error("Server: Unable to find file " + fileDetails.getFileName()
											+ " to delete from server as received file is not correct ");

								serverOutputStream.write(Constants.STATUS_FAILED);
								logger.info("Server: Received file '" + fileDetails.getFileName()
										+ "' was incorrect so deleted");
							}
						} else {
							logger.info("Server: MD5 could not be generated file may be incorect");
						}
					} finally {
						if (objectInputStream != null)
							objectInputStream.close();
						if (fileOutputStream != null)
							fileOutputStream.close();
						if (serverOutputStream != null)
							serverOutputStream.close();
					}
				} else {
					logger.info("Client socket not sending data");
				}
				serverCocket.close();
			}
		} catch (Exception e) {
			logger.error("Server: Client Stopped abruptly.Detail message as below" + e.getMessage());
		}
	}

	/**
	 * This method is used to generate MD5 checksum of file This checksum used to
	 * compare correct file received from client
	 * 
	 * @param filePath
	 *            - Input file location
	 * @return File checksum
	 */
	public String checkSumMD5(File fileToSave, FileDetails fileDetails) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Files
					.readAllBytes(Paths.get(fileToSave + Constants.DIRECTORY_SEPERATOR + fileDetails.getFileName())));
			byte[] digest = md.digest();
			md5 = Arrays.toString(digest);
			return md5;
		} catch (NoSuchAlgorithmException e1) {
			logger.error(
					"Unable to generate checksum for file.Please specify correct algorithm.  Detail error message as below \n"
							+ e1.getMessage());
		} catch (IOException e) {
			logger.error("Unable to read input file. Detail error message as below " + e.getMessage());
		}
		return md5;
	}
}