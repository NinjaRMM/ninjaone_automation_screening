## Requirements
- Java 18
- Maven 3.8

## Build
Before using the cli it is necesarry to go into the `six-degrees-kevin-bacon` folder and build the project using the `mvn clean package` command

## Usage
from the `six-degrees-kevin-bacon` folder:

```sh
java -jar target/six-degrees-kevin-bacon-1.0.jar -a1 <actor1> [-a2 <actor2>] [-i <inputFile>]
```

### Options:
-  -a1, --actor1    Name of the first actor (required)
-  -a2, --actor2    Name of the second actor (optional, defaults to "Kevin Bacon")
-  -i, --inputFile  Path to the movie data file (optional, defaults to "movies.json")

### Example:
```sh
java -jar target/six-degrees-kevin-bacon-1.0.jar -a1 "Tom Cruise" -a2 "Ben Kingsley" -i ../../movies.json
```

 output:
 ```
There are 2 degrees of separation between Tom Cruise and Ben Kingsley
Tom Cruise starred with Robert Downey, Jr. in Tropic Thunder
Robert Downey, Jr. starred with Ben Kingsley in Iron Man 3
 ```