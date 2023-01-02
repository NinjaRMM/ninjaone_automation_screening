package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesByYearArgsValidatorRunSteps {

  private MoviesByYearArgsValidator moviesByYearArgsValidator;
  private ApplicationArguments appArgs;
  private Exception validateException;
  
  @Before
  public void setUp() {
    moviesByYearArgsValidator = new MoviesByYearArgsValidator("decade", "output", 1900, 2010);
    validateException = null;
    appArgs = null;
  }
  
  @Given("^I send args to validate$")
  public void given(DataTable table) throws Exception {
    List<String> rows = table.values();
    String[] args = new String[2];
    for (int i = 0; i < rows.size(); i++) {
     args[i] = rows.get(i);  
    }
    appArgs = new DefaultApplicationArguments(args);
  }

  @When("^I execute validation with parameters list$")
  public void receiveParametersForMoviesByYear() {

    try {
      
      moviesByYearArgsValidator.run(appArgs);
      
    } catch (Exception e) {
      
      validateException = e;
    }
    
  }

  @Then("^No exception should be thrown$")
  public void validationExceptionShouldNotBeThrown() {
    assertNull(validateException);
  }
  
  @Then("^An exception should be thrown$")
  public void validationExceptionShouldBeThrown() {
    assertNotNull(validateException);
  }
}
