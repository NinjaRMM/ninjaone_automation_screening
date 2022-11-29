## Requirements
- Java 11+
- Maven 3.6.3+

## Execute
open the terminal in degree-of-separations folder

run the command: 'mvn clean install spring-boot:run'

It's a GET Rest API service, by browse or Postman use this url http://localhost:8080/movies/80

the last parameter '80' is the decade

the response will be the movies from 80s and a file will be created in data directory named 80s-movies.json