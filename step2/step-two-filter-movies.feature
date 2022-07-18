Feature: Filter movies by decade

Scenario_1: Get the movies from 1980-1989
Given a json file of all movies between 1900-2018 "movies.json"
And I run the application
When I provide a decade like 80 as a parameter
Then a file is created called "80s-movies.json" in the data folder
And it contains a JSON array of movies from "movies.json" from the years 1980-1989

Scenario_2: Get the moveis from 1970 to 2005 with name starts with letter A
Given a json file of all movies from 1970 to 2005 "movies.json"
When I write a query to get the movies from 1970 to 2005 with only letter A with parameter alphabetA
Then a file is created called "alphabetA" from the years 1970 to 2005
And it contains a JSON array of movies from "movies.json" from the yers 1970 to 2005 which contains moveies with letter A

Scenario_3: Get the movies from any 10 years of period.
Given a json file fo all movies between 1900 to 2018
When I provide a parameter like 10
Then a file is created called "10_decade.json" in the data folder
THen give a query of any period for ten yers as desired (say 2970-1980)
And it contains a JSON array of movies from "movies.json" from any desired period of decade



