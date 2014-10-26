architect-pizza-service
------------------------------------

Here are the most useful Maven commands.

Maven Goal HowTo:

mvn clean package tomee:run

Cleans and packages the sources and deploys them to the integrated tomee.
Useful to test the application. You can access the web application under
http://localhost:8080/architect-pizza-service/index.xhtml

mvn clean package tomee:run -P skip-tests

The same as before but ignores JUnit tests.

mvn sql:execute -P db-init

Executes the init scripts for the MySQL database from the folder /sql. This
creates the database, tables, user and testdata. You hava to change the
mysql.user and mysql.password properties in the pom.xml.

mvn sql:execute -P db-drop

Executes the drop script for the MySQL database from the folder /sql. This
drops the database, tables and user. You have to change the
mysq.user and mysql.password properties in the pom.xml.
