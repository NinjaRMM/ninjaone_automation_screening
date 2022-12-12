Feature: Six Degrees of Separation

Scenario: I enter one actor's name
  Given I provide one actor's name as a parameter "Tom Cruise"
  And I should see the number of degrees of separation from "Kevin Bacon"
  And I run the application
  And "ninjaone_automation_screening/data/80s-movies.json" exists from step one
  And I see a list of movies describing the degree steps

# There are 2 degrees of separation between Tom Cruise and Sylvester Stallone
# Tom Cruise starred with Tom Skerrit in Top Gun.
# Tom Skerrit starred with Dolly Parton in Steel Magnolias
# Dolly Parton starred with Sylvester Stallone in Rhinestone

 Scenario: I enter one actor's name that did not star in a movie
   Given I provide one actor's name as a parameter "Tom Cruiseee"
   And I should see the number of degrees of separation from "Kevin Bacon"
   And I run the application
   And "ninjaone_automation_screening/data/80s-movies.json" exists from step one
   And I should see a message stating that name did not star in a movie

