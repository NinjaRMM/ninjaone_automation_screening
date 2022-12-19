# Step3

Notes:
- Due to the complexity of the algorithm, on this step I decided to bring everything to memory to use Maps
- A strategy similar to the step2 could be used, but it would increase complexity to avoid reading the whole file multiples times

To execute the program, run:
```sh
java -jar bin/step3.jar Actor1,Actor2 # Actor2 is optional. Default=Kevin Bacon
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