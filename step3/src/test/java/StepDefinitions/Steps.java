package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dijkstra.sample.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Steps {

    private String source_actor;
    private String final_actor;

    private String degrees;

    @Given("I run the application")
    public void i_run_the_application() {

        String[] args = {source_actor, final_actor,"ninjaone_automation_screening/data/80s-movies.json"};
        Main.main(args);
        System.out.println(args);
        degrees=args[2];

    }

    @Given("{string} exists from step one")
    public void exists_from_step_one(String pathIn) {
        Path path = Paths.get(pathIn);
        assertTrue(Files.exists(path));
    }

    @When("I provide one actor's name as a parameter")
    public void i_provide_one_actor_s_name_as_a_parameter() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see the number of degrees of separation from Kevin Bacon")
    public void i_should_see_the_number_of_degrees_of_separation_from_kevin_bacon() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I see a list of movies describing the degree steps")
    public void i_see_a_list_of_movies_describing_the_degree_steps() {

        assertEquals("3", degrees);
    }

    @Given("I provide one actor's name as a parameter {string}")
    public void iProvideOneActorSNameAsAParameter(String actor) {
        this.source_actor=actor;
    }

    @And("I should see the number of degrees of separation from {string}")
    public void iShouldSeeTheNumberOfDegreesOfSeparationFrom(String actor) {
        this.final_actor=actor;
    }



    @When("I provide two actors' names one known, and one not in the data as a parameters")
    public void i_provide_two_actors_names_one_known_and_one_not_in_the_data_as_a_parameters() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("I should see a message stating that name did not star in a movie")
    public void i_should_see_a_message_stating_that_name_did_not_star_in_a_movie() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals("0", degrees);
    }



}
