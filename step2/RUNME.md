# Step2

Notes:
- I'm streaming the movies.json to avoid bringing everything to memory
- For the same reason, I'm streaming the output
- Unit tests use another movies.json (inside test/resources). Also, it uses temp folder for the output

To execute the program, run:
```sh
java -jar bin/step2.jar --decade=80 --output=/dir/filtered-movies.json
```
_PS.: I'm versioning the .jar inside `bin` folder so you don't have to compile it. Please note that I wouldn't do this in a "real project"_

To compile the project using Maven, run:
```sh
mvn package
```

To execute unit tests, run:
```sh
mvn test
```