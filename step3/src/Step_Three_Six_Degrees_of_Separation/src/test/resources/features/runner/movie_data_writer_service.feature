Feature: Validade the Movie Json Data Writer
  Verify if the writer is working as expected.

  Scenario: Write two movies and check file content
    Given I create a new writer for folder ./src/test/resources/json_movies/
    When I call begin method twoDelete.json
    When I call process method with movie
    |title|year|cast|genres|
    |Angel Town|1990|Olivier Gruner,Theresa Saldana|Action|
    |Almost Famous|2000|Billy Crudup,Kate Hudson|Comedy,Drama|
    When I call close method
    Then No writer exception should be thrown
    Then I check if the file was written
    Then I check the file size 2
    