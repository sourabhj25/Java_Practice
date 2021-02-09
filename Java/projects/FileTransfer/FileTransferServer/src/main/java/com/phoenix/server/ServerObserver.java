package com.phoenix.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

/**
 * @author nilesh
 */

/**
 * This class observe for client to send file If client send any new file then
 * read file and write to server
 */
public class ServerObserver {

	public static Logger logger = Logger.getLogger(ServerObserver.class);
	// Registering the JSSE provider				
	private static SSLContext sslcontext;
		
	public static void main(String[] args) {
	
		logger.info("In side server setup");
		int port = 6666;// Set default port
		String directoryPath = null;
		String serverCertificatePath=null;
		String keyStorePassword=null;
		String keyPassword=null;
		if (args.length < 5) {
			logger.error(
					"Server connection details are not provided.Please pass input argument as 'Server Port' 'File write directory path' ");
		} else {
			for (int i = 0; i < args.length; i++) {
				port = Integer.parseInt(args[0]);
				directoryPath = args[1];
				serverCertificatePath=args[2];
				keyStorePassword=args[3];
				keyPassword=args[4];
			}
			logger.info("Waiting for client to connect......");
			connectClients(port, directoryPath,serverCertificatePath,keyStorePassword,keyPassword);
		}
	}

	/**
	 * This method connect to available client
	 * It will make sure client-server connection should not break
	 * @param portNumber
	 *            : Required to connect client
	 * @param directoryPath
	 *            : Required to write file
	 * @throws IOException
	 */
	public static void connectClients(int portNumber, String directoryPath,String certificatePath,String keyStorePassword,String keyPassword) {
		SSLServerSocket serverSocket = null;
		
		 ServerSocketFactory ssf = null;
			try {
				if(serverSocket==null)
				   {
					sslcontext = SSLContext.getInstance("TLS");
					sslcontext.init(createKeyManagers(certificatePath, keyStorePassword, keyPassword), createTrustManagers(certificatePath, keyStorePassword), new SecureRandom());
		            ssf = sslcontext.getServerSocketFactory();
		            serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber);
					logger.info("Create new socket connection ");
				   }
				while (true) 
				{
					SSLSocket socket = (SSLSocket) serverSocket.accept();
					logger.info("Accepting client connection ");
					new Server(socket, directoryPath).start();
				}
			} catch (Exception ex) {
				//Create new connection if exception occur so that client -server connection should not break 
				logger.info("Server connection exception occur .Detail message as below "+ex.getMessage());
				if (serverSocket != null)
					try {
						serverSocket.close();
						serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber);
						logger.info("Close connection and Create new connection after server connection exception");
					} catch (IOException e) {
						logger.info("Exception occur after closing connection . Detail error message as below"+e.getMessage());
					}
			} finally {
				//Create new connection if exception occur so that client -server connection should not break 
				logger.info("Closing socket connection");
				if (serverSocket != null)
					try {
						serverSocket.close();
						logger.info("Closed socket connection");
						serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber);
					} catch (IOException e) 
				    {
						logger.info("Exception occur after closing connection . Detail error message as below"+e.getMessage());
					}
			}
		}
	
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


