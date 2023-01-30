## Instructions to run the application

- Install JDK 11+
- Go to the 'filter-movies' directory. There will be a specific file there: 'app.jar'
- From terminal execute the following command:
```console
user@computer:~$ java -jar app.jar
```

- You will be asked to inform a decade. Inform some value among 0, 10, 20, 30, ..., 90. Press enter and the filtered JSON file will be generated into 'data' folder.
- To run Unit Tests go to 'filter-movies' directory and execute the following command:
```console
user@computer:~$ ./gradlew test
```