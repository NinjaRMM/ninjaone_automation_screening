#!/bin/sh

cd automation-tool/

mvn test -Dtest="br.com.timoteosoutello.cucumber.stepdefinitions.sixdegrees.SixDegreesTest" -Dcucumber.publish.enabled=true -Dcucumber.publish.quiet=true -Dcucumber.plugin="pretty, html:target/cucumber-reports/Cucumber.html, json:target/cucumber-reports/Cucumber.json, junit:target/cucumber-reports/Cucumber.xml" 

cd ..
