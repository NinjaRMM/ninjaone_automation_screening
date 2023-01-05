package com.gbvbahia.ninjarmm.service.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import java.util.stream.Collectors;
import com.gbvbahia.ninjarmm.model.Movie;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Movies80ServiceReaderServiceRunSteps {

  private Movies80ServiceReaderService subject;
  private List<Movie> movies;
  
  @Before
  public void setUp() throws Exception {
    subject = new Movies80ServiceReaderService("./src/test/resources/json_movies", "exist.json");
    movies = null;
  }
  
  @Given("^I call the get80sMovies method$")
  public void givenGet80sMovies() throws Exception {
    movies = subject.get80sMovies();
  }
  
  @Then("^The movies list should not be null$")
  public void thenMoviesShouldNotBeNull() {
    assertNotNull(movies);
  }
  
  @Then("^The movies list should be with (\\d+)$")
  public void thenMoviesShouldBeWithSize(int sizeList) {
    assertEquals(sizeList, movies.size());
  }
  
  @Then("^The movies list should be with yeas and count (\\d+) (\\d+)$")
  public void thenMoviesShouldBeWithYearsCount(int year, int count) {
    List<Movie> filtered = movies.stream().filter(m -> m.getYear() == year).collect(Collectors.toList());
    assertEquals(count, filtered.size());
  }
}
