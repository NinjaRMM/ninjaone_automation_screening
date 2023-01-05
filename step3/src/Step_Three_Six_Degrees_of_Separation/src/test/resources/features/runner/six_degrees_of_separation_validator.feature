Feature: Required arguments are valid or not
  I want to check if all requires parameters are valid or not

  Scenario: Valid Parameters two actors
    Given I send actors to validate --actors=Tom Cruise,Sylvester Stallone
    When I execute validation with parameters list
    Then No actors exception should be thrown
    
  Scenario: Valid Parameters one actors
    Given I send actors to validate --actors=Tom Cruise
    When I execute validation with parameters list
    Then No actors exception should be thrown
    
  Scenario: Invalid Parameters three actors
    Given I send actors to validate --actors=Tom Cruise,Sylvester Stallone,Bill Rich
    When I execute validation with parameters list
    Then An actors exception should be thrown

  Scenario: Invalid Arg name three actors
    Given I send actors to validate --actor=Tom Cruise,Sylvester Stallone
    When I execute validation with parameters list
    Then An actors exception should be thrown

  Scenario: Invalid Parameters no actors
    Given I send actors to validate --actors=
    When I execute validation with parameters list
    Then An actors exception should be thrown