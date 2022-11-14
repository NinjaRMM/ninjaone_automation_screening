# Getting Started

### Requirements

- Java 11+
- Maven 3.6.0+

### How to use

Build:
`mvn clean install`

Execute:
`java -jar target/sixdegrees-0.0.1-SNAPSHOT.jar [DECADE] [ACTOR 1] [ACTOR 2]`

Where:

`[DECADE]` a valid decade, like 70 or 80

`[ACTOR 1] and [ACTOR2]` a actor full name with quotes, like "Tom Cruise" or "Sylvester Stallone"

Example:
`java -jar target/sixdegrees-0.0.1-SNAPSHOT.jar 80 "Tom Cruise" "Sylvester Stallone"`

The application has a internal movie file list, but this can be passed by argument too, as:
`java -jar target/sixdegrees-0.0.1-SNAPSHOT.jar [DECADE] [MOVIE FILE JSON] [ACTOR 1] [ACTOR 2]`

Example:
`java -jar target/sixdegrees-0.0.1-SNAPSHOT.jar 80 movies.json "Tom Cruise" "Sylvester Stallone"`

Instead of pass the movies file by parameter, it's possible to put the file in the data folder too to be used by application.
