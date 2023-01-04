package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.test.util.ReflectionTestUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesByYearArgsValidatorRunSteps {

  private MoviesByYearArgsValidator subject;
  private ApplicationArguments appArgs;
  private Exception validateException;
  
  @Before
  public void setUp() {
    subject = new MoviesByYearArgsValidator("decade", "output", 1900, 2010, "./src/test/resources/json_movies");
    ReflectionTestUtils.invokeMethod(subject, "init");
    validateException = null;
    appArgs = null;
  }
  
  @Given("^I send args to validate$")
  public void given(DataTable table) throws Exception {
    List<String> rows = table.values();
    appArgs = new DefaultApplicationArguments(rows.toArray(new String[rows.size()]));
  }

  @When("^I execute validation with parameters list$")
  public void receiveParametersForMoviesByYear() {

    try {
      
      subject.run(appArgs);
      
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
