package com.gbvbahia.ninjarmm.runner;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.ConfigurationParameters;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/runner")
@ConfigurationParameters ({
  @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty"),
  @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.gbvbahia")
})
public class MoviesByYearArgsValidatorTests {
}
