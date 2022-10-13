# Step 3

Implements an application that solves the Six Degrees of Kevin Bacon problem in Java, according to the [provided specification](../step-three-six-degrees.feature).

## Requirements

- Java JDK 16

Useful links:
- [Java JDK Download](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)

## Usage

The `SixDegreesofKevinBacon-1.0-SNAPSHOT-jar-with-dependencies.jar` file is available at the `ninjaone_automation_screening/step3/src/SixDegreesofKevinBacon/target` [path](../src/SixDegreesofKevinBacon/target).

Navigate to the above path, then run:


```bash
java -jar SixDegreesofKevinBacon-1.0-SNAPSHOT-jar-with-dependencies.jar -p "C:\Path\to\json\80s-movies.json -s "Tom Cruise"
```

or, for example: 

```bash
java -jar SixDegreesofKevinBacon-1.0-SNAPSHOT-jar-with-dependencies.jar -p "C:\Path\to\json\80s-movies.json -s "Tom Cruise" -s "Sylvester Stallone"
```

Where: 
- `-p` or `--path` is the parameter to pass the path to the source json file.
- `-s` or `--star` is the parameter to pass the Movie Stars.

The `80s-movies.json` can be found [here](../../data/80s-movies.json), or generated with the [Step 2 application](../../step2).

## Compiling

The .jar file of the implementation was provided to make the application execution easier. Still, if you want to compile the code by yourself, here follows the instructions:

To compile the project you need the `Maven 3.8.6` installed.

Navigate to the [main project path](./SixDegreesofKevinBacon) (`ninjaone_automation_screening/step3/src/SixDegreesofKevinBacon`), and then run:

```bash
 mvn clean compile assembly:single
```

The above command will compile the Java code and generate the .jar file, with all dependences, inside the folder `target`:
`ninjaone_automation_screening/step3/src/SixDegreesofKevinBacon/target`

## Additional Comments
- This implementation covers strictly the specified requirements of the Step 3.
- A set of extra optimizations can be implemented, as well as tests.
- Example of some uncovered cases:
  - It does not cover when there are no connection between the stars.
  - It does not cover when same stars are given as parameter
  - It does not cover when more than 2 stars are given as parameter.
 
- I had a lot of fun implementing this Step! Let's talk about it :]
- Nice Easter egg! :]

## Questions
Feel free to [contact me](mailto:lualjsantos@gmail.com) if you have any questions.
