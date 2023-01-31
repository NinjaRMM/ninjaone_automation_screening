package br.com.timoteosoutello.cucumber.stepdefinitions.filtermovies;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

import br.com.timoteosoutello.cucumber.CucumberSpringBaseConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;

@Suite
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@SelectDirectories("src/test/resources/features/step-two-filter-movies.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.timoteosoutello.cucumber.stepdefinitions.filtermovies")
public class FilterMoviesTest extends CucumberSpringBaseConfiguration {

}
