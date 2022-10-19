package com.ninjaone.cucumber;

import com.ninjaone.service.MoviesService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {


    MoviesService moviesService = new MoviesService();


    @Given("a json file of all movies between 1900-2018 {string}")
    public void givenAFile(String filename) {
        moviesService.setSourceFilename(filename);
        moviesService.setMovies(moviesService.getMoviesByFilename(filename));
    }

    @And("I run the application")
    public void andRunApp(){
        System.out.println("Running the app...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("I provide a decade like {int} as a parameter")
    public void whenProvideDecade(Integer decade) {
        moviesService.setDecade(decade);
        moviesService.setFilteredMovies(moviesService.getMoviesByDecade(decade, moviesService.getMovies()));
    }

    @Then("a file is created called {string} in the data folder")
    public void thenCreateFile(String filename) {
        moviesService.generateMoviesFile(moviesService.getFilteredMovies(), moviesService.getDecade(), filename);
    }

    @And("it contains a JSON array of movies from {string} from the years {int}-{int}")
    public void andFileContent(String expectedSource, int expectedInitYear, int expectedFinYear) {
        assertEquals(expectedSource, moviesService.getSourceFilename());
        assertEquals(expectedInitYear, moviesService.getFirstYear());
        assertEquals(expectedFinYear, moviesService.getLastYear());
    }
}
