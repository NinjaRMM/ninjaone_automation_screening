package com.gbvbahia.ninjarmm.service.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import com.gbvbahia.ninjarmm.model.Movie;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesDataReaderServiceRunSteps {

  private MoviesDataReaderService subject;
  private Exception exception;
  private List<Movie> movies;
  private WriterService<Movie> processMovie;
  
  @Before
  public void setUp() throws Exception {
    subject = null;
    exception = null;
    movies = new ArrayList<>();
    processMovie = (m) -> movies.add(m);
  }
  
  @Given("^I create a new raeder for file (.+)$")
  public void given(String moviesFile) throws Exception {
    subject = new MoviesDataReaderService(moviesFile);
  }
  
  @When("^I call PostConstruct method$")
  public void whenInit() {
    try {
      ReflectionTestUtils.invokeMethod(subject, "init");
    } catch (Exception e) {
      this.exception = e;
    }
  }
  
  @When("^I fetch all movies by decade (\\d+)$")
  public void whenFetchAllMoviesByDecade(int decadeInit) {
    try {
      subject.fetchAllMoviesByDecade(decadeInit, processMovie);
    } catch (Exception e) {
      this.exception = e;
    }
  }
  
  @Then("^No reader exception should be thrown$")
  public void exceptionShouldNotBeThrown() {
    assertNull(exception);
  }
  
  @Then("^An reader exception should be thrown$")
  public void exceptionShouldBeThrown() {
    assertNotNull(exception);
  }
  
  @Then("^I count the amount of books found (\\d)$")
  public void countMovies(int countExpected) {
    assertEquals(countExpected, movies.size());
  }

  @Then("^I verify the year of books found (\\d+)$")
  public void verifyYears(int yearExpected) {
    movies.forEach(m -> assertEquals(yearExpected, m.getYear()));
  }
  
}
