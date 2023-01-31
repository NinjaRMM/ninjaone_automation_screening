## Background

This is simple automation application using Cucumber.

## Technical Approach

### Technologies

- Open JDK 17 version
- Spring Boot
- JUnit 5.x (Jupiter)
- Cucumber 7.x
- Lombok

### Configuring the environment
1. Installing Java 17 - Download JDK 
   1. Set JAVA_HOME env based on where the JDK will be installed. Add to PATH as JAVA_HOME\bin. 
   2. To know if the java is installed, run *java -version* command on prompt should work 
2. Installing Maven
   1. Download the latest version on https://maven.apache.org/download.cgi
   2. Define the variable MVN_HOME on your OS pointing to the extracted directory. Add to the PATH variable the MVN_HOME\bin.
   3. To know if the mvn is installed, run the command *mvn --version* and check if its recognition by the SO or not;
   
### Running

mvn test -Dtest="br.com.timoteosoutello.cucumber.RunStepTwoFilterMoviesTest" -Dcucumber.publish.enabled=true -Dcucumber.publish.quiet=true -Dcucumber.plugin="pretty, html:target/cucumber-reports/Cucumber.html, json:target/cucumber-reports/Cucumber.json, junit:target/cucumber-reports/Cucumber.xml"