Feature: Six Degrees of Separation

Scenario: I enter one actor's name
  Given I run the application
  And  "80s-movies.json" exists from step two
  When I provide one actor's name as a parameter "Tom Cruise"
  Then I should see the number of degrees of separation from Kevin Bacon
  And I see a list of movies describing the degree steps

#There are 2 degrees of separation between Tom Cruise and Kevin Bacon.
#Tom Cruise starred with Chris Penn in All the Right Moves
#Chris Penn starred with Kevin Bacon in Footloose

Scenario: I enter two actors' name
  Given I run the application
  And  "80s-movies.json" exists from step two
  When  I provide two actors' names as a parameters "Tom Cruise" and "Sylvester Stallone"
  Then I should see the number of degrees of separation between the two actors
  And I see a list of movies describing the degree steps

#There are 3 degrees of separation between Tom Cruise and Sylvester Stallone.
#Tom Cruise starred with Kelly McGillis in Top Gun
#Kelly McGillis starred with Kurt Russell in Winter People
#Kurt Russell starred with Sylvester Stallone in Tango & Cash

Scenario: I enter one actor's name that did not star in a movie
  Given I run the application
  And  "80s-movies.json" exists from step two
  When  I provide a name not in the data as a parameter "Buzz Lightyear"
  Then I should see a message stating that name did not star in a movie

# Buzz Lightyear did not star in a movie in the data provided.

Scenario: I enter one actor's name that did not star in a movie and the other did
  Given I run the application
  And  "80s-movies.json" exists from step two
  When  I provide two actors' names one known, and one not in the data as a parameters "Woody" and "Tom Cruise"
  Then I should see a message stating that name did not star in a movie

# Woody did not star in a movie in the data provided.
