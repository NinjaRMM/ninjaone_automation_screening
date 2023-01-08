package com.gbvbahia.ninjarmm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gbvbahia.ninjarmm.model.Movie;

import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesDataWriterServiceRunSteps {

  private ObjectMapper objectMapper = new ObjectMapper();
  
  private MoviesDataWriterService subject;
  private Exception exception;
  private String outputFolder;
  private String outputFileName;
  
  @Before
  public void setUp() throws Exception {
    subject = null;
    exception = null;
    outputFolder = null;
    outputFileName = null;
  }
  
  @After
  public void end() throws Exception {
    if (StringUtils.isNoneBlank(outputFolder, outputFileName)) {
		  Files.deleteIfExists(Path.of(outputFolder, outputFileName));
    }
  }
  
  @DataTableType
  public Movie authorEntry(Map<String, String> entry) {
      return Movie.builder()
          .title(entry.get("title"))
          .year(Integer.valueOf(entry.get("year")))
          .cast(Arrays.asList(entry.get("cast").split(",")))
          .genres(Arrays.asList(entry.get("genres").split(",")))
          .build();
  }
  
  @Given("^I create a new writer for folder (.+)$")
  public void given(String outputFolder) throws Exception {
    subject = new MoviesDataWriterService(outputFolder);
    this.outputFolder = outputFolder;
  }
  
  @When("^I call begin method (.+)$")
  public void writerBegin(String outputFileName) {
    this.outputFileName = outputFileName;
    
    try {
      
      subject.begin(outputFileName);
      
    } catch (Exception e) {
      this.exception = e;
    }
  }
  
  @When("^I call process method with movie$")
  public void writerProcess(DataTable table) {
    
    List<Movie> movies = table.asList(Movie.class);
    
    try {
      
      for (Movie movie : movies) {
        subject.process(movie);
      }
      
    } catch (Exception e) {
      this.exception = e;
    }
  }
  
  @When("^I call close method$")
  public void writerClose() {
    try {
      subject.close();
    } catch (Exception e) {
      this.exception = e;
    }
  }
  
  @Then("^No writer exception should be thrown$")
  public void exceptionShouldNotBeThrown() {
    assertNull(exception);
  }
  
  @Then("^I check if the file was written$")
  public void fileExist() {
    assertTrue(Files.exists(Path.of(outputFolder, outputFileName)));
  }
  
  @Then("^I check the file size (\\d)$")
  public void checkFileSize(int size) throws Exception {
    List<Movie> movies = objectMapper.readValue(new File(outputFolder, outputFileName), new TypeReference<List<Movie>>(){});
    assertEquals(size, movies.size());
  }
}
