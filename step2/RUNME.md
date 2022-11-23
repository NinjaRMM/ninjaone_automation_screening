#ninjaone automation step2

###Environment requirements
* Java 11+
* Gradle 7+

###Run automated tests
You can use the following command to execute the automated tests:

```shell script
.\gradlew clean runFeatures
```

###Run application
You can use the following command to execute the application:

```shell script
.\gradlew run --args='80 80s-movies.json'
```

the decades 00s and 10s will use the years 2000 and 2010 respectively,
if you want to use 10s from 1910 for example, 
you need to pass an explicit args:

```shell script
.\gradlew run --args='1910 1910s-movies.json'
```