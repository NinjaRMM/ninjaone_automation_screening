# How to execute SixDegrees.java

This solution uses org.json library. It is added as a dependency in the pom.xml file.
To create the SixDegrees.java the IntelliJ IDEA has been used. And it created it within the ninja package.
As an alternative here are provided the terminal steps:

+ Change the directory where the pom.xml file is located
```
cd ninjaone_automation_screening/step3
```

+ Run this command where the pom.xml file located
```
mvn clean install
```

+ Compile the java sources:
```
javac -classpath <path to jar files> <path to java source> SixDegrees.java
```

+ Execute it. 
``` 
java -classpath <path to ninja package classes>:<path to jar files> ninja.SixDegrees <n actors>
```
Where the argument *n actors* needs to be 1 or 2.

Note: it is important to not change of directory to let the program find the json file.