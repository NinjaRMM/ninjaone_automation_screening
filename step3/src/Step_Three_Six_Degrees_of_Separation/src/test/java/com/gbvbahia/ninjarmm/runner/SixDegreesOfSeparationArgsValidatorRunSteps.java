package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SixDegreesOfSeparationArgsValidatorRunSteps {

  private SixDegreesOfSeparationArgsValidator subject;
  private ApplicationArguments appArgs;
  private Exception validateException;
  
  @Before
  public void setUp() {
    subject = new SixDegreesOfSeparationArgsValidator("actors", ",", "Kevin Bacon");
    validateException = null;
    appArgs = null;
  }
  
  @Given("^I send actors to validate (.+)$")
  public void given(String actors) throws Exception {
    String[] args = new String[] {actors};
    appArgs = new DefaultApplicationArguments(args);
  }

  @When("^I execute validation with parameters list$")
  public void receiveParametersForMoviesByYear() {

    try {
      
      subject.run(appArgs);
      
    } catch (Exception e) {
      
      validateException = e;
    }
    
  }

  @Then("^No actors exception should be thrown$")
  public void validationExceptionShouldNotBeThrown() {
    assertNull(validateException);
  }
  
  @Then("^An actors exception should be thrown$")
  public void validationExceptionShouldBeThrown() {
    assertNotNull(validateException);
  }
}
