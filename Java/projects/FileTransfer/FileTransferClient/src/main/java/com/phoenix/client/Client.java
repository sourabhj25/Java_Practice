package com.phoenix.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import com.phoenix.common.Constants;
import com.phoenix.models.FileDetails;
/**
 * @author nilesh
 */
/**
 * This class implementation include 
 * <li>Send file from client to server</li>
 * </li>Read file from client machine write on server socket </li>
 * </li>Generate file MD5 checksum</li>
 */
public class Client {

	public static Logger logger = Logger.getLogger(Client.class);

	/**
	 * This method used to transfer file from client machine to server
	 * 
	 * @param fileName
	 *            : file to be transfer from client to server
	 * @param hostIpAddress
	 *            :client machine IP address
	 * @param portNumber
	 *            : socket port number
	 * @param directoryPath
	 *            : client machine directory path of newly created file
	 * @param transferFileName
	 *            : newly created filename
	 * @throws IOException
	 */
	public static boolean sendFile(String hostIpAddress, int portNumber, String directoryPath, String transferFileName,String certificatePath,String keyStorePassword,String keyPassword)
			throws IOException {
		boolean isFileTransfer = false;
		SSLSocket socket=null;
		try {
			//Used SSL TLS
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			//Client certificate verification
			sslcontext.init(createKeyManagers(certificatePath, keyStorePassword, keyPassword), createTrustManagers(certificatePath, keyStorePassword), new SecureRandom());
			SSLSocketFactory sslsocketfactory = sslcontext.getSocketFactory();
			socket = (SSLSocket)sslsocketfactory.createSocket(hostIpAddress,portNumber);
			logger.info("Waiting for server connection " + hostIpAddress + " with port address " + portNumber);
			logger.info("Client-Server connection completed");
			/**
			 * Read file from client machine and write on socket
			 */
			isFileTransfer = readWriteFile(socket, directoryPath, transferFileName);

		} catch (BindException e) {
			logger.error("Port address " + portNumber
					+ " is already in used.Please try with some other port address.\n Detail error message as below\n"
					+ e.getMessage());
		} catch (ConnectException e) {
			logger.error("Connection problem to connect server .Detail error message as below " + e.getMessage());
		} catch (UnknownHostException e) {
			logger.error("Couldn't bind client socket connection to unknown host " + hostIpAddress
					+ " Detail error message as below" + e.getMessage());
		} catch (IOException e) {
			logger.error("Unable to read or write file on client . Detail error message as below " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Client socket error occur. Detail error message as below \n" + ex.getMessage());
		} finally {
			if (socket != null)
				socket.close();
		}
		return isFileTransfer;
	}

	/**
	 * <h3>This method read file from client machine and write as stream to
	 * socket</h3>
	 * <li>Read transfer files from client specified directory</li>
	 * <li>Convert file to byte array</li>
	 * <li>Stored client machine file details to transfer server
	 * <li>Transfer file name</li>
	 * <li>File checksum to verify same file transfer at server side</li>
	 * <li>Transfer file as byte array</li></li>
	 * <li>Write file details to server</li>
	 * <li>Read server file transfer response</li>
	 * <li>If file transfer successfully then delete file from client machine
	 * otherwise transfer file once again</li>
	 * 
	 * @param socket
	 * @param fileName
	 * @param directoryPath
	 * @throws IOException
	 */
	public static boolean readWriteFile(Socket socket, String directoryPath, String fileName) throws IOException {
		boolean isFileTransfer = false;
		ObjectOutputStream outputStream = null;
		// File reader
		InputStream fileInputStream = null;
		// Socket stream reader
		InputStream socketInputStream = null;

		// Modify file path as per client machine operating system
		String finalFilePathWithFileName = directoryPath.concat(Constants.DIRECTORY_SEPERATOR).concat(fileName);
		logger.info("Client transferring file " + finalFilePathWithFileName);
		/** create file object , create byte array of same size */
		File file = new File(finalFilePathWithFileName);
		byte fileDataArray[] = new byte[(int) file.length()];
		logger.info("Convert file to byte array ");
		/**
		 * Generate checksum of transfer file pass as input argument File Checksum used
		 * to compare correct file receive at server side Stored file details as file
		 * checksum and file name,file byte array in object to send on server
		 */
		FileDetails fileDetails = new FileDetails();
		String md5 = checkSumMD5(finalFilePathWithFileName);
		logger.info("Client file checksum for transfer file " + fileName + ":\t" + md5);
		fileDetails.setFileName(fileName);
		fileDetails.setMd5(md5);

		/**
		 * Read the file in byte array ,store it in object of fileDetails and write it
		 * on socket
		 */
		try {
			fileInputStream = new FileInputStream(finalFilePathWithFileName);
			fileInputStream.read(fileDataArray);
			fileDetails.setFileData(fileDataArray);

			outputStream = new ObjectOutputStream(socket.getOutputStream());
			/** Write file to server */
			logger.info("Client : Sending file to server.....");
			outputStream.writeObject(fileDetails);
			logger.info("Client: Send file to server with below details");
			logger.info("File Name: " + fileDetails.getFileName());
			logger.info("File MD5 checksum: " + fileDetails.getMd5());
			logger.info("File size : " + fileDetails.getFileData().length);
			/** Read the response from server for file to be deleted or not */
			socketInputStream = socket.getInputStream();
			int responseFromServer = socketInputStream.read();

			// check response is 200 or not
			if (responseFromServer == 200) {
				isFileTransfer = true;
				logger.info("Successfully transfer file '" + fileName + "' to server");
				fileInputStream.close();
				File fileTobeDeleted = new File(finalFilePathWithFileName);
				// Delete file from client machine as file successfully transfer to server
				boolean isFileDelete = fileTobeDeleted.delete();
				if (isFileDelete)
					logger.info("Delete file '" + fileName
							+ "' successfully from client after successfully transfer file to server.");
				else
					logger.error("Failed to delete file '" + fileName
							+ "' from client machine after successfully transfer file to server");
			} else {
				logger.error("Failed to transfer file '" + fileName
						+ "' from client to server. Please verify server up and running ");
			}
		} catch (FileNotFoundException e) {
			logger.error("Unable to find input file to transfer.\n Detail error message as below" + e.getMessage());
		} catch (IOException e) {
			logger.error("Unable to read or write file on client. \n Detail error message as below" + e.getMessage());
		} finally {
			if (outputStream != null)
				outputStream.close();
			if (socketInputStream != null)
				socketInputStream.close();
		}
		return isFileTransfer;
	}

	/**
	 * This method is used to generate MD5 checksum of file This checksum used to
	 * compare correct file transfer and received over client and server
	 * 
	 * @param filePath
	 *            - Input file location
	 * @return File checksum
	 */
	public static String checkSumMD5(String filePath) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Files.readAllBytes(Paths.get(filePath)));
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

	public static boolean deleteFile(String filePath) {
		return new File(filePath).delete();
	}
	
	/**
     * Creates the key managers required to initiate the {@link SSLContext}, using a JKS keystore as an input.
     *
     * @param filepath - the path to the JKS keystore.
     * @param keystorePassword - the keystore's password.
     * @param keyPassword - the key's passsword.
     * @return {@link KeyManager} array that will be used to initiate the {@link SSLContext}.
     * @throws Exception
     */
	protected static KeyManager[] createKeyManagers(String filepath, String keystorePassword, String keyPassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        InputStream keyStoreIS = new FileInputStream(filepath);
        try {
            keyStore.load(keyStoreIS, keystorePassword.toCharArray());
        } finally {
            if (keyStoreIS != null) {
                keyStoreIS.close();
            }
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyPassword.toCharArray());
        return kmf.getKeyManagers();
    }

    /**
     * Creates the trust managers required to initiate the {@link SSLContext}, using a JKS keystore as an input.
     *
     * @param filepath - the path to the JKS keystore.
     * @param keystorePassword - the keystore's password.
     * @return {@link TrustManager} array, that will be used to initiate the {@link SSLContext}.
     * @throws Exception
     */
    protected static TrustManager[] createTrustManagers(String filepath, String keystorePassword) throws Exception {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        InputStream trustStoreIS = new FileInputStream(filepath);
        try {
            trustStore.load(trustStoreIS, keystorePassword.toCharArray());
        } finally {
            if (trustStoreIS != null) {
                trustStoreIS.close();
            }
        }
        TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustFactory.init(trustStore);
        return trustFactory.getTrustManagers();
    }
}
