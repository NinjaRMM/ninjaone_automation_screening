Feature: Filter movies by genre

Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide the word "genres" title as a parameter
And I add a "-" in front of the "genres" word with no spaces
And I provide the desired genre as a parameter
And the genre is written with no spaces
Then a file is created called "moviesFiltered.json" in the step2/src/data folder
And it contains a JSON array of movies from "movies.json" that has all movies with that genre
