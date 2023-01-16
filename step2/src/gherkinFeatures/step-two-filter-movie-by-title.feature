Feature: Filter movies by title

Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide the word "title" as a parameter
And I add a "-" in front of the "title" word with no spaces
And I provide a movie title as a parameter
And the title is written with no spaces
Then a file is created called "moviesFiltered.json" in the step2/src/data folder
And it contains a JSON array of movies from "movies.json" that has the title presented
