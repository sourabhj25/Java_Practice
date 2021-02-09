/**
 * 
 */
package com.phoenix.client;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author nilesh
 * 
 *         <H2>Implementation of this class observe client machine directory to
 *         transfer file from client to server</H2>
 *         <ol>
 *         <li>Observe if new file created in directory</li>
 *         <li>If new file created send file to server</li>
 *         <li>Once file transfer to server delete file from client directory
 *         </li>
 *         </ol>
 */
public class ClientFilesObserver {

	public static Logger logger = Logger.getLogger(ClientFilesObserver.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String remoteHostIP = null;
		int port = 6666; // By Default set port number as 6666
		String fileDirectoryPath = null;
		String serverCertificatePath=null;
		String keyStorePassword=null;
		String keyPassword=null;
		try {
			if (args.length < 6) {
				logger.error(
						"Please provide client server connection details.Pass input argument as 'Server IP address' 'Server port' 'File transfer directory path' 'Trusted certificate path','KeyStore Password','Key Password' ");
			} else {
				for (int i = 0; i < args.length; i++) {
					remoteHostIP = args[0];
					port = Integer.parseInt(args[1]);
					fileDirectoryPath = args[2];
					serverCertificatePath=args[3];
					keyStorePassword=args[4];
					keyPassword=args[5];
				}
				directoryWatcher(remoteHostIP, port, fileDirectoryPath,serverCertificatePath,keyStorePassword,keyPassword);
			}
		} catch (Exception e) {
			logger.error(
					"Server connection details are not correct.Please pass input argument as 'Server IP address' 'Server port' 'File transfer directory' 'Trusted certificate path' 'KeyStore Password' 'Key Password' ");
		}

	}

	/**
	 * DirecoryWatcher method used to monitor directory for file system events such
	 * as new file created,file modified,file deleted inside directory
	 * <li>if any new file created inside directory send file to sever</li>
	 * <li>Once file transfer to server successfully delete file from client</li>
	 * 
	 * @param hostIpAddress
	 *            : IP address of server machine
	 * @param portNumber
	 *            : socket port number
	 * @param direcortyPath
	 *            : Direc
	 */
	public static void directoryWatcher(String hostIpAddress, int portNumber, String directoryPath,String certificatePath,String keyStorePassword,String keyPassword) {
		try {
			DirectoryWatchService watchService = new SimpleDirectoryWatchService();
			watchService.register( // May throw
					new DirectoryWatchService.OnFileChangeListener() {

						@Override
						public void onFileCreate(String filePath) {
							logger.info("'" + filePath + "' New file Added inside directory " + directoryPath);
							try {
								logger.info("Sending file to server.....");
								// Send file to server
								if (Client.sendFile(hostIpAddress, portNumber, directoryPath, filePath,certificatePath,keyStorePassword,keyPassword)) {
									logger.info("File transfer to server successfully");
									/*boolean isFileDelete = Client.deleteFile(filePath);
									if (isFileDelete)
										logger.info("Delete file '" + filePath + "' successfully");
									else
										logger.error("Failed to delete file '" + filePath + "' from client machine");*/
								}
								else
								{
									logger.info(filePath +" File transfer to server failed");
								}
							} catch (IOException e) {
								logger.info( "File transfer operation failed. below is details error message\n"
										+ e.getMessage());
							}
						}

						@Override
						public void onFileModify(String filePath) {
							logger.info(filePath + " File Modified .. ");
						}

						@Override
						public void onFileDelete(String filePath) {
							logger.info(filePath + " File Deleted ....");
						}
					}, directoryPath, "*.*");
			watchService.start();
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					watchService.stop();
					break;
				}
			}
		} catch (IOException e) {
			logger.error("File not found in transfer direcorty.Detail error message as below" + directoryPath + ". ");
		}
	}

}
