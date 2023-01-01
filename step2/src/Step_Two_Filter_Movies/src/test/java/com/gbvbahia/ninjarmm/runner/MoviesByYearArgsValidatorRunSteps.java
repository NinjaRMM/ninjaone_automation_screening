package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesByYearArgsValidatorRunSteps {

  private MoviesByYearArgsValidator moviesByYearArgsValidator;
  private ApplicationArguments appArgs;
  private Exception validateException;
  
  @Given("^I have a MoviesByYearArgsValidator$")
  public void setUp() throws Exception {
    moviesByYearArgsValidator = new MoviesByYearArgsValidator("decade", "output", 1900, 2010);
    validateException = null;
    appArgs = null;
  }

  @When("^I have the following parameters list$")
  public void receiveParametersForMoviesByYear(DataTable table) {
    List<String> rows = table.values();
    String[] args = new String[2];
    for (int i = 0; i < rows.size(); i++) {
     args[i] = rows.get(i);  
    }
    appArgs = new DefaultApplicationArguments(args);

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
