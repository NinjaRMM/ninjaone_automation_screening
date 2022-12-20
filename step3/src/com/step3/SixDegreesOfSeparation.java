package com.step3;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class SixDegreesOfSeparation {

    private Process process;
    private StringBuilder result;
    private int degreesOfSeparation;
    private int expectedDegreesOfSeparation;
    private StringBuilder expectedResult;

    @Given("I run the application")
    public void iRunTheApplication() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "libs/gson-2.10.jar;.", "com.step3.SixDegrees");
        process = pb.start();
    }

    @And("{string} exists from step one")
    public void existsFromStepOne(String fileName) {
        Path filePath = Paths.get("data", fileName);
        assertTrue(Files.exists(filePath));
    }

    @When("I provide one actor's name as a parameter")
    public void iProvideOneActorSNameAsAParameter() throws IOException, InterruptedException {
        OutputStream outputStream = process.getOutputStream();
        String actor1 = "Tom Cruise";
        String actor2 = "Kevin Bacon";
        // Write the arguments to the output stream of the process
        outputStream.write((actor1 + "\n").getBytes());
        outputStream.write((actor2 + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        String line1 = "Tom Cruise starred with Chris Penn in All the Right Moves";
        String line2 = "Chris Penn starred with Kevin Bacon in Footloose";
        expectedResult = new StringBuilder();
        expectedResult.append(line1);
        expectedResult.append(System.getProperty("line.separator"));
        expectedResult.append(line2);

        expectedDegreesOfSeparation = 1;

        processApp();
    }

    private void processApp() throws InterruptedException, IOException {
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        int i = 0;
        while ( (line = reader.readLine()) != null) {
            builder.append(line);
            if(i == 0 ){
               degreesOfSeparation =  Integer.parseInt(line);
               i++;
            }
            builder.append(System.getProperty("line.separator"));
        }
        result = builder;
    }

    @Then("I should see the number of degrees of separation from Kevin Bacon")
    public void iShouldSeeTheNumberOfDegreesOfSeparationFromKevinBacon() throws InterruptedException, IOException {
        // Wait for the command line application to finish
        assertTrue(degreesOfSeparation == expectedDegreesOfSeparation);
    }

    @And("I see a list of movies describing the degree steps")
    public void iSeeAListOfMoviesDescribingTheDegreeSteps() {
        assertTrue(result.toString().contains(expectedResult.toString()));
    }

    @When("I provide two actors' names as a parameters")
    public void iProvideTwoActorsNamesAsAParameters() throws IOException, InterruptedException {
        OutputStream outputStream = process.getOutputStream();
        String actor1 = "Tom Cruise";
        String actor2 = "Sylvester Stallone";
        // Write the arguments to the output stream of the process
        outputStream.write((actor1 + "\n").getBytes());
        outputStream.write((actor2 + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        String line1 = "Tom Cruise starred with Craig T. Nelson in All the Right Moves";
        String line2 = "Craig T. Nelson starred with Rutger Hauer in The Osterman Weekend";
        String line3 = "Rutger Hauer starred with Sylvester Stallone in Nighthawks";
        expectedResult = new StringBuilder();
        expectedResult.append(line1);
        expectedResult.append(System.getProperty("line.separator"));
        expectedResult.append(line2);
        expectedResult.append(System.getProperty("line.separator"));
        expectedResult.append(line3);

        expectedDegreesOfSeparation = 2;

        processApp();
    }

    @Then("I should see the number of degrees of separation between the two actors")
    public void iShouldSeeTheNumberOfDegreesOfSeparationBetweenTheTwoActors() {
        assertTrue(degreesOfSeparation == expectedDegreesOfSeparation);
    }

    @When("I provide a name not in the data as a parameter")
    public void iProvideANameNotInTheDataAsAParameter() throws IOException, InterruptedException {
        OutputStream outputStream = process.getOutputStream();
        String actor1 = "Buzz Lightyear";
        String actor2 = "Kevin Bacon";
        // Write the arguments to the output stream of the process
        outputStream.write((actor1 + "\n").getBytes());
        outputStream.write((actor2 + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        String line1 = "Buzz Lightyear did not star in a movie in the data provided.";
        expectedResult = new StringBuilder();
        expectedResult.append(line1);

        expectedDegreesOfSeparation = -1;

        processApp();
    }

    @Then("I should see a message stating that name did not star in a movie")
    public void iShouldSeeAMessageStatingThatNameDidNotStarInAMovie() {
        assertTrue(result.toString().contains(expectedResult.toString()));
    }

    @When("I provide two actors' names one known, and one not in the data as a parameters")
    public void iProvideTwoActorsNamesOneKnownAndOneNotInTheDataAsAParameters() throws IOException, InterruptedException {
        OutputStream outputStream = process.getOutputStream();
        String actor1 = "Buzz Lightyear";
        String actor2 = "Tom Cruise";
        // Write the arguments to the output stream of the process
        outputStream.write((actor1 + "\n").getBytes());
        outputStream.write((actor2 + "\n").getBytes());
        outputStream.flush();
        outputStream.close();

        String line1 = "Buzz Lightyear did not star in a movie in the data provided.";
        expectedResult = new StringBuilder();
        expectedResult.append(line1);

        expectedDegreesOfSeparation = -1;

        processApp();
    }
}
