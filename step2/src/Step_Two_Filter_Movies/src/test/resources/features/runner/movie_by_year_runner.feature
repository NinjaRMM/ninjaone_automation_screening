Feature: Check the services reader and writer are called
  I want to check if all interactions between services are right and parameters

  Scenario: Valid Parameters
    Given I start movies by year runner decade output
     Given I send args to run
     |--decade=1980| --output=80s-movies.json|
    When I execute run on movies by year
    Then No exception on start runner should be thrown
    Then I check mockito services interactions 1 1980
