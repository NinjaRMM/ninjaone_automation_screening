Feature: Six Degrees of Separation

  Scenario: I enter two actors that worked together
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Robert Hays,Julie Hagerty
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 1
		Then I see a list of movies describing the degree steps 1
		
  Scenario: I enter two actors that do not worked together
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Robert Hays,Tisa Farrow
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 0
		Then I see a list of movies describing the degree steps 0
			  
	Scenario: I enter two actors that have 2 degrees
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Tom Cruise,Julie Hagerty
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 2
		Then I see a list of movies describing the degree steps 2
		
  Scenario: I enter two actors that have 3 degrees
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Tom Cruise,Robert Hays
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 3
		Then I see a list of movies describing the degree steps 3
		
  Scenario: I enter two actors that have 4 degrees
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Sylvester Stallone,Robin Riker
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 4
		Then I see a list of movies describing the degree steps 4
			  	  
  Scenario: I enter two actors that have 5 degrees
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Sylvester Stallone,Dennis Christopher
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 5
		Then I see a list of movies describing the degree steps 5
			  
  Scenario: I enter two actors that have 6 degrees
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Sylvester Stallone,Linda Kerridge
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 6
		Then I see a list of movies describing the degree steps 6

  Scenario: I enter one actors that have 1 degrees with Kevin Bacon
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Teri Hatcher
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 1
		Then I see a list of movies describing the degree steps 1
				
  Scenario: I enter one actors that have 2 degrees with Kevin Bacon
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Sylvester Stallone
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 2
		Then I see a list of movies describing the degree steps 2
		
  Scenario: I enter two actor's name that one did not star in a movie
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Sylvester Stallone,Lindo Xishzu
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 0
		Then I see a list of movies describing the degree steps 0
		
  Scenario: I enter one actor's name that did not star in a movie
    Given I run the application
    And "80s-movies.json" exists from step one
	  When I provide two actors names as a parameters --actors=Lindo Xishzu
		Then I should see a generated summary
		Then I should see the number of degrees of separation between the two actors 0
		Then I see a list of movies describing the degree steps 0		