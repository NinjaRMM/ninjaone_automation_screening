package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import com.gbvbahia.ninjarmm.service.SixDegreesService;
import com.gbvbahia.ninjarmm.service.io.Movies80ServiceReaderService;
import com.gbvbahia.ninjarmm.service.io.MoviesDataReaderService;
import com.gbvbahia.ninjarmm.service.io.MoviesDataService;
import com.gbvbahia.ninjarmm.service.io.MoviesDataWriterService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class SixDegreesOfSeparationRunnerRunSteps {

  private final String MOVIES_80_JSON_DEFAULT = "80s-movies.json";
  private final String MOVIES_FOLDER_DEFAULT = "../../../data/";
  private final Integer DECATE_80_DEFAULT = 1980;
  
  private SixDegreesOfSeparationRunner subject;

  private ApplicationArguments appArgs;
  
  @Before
  public void setUp() throws Exception {
    MoviesDataReaderService reader = new MoviesDataReaderService("../../../data/movies.json");
    @SuppressWarnings("all")
    MoviesDataWriterService writer = new MoviesDataWriterService(MOVIES_FOLDER_DEFAULT);
    MoviesDataService moviesDataService = new MoviesDataService(DECATE_80_DEFAULT, MOVIES_80_JSON_DEFAULT, reader, () -> writer);
    
    Movies80ServiceReaderService movies80ServiceReaderService = new Movies80ServiceReaderService(MOVIES_FOLDER_DEFAULT, MOVIES_80_JSON_DEFAULT);
    
    SixDegreesService sixDegreesService = new SixDegreesService(movies80ServiceReaderService);
    
    subject = new SixDegreesOfSeparationRunner("Kevin Bacon", "actors", ",", moviesDataService, sixDegreesService);

    appArgs = null;
  }
  
  @After
  private void end() throws Exception {
    Files.deleteIfExists(Path.of(MOVIES_FOLDER_DEFAULT, MOVIES_80_JSON_DEFAULT));
  }
  
  @Given("^I run the application$")
  public void given() throws Exception {
    subject.generate80Movies();
  }
  
  @And("^\"80s-movies.json\" exists from step one$")
  public void and80MoviesExists() throws Exception {
    assertTrue(Files.exists(Path.of(MOVIES_FOLDER_DEFAULT, MOVIES_80_JSON_DEFAULT)));
  }

  @When("^I provide two actors names as a parameters (.+)$") 
  public void runWith(String actorsArg) throws Exception {
    String[] args = new String[] {actorsArg};
    appArgs = new DefaultApplicationArguments(args);
    subject.run(appArgs);
  }
  
}
