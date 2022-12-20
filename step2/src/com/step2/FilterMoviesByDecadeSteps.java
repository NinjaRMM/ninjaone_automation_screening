package com.step2;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;


public class FilterMoviesByDecadeSteps {
    private Process process;

    @Given("a json file of all movies between 1900-2018 {string}")
    public void given_json_file(String fileName) {
        // Check that the file exists
        Path filePath = Paths.get("data/"+fileName);
        assertTrue(Files.exists(filePath));
    }

    @When("I run the application")
    public void when_run_application() throws IOException {
        // Execute the command line application
        //java -cp step2/libs/json-20220924.jar;. -classpath ../../ com.step2.FilterMovies
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "step2/libs/json-20220924.jar;.", "-classpath", "../../", "com.step3.FilterMovies");
        process = pb.start();
    }

    @When("I provide a decade like {int} as a parameter")
    public void when_provide_parameter(int decade) throws IOException {
        // Write the decade to the command line application's standard input
        process.getOutputStream().write(decade);
    }

    @Then("a file is created called {string} in the data folder")
    public void then_file_created(String fileName) throws InterruptedException {
        // Wait for the command line application to finish
        process.waitFor();

        // Check that the file was created
        Path filePath = Paths.get("data", fileName);
        assertTrue(Files.exists(filePath));
    }

    @Then("it contains a JSON array of movies from {string} from the years {int}-{int}")
    public void then_file_contains_movies(String sourceFileName, int startYear, int endYear) throws IOException {
        // Read the contents of the file
        String fileContents = new String(Files.readAllBytes(Paths.get("data", sourceFileName)));

        // Check that the file contains a JSON array of movies from the specified years
        //assertTrue(fileContents.matches("\\[\\s*(\\{.*\\}\\s*,\\s*)*\\]"));
        assertTrue(fileContents.contains("\"year\":" + startYear));
        assertTrue(fileContents.contains("\"year\":" + endYear));
    }
}