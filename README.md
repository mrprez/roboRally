# roboRally

To launch se server, launch following maven commands:
* To create application: mvn clean package
* To initialiase hsqldb database: mvn resources:copy-resources@reset-hsqldb
* To launch tomcat server: mvn cargo:run
Then go to loacalhost:8080/roboRally
