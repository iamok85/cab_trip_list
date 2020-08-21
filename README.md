# Rest API for Retrieving Cab Trip Summary
 
Technology Stack: Java 1.8 Mysql Springboot Hibernate Gradle
 
Summary: This project is called cabtripapp. It has two sub projects,server and client respectively. 
         Server is building rest Apis and Client is building a console command to consume the rest Apis
         
Prerequisite:
         Please install Mysql database and import data from ny_cab_data_cab_trip_data_full.sql.  The database name should be ny_cab_data and using the default table name from the sql script. The root username is 'root' and the password is 'password'. However you can configure the username and password in cabtripapp/server/src/main/resources/application.properties.
         Also install Java1.8 and gradle https://gradle.org/install/ 
   
Configuration:
       In cabtripapp/server/src/main/resources/application.properties, You can specify database url, username, password and port       

## How to build

Change permission of script gradlew

	chmod +x gradlew

Build with Gradle wrapper:

	./gradlew clean build

## How to run, please run the server project first then run the client project

Run Server with Gradle wrapper:

	./gradlew server:bootRun 
    
Or run server it as an executable jar:

 	java -jar cabtripapp/server/build/libs/server-1.0.0.jar

Run Client as an excecutable jar 
 
 	java -jar client/build/libs/client-1.0.0.jar help 
 	java -jar client/build/libs/client-1.0.0.jar http://localhost:8080 2013-12-05 true

## Testing Server with Curl
	curl http://localhost:8080/cabtripcountpickup?pickupdate=2013-12-05&cache=true

