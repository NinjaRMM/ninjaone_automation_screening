package br.com.timoteosoutello.cucumber.stepdefinitions.common;

import io.cucumber.java.Scenario;

public class CommonStepDefinitions {

	protected Scenario scenario;

	public void beforeStep(Scenario scenario) {
		this.scenario = scenario;
		System.out.println("\n".concat(String.format("Current scenario's test: %s", this.scenario.getName())));
	}
}
