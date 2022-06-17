Feature: Filter movies by decade

Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide a decade like 80 as a parameter
Then a file is created called "80s-movies.json" in the data folder
And it contains a JSON array of movies from "movies.json" from the years 1980-1989
