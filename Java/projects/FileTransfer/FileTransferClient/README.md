FileTrasfer project used to setup socket client server to send file on server
Executable command:
mvn clean install exec:java -DhostAddress=127.0.0.1 -Dport=6666 -DfileDir="/home/user/socket" -DcertificateFile="/home/user/trustedCertificate.jks" -DkeyStorePassword="password" -DkeyPassword="password"

hostAddress --> should as server machine IP address
port --> Port address on which socket to be run
fileDir --> File directory path of send file on server






Java Keytool - Create Keystore

1. Install the java if not installed

2. Perform the following command to generate keystore
	keytool -genkey -alias mydomain -keyalg RSA -keystore KeyStore.jks -keysize 2048

2. Answer each question when prompted.
   a) Keystore password
   b) First & Last Name 
   c) Organizational Unit
   d) Organization
   e) City or Locality
   f) State or Province
   g) Two letter country code

3. It will ask for the confirmation about whether the above information is correct or not?(Yes/NO)

4. Enter 'Yes' if correct


Note: The same keystore password is required while testing the application

To know more about keystore generation please refer the link given below,
	https://support.globalsign.com/customer/en/portal/articles/2121490-java-keytool---create-keystore

======================================================================================


Test Process,

1. Start the server by using following command,
	mvn clean install exec:java -Dport=6666 -DfileDir="/home/user/socket/send" -DcertificateFile="/home/user/trustedCertificate.jks"  -DkeyStorePassword="password" -DkeyPassword="password"

2. Start the client by using following command,
	mvn clean install exec:java -DhostAddress=127.0.0.1 -Dport=6666 -DfileDir="/home/user/socket" -DcertificateFile="/home/user/trustedCertificate.jks" -DkeyStorePassword="password" 
	-DkeyPassword="password"


Note: You must have directories at the specified path above

