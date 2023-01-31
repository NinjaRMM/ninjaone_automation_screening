Feature: Six Degrees of Separation

	Scenario Outline: I enter one actor's name
		Given "80s-movies.json" exists from step one
		When  I provide actor's name "Tom Cruise"
		Then I should see the number of degrees of separation from "Kevin Bacon"
		And I see a list of movies describing the degree steps

# Original Info
# There are 2 degrees of separation between Tom Cruise and Kevin Bacon
# Ralph Macchio starred with Tom Cruise in The Outsiders
# John Lithgow starred with Ralph Macchio in Distant Thunder
# Kevin Bacon starred with John Lithgow in Footloose.

# Actual Info
# There is actually 1 degree as the Kevin bacon's algorithm is to try to find the shortest path between the two actors.
# Chris Penn starred with Tom Cruise in All the Right Moves
# Kevin Bacon starred with Chris Penn in Footloose.

	Scenario Outline: I enter two actors' name
		Given "80s-movies.json" exists from step one
		When  I provide actor name "Tom Cruise" and "Sylvester Stallone"
		Then I should see the number of degrees of separation between the two actors
		And I see a list of movies describing the degree steps
		
# Original Info
# There are 2 degrees of separation between Tom Cruise and Sylvester Stallone
# Tom Cruise starred with Tom Skerritt in Top Gun.
# Tom Skerrit starred with Dolly Parton in Steel Magnolias
# Dolly Parton starred with Sylvester Stallone in Rhinestone

# Actual Info
# Easter was found, =). But indeed 2 degrees with other movies as instance
# Tom Cruise starred with Tom Skerritt in Top Gun.
# Tom Skerrit starred with Dolly Parton in Steel Magnolias
# Dolly Parton starred with Sylvester Stallone in Rhinestone

	Scenario Outline: I enter one actor's name that did not star in a movie
		Given "80s-movies.json" exists from step one
		When  I provide actor's name "Buzz Lightyear"
		Then I should see a message stating that name did not star in a movie

# Buzz Lightyear did not star in a movie in the data provided.

	Scenario Outline: I enter one actor's name that did not star in a movie
		Given "80s-movies.json" exists from step one
		When  I provide actor name "Tom Cruise" and "Buzz Lightyear"
		Then I should see a message stating that name did not star in a movie

# Buzz Lightyear did not star in a movie in the data provided.