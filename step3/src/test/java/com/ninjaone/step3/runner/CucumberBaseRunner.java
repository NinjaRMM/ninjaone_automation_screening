package com.ninjaone.step3.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.ninjaone.step3.stepdefinitions", "com.ninjaone.step3.configuration"}
)
public class CucumberBaseRunner {

}
