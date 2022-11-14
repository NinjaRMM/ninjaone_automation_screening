# Getting Started

### Requirements

- Java 11+
- Maven 3.6.0+

### How to use

Build:
`mvn clean install`

Execute:
`java -jar target/movieshistory-0.0.1-SNAPSHOT.jar [DECADE]`

Where:

`[DECADE]` a valid decade, like 70 or 80

Example:
`java -jar target/movieshistory-0.0.1-SNAPSHOT.jar 80`

The application has an internal movie file list, but it's possible to put the file in the data folder too to be used by application.
Put the file `movies.json` in `data` folder if want process with your own movie list.
