Feature: Validade the Movies 80 Json Data Reader

  Scenario: Verify is reading the 80s-movies.json into Movies List
    Given I call the get80sMovies method
    Then The movies list should not be null
    Then The movies list should be with 23
    Then The movies list should be with yeas and count 1930 3
    Then The movies list should be with yeas and count 1940 4
    Then The movies list should be with yeas and count 1950 2 
    
    