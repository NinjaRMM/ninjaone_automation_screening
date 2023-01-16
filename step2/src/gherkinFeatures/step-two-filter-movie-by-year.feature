Feature: Filter movies by year

Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide the word "year" title as a parameter
And I add a "-" in front of the "year" word with no spaces
And I provide a year as a parameter
And the year is written with no spaces
Then a file is created called "moviesFiltered.json" in the step2/src/data folder
And it contains a JSON array of movies from "movies.json" that has all movies produced in that year
