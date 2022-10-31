package com.ninjaone.cucumber;

import com.ninjaone.SixDegrees;
import com.ninjaone.service.MoviesService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.ro.Si;
import lombok.SneakyThrows;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitions {


    MoviesService moviesService = new MoviesService();
    SixDegrees sixDegrees = null;

    @Given("a json file of all movies between 1900-2018 {string}")
    public void givenAFile(String filename) {
        moviesService.setSourceFilename(filename);
        moviesService.setMovies(moviesService.getMoviesByFilename(filename));
    }

    @And("I run the application")
    public void andRunApp() {
        System.out.println("Running the app...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("I provide a decade like {int} as a parameter")
    public void whenProvideDecade(Integer decade) {
        moviesService.setFilteredMovies(moviesService.getMoviesByDecade(decade, moviesService.getMovies()));
    }

    @Then("a file is created called {string} in the data folder")
    public void thenCreateFile(String filename) {
        moviesService.generateMoviesFile(moviesService.getFilteredMovies(), filename);
    }

    @And("it contains a JSON array of movies from {string} from the years {int}-{int}")
    public void andFileContent(String expectedSource, int expectedInitYear, int expectedFinYear) {
        assertEquals(expectedSource, moviesService.getSourceFilename());
        assertEquals(expectedInitYear, moviesService.getFirstYear());
        assertEquals(expectedFinYear, moviesService.getLastYear());
    }

    @And("{string} exists from step two")
    public void andFileExists(String fileName){
        File file = new File("../step2/" + fileName);
        assertTrue("The file doesn't exist", file.exists() && file.isFile());
    }

    @SneakyThrows
    @When("I provide one actor's name as a parameter {string}")
    public void whenIProvideActorName(String actorName) {
        sixDegrees = new SixDegrees();
        sixDegrees.setActorName(actorName);
    }

    @SneakyThrows
    @Then("I should see the number of degrees of separation from Kevin Bacon")
    public void thenISeeSixDegrees() {
        sixDegrees.calculateDegrees();
    }

    @And("I see a list of movies describing the degree steps")
    public void andSeeMoviesList() {
        sixDegrees.displayThePath();
    }

    @SneakyThrows
    @When("I provide two actors' names as a parameters {string} and {string}")
    public void whenIProvideTwoActorNames(String actor1, String actor2) {
        sixDegrees = new SixDegrees(actor1, actor2);
    }

    @SneakyThrows
    @When("I should see the number of degrees of separation between the two actors")
    public void whenIShouldSeeSixDegress() {
        sixDegrees.calculateDegrees();
    }

    @When("I provide a name not in the data as a parameter {string}")
    public void whenIProvideNameNotInData(String actorName) {
        sixDegrees = new SixDegrees();
        sixDegrees.setActorName(actorName);
    }

    @SneakyThrows
    @When("I provide two actors' names one known, and one not in the data as a parameters {string} and {string}")
    public void whenIProvideOnaKnownOneNotInData(String actor1, String actor2) {
        sixDegrees = new SixDegrees(actor1, actor2);
    }

    @SneakyThrows
    @Then("I should see a message stating that name did not star in a movie")
    public void thenISeeAMessageDidNotStarInAMovie() {
        sixDegrees.calculateDegrees();
    }
}
