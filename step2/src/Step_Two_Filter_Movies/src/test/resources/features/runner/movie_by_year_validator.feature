Feature: Required arguments are valid or not
  I whan to check if all requires parameters are valid or not

  Scenario: Valid Parameters
    Given I send args to validate
     |--decade=1980| --output=80s-movies.json|
    When I execute validation with parameters list
    Then No exception should be thrown
    
   Scenario: Missing Decade Parameter
    Given I send args to validate
    |--aecade=1980| --output=80s-movies.json|
    When I execute validation with parameters list
    Then An exception should be thrown

   Scenario: Missing Output Parameter
    Given I send args to validate
    |--decade=1980| --autput=80s-movies.json|
    When I execute validation with parameters list
    Then An exception should be thrown
    
   Scenario: Bigger Decade Parameter
    Given I send args to validate
    |--decade=3080| --output=80s-movies.json|
    When I execute validation with parameters list
    Then An exception should be thrown

   Scenario: Smaller Decade Parameter
    Given I send args to validate
    |--decade=1080| --output=80s-movies.json|
    When I execute validation with parameters list
    Then An exception should be thrown
    
   Scenario: Invalid Decade Parameter
    Given I send args to validate
    |--decade=1981| --output=80s-movies.json|
    When I execute validation with parameters list
    Then An exception should be thrown

   Scenario: Already Exists Output File
    Given I send args to validate
    |--decade=1980| --output=exist.json|
    When I execute validation with parameters list
    Then An exception should be thrown    