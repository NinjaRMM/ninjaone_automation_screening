package com.gbvbahia.ninjarmm.runner;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import com.gbvbahia.ninjarmm.service.MoviesDataReaderService;
import com.gbvbahia.ninjarmm.service.MoviesDataWriterService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesByYearRunnerRunSteps {

  private MoviesByYearRunner subject;
  @Mock
  private MoviesDataReaderService moviesDataReaderService;
  @Mock
  private MoviesDataWriterService moviesDataWriterService;
  @Mock
  private ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider;

  private ApplicationArguments appArgs;
  private Exception exception;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    Mockito.when(moviesDataWriterServiceProvider.getObject()).thenReturn(moviesDataWriterService);
    Mockito.when(moviesDataWriterService.begin(anyString())).thenReturn(moviesDataWriterService);
    appArgs = null;
    exception = null;
  }
  
  @Given("^I start movies by year runner (.+) (.+)$")
  public void given(String decadeArg, String outputArg) {
    subject = new MoviesByYearRunner(decadeArg, outputArg, moviesDataReaderService, moviesDataWriterServiceProvider);
  }
  
  @Given("^I send args to run$")
  public void given(DataTable table) throws Exception {
    List<String> rows = table.values();
    appArgs = new DefaultApplicationArguments(rows.toArray(new String[rows.size()]));
  }

  @When("^I execute run on movies by year$")
  public void whenRun() {
    try {
      
      subject.run(appArgs);
      
    }catch (Exception e) {
      this.exception = e;
    }
  }
  
  @Then("^I check mockito services interactions (\\d) (\\d+)$")
  public void verifyMockito(int times, int decade) throws Exception {
    verify(moviesDataReaderService, times(times)).fetchAllMoviesByDecade(eq(decade), eq(moviesDataWriterService));
  }
  
  @Then("^No exception on start runner should be thrown$")
  public void exceptionShouldNotBeThrown() {
    assertNull(exception);
  }
}
