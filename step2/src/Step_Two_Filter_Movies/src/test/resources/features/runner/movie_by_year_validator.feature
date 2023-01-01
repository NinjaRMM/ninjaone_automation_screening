Feature: Required arguments are valid or not
  I whan to check if all requires parameters are valid or not

  Scenario: Valid Parameters
    Given I have a MoviesByYearArgsValidator
    When I have the following parameters list
    |--decade=1980| --output=80s-movies.json|
    Then No exception should be thrown
    
   Scenario: Missing Decade Parameter
    Given I have a MoviesByYearArgsValidator
    When I have the following parameters list
    |--aecade=1980| --output=80s-movies.json|
    Then An exception should be thrown

   Scenario: Missing Output Parameter
    Given I have a MoviesByYearArgsValidator
    When I have the following parameters list
    |--decade=1980| --autput=80s-movies.json|
    Then An exception should be thrown
    
   Scenario: Bigger Decade Parameter
    Given I have a MoviesByYearArgsValidator
    When I have the following parameters list
    |--decade=3080| --output=80s-movies.json|
    Then An exception should be thrown

   Scenario: Smaller Decade Parameter
    Given I have a MoviesByYearArgsValidator
    When I have the following parameters list
    |--decade=1080| --output=80s-movies.json|
    Then An exception should be thrown    