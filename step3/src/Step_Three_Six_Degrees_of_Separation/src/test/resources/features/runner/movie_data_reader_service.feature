Feature: Validade the Movie Json Data Reader
  Verify if the reading and filtering in Reader are working as expected.

  Scenario: There is no movies.json file.
    Given I create a new raeder for file nonexistentFile.json
    When I call PostConstruct method
    Then An reader exception should be thrown
    
  Scenario: There is a movies.json file.
    Given I create a new raeder for file ./src/test/resources/json_movies/exist.json
    When I call PostConstruct method
    Then No reader exception should be thrown

  Scenario: Read all 1970s movies from movies.json
    Given I create a new raeder for file ./src/test/resources/json_movies/exist.json
    When I fetch all movies by decade 1970
    Then I count the amount of books found 1
    Then I verify the year of books found 1970
    Then No reader exception should be thrown

  Scenario: Read all 1990s movies from movies.json
    Given I create a new raeder for file ./src/test/resources/json_movies/exist.json
    When I fetch all movies by decade 1990
    Then I count the amount of books found 3
    Then I verify the year of books found 1990
    Then No reader exception should be thrown
    