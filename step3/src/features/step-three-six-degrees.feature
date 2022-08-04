Feature: Six Degrees of Separation

  Background:
    Given I run the application
    And  "80s-movies.json" exists from step one

  Scenario: I enter one actor's name
    When  I provide one actor's "<actor1>" as a parameter
    Then I should see the "<numberOfDegrees>" of separation from "<actor2>"
    And I see a list of movies describing the degree steps
    ```
    Tom Cruise starred with Kevin Bacon in A Few Good Men

    ```
    Examples:
      | actor1     | actor2      | numberOfDegrees                                                    |
      | Tom Cruise | Kevin Bacon | There is 1 degree of separation between Tom Cruise and Kevin Bacon |


  Scenario: I enter two actors' name
    When  I provide two actors' names as a parameters "<actor1>", "<actor2>"
    Then I should see the "<numberOfDegrees>" of separation between the two actors
    And I see a list of movies describing the degree steps
    ```
    Tom Cruise starred with Diane Lane in The Outsiders
    Diane Lane starred with Sylvester Stallone in Judge Dredd

    ```
    Examples:
      | actor1     | actor2             | numberOfDegrees                                                             |
      | Tom Cruise | Sylvester Stallone | There are 2 degrees of separation between Tom Cruise and Sylvester Stallone |

  Scenario: I enter one actor's name that did not star in a movie
    When  I provide a name not in the data as a parameter "<actorNotInData>"
    Then I should see a "<message>" stating that name did not star in a movie

    Examples:
      | actorNotInData | message                                                      |
      | Buzz Lightyear | Buzz Lightyear did not star in a movie in the data provided. |
      | Julio Verne    | Julio Verne did not star in a movie in the data provided.    |

  Scenario: I enter one actor's name that did not star in a movie
    When  I provide two actors' names one known, and one not in the data as a parameters "<actor1>", "<actor2>"
    Then I should see a "<message>" stating that name did not star in a movie

    Examples:
      | actor1         | actor2             | message                                              |
      | Buzz Lightyear | Sylvester Stallone | Buzz Lightyear did not star in a movie in the data provided. |
      | Julio Verne    | Oprah Winfrey      | Julio Verne did not star in a movie in the data provided.  |