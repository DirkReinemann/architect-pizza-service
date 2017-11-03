# architect-pizza-service

Website for a fictional pizza delivery service.

# commands

| command | description |
| - | - |
| mvn clean package tomee:run | Starts the application under http://localhost:8080/architect-pizza-service/index.xhtml |
| mvn clean package tomee:run -P skip-tests | Same as above but skips tests |
| mvn sql:execute -P db-init | Initializes the mysql database. You have to change mysql.user and mysql.password in the pom.xml |
| mvn sql:execute -P db-drop | Removes the mysql database. You have to change mysql.user and mysql.password in the pom.xml |
