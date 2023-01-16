Feature: Filter movies by cast

Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide the word "cast" as a parameter
And I add a "-" in front of the "cast" word with no spaces
And I provide an actor name as a parameter
And the actor name is written with no spaces
Then a file is created called "moviesFiltered.json" in the step2/src/data folder
And it contains a JSON array of movies from "movies.json" that has all the movies that the actor casted
