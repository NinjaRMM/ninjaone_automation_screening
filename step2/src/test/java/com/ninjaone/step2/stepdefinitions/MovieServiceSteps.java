package com.ninjaone.step2.stepdefinitions;

import static com.ninjaone.step2.util.TestUtils.get80sMovies;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.step2.cli.ParseCommand;
import com.ninjaone.step2.model.Movie;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import picocli.CommandLine;

public class MovieServiceSteps {

    private static final String DATA_FOLDER_PATH = "../data/";

    private String outputFileName;

    @Autowired
    private ParseCommand parseCommand;

    @Given("^a json file of all movies between 1900-2018 (.+)")
    public void givenJsonFile(String fileName) {

        File allMoviesFile = new File(DATA_FOLDER_PATH + escapeDoubleQuote(fileName));

        assertTrue(allMoviesFile.exists());
    }

    @And("^I run the application")
    public void runApplication() {
        // nothing to do here
    }

    @When("^I provide a decade like (\\d+) as a parameter")
    public void whenProvideDecade(Integer decade) {

        CommandLine cmd = new CommandLine(parseCommand);
        Integer exitCode = cmd.execute("--decade", decade.toString());

        assertEquals(0, exitCode);
    }

    @Then("^a file is created called (.+) in the data folder")
    public void thenFileIsCreated(String fileName) throws IOException {

        outputFileName = escapeDoubleQuote(fileName);

        File fileCreated = new File(DATA_FOLDER_PATH + outputFileName);

        assertTrue(fileCreated.exists());
    }

    @And("^it contains a JSON array of movies from (.+) from the years (\\d+)-(\\d+)")
    public void andContainsJsonArray(String fileName, int startYear, int endYear) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> filteredMovies = mapper.readValue(new File(DATA_FOLDER_PATH + outputFileName), mapper.getTypeFactory().constructCollectionType(List.class, Movie.class));

        List<Movie> hardCoded80sMovies = get80sMovies();

        assertEquals(filteredMovies.size(), hardCoded80sMovies.size());
        assertTrue(filteredMovies.containsAll(hardCoded80sMovies));
    }

    private static String escapeDoubleQuote(String testParam) {
        return testParam.replaceAll("^\"|\"$", "");
    }
}
