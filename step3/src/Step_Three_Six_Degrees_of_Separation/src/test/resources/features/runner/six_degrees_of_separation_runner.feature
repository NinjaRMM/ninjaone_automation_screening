Feature: Six Degrees of Separation

  Scenario: I enter two actors that worked together
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Robert Hays,Julie Hagerty

  Scenario: I enter two actors that do not worked together
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Robert Hays,Tisa Farrow