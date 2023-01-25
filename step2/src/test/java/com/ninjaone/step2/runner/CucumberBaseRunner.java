package com.ninjaone.step2.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.ninjaone.step2.stepdefinitions", "com.ninjaone.step2.configuration"}
)
public class CucumberBaseRunner {

}
