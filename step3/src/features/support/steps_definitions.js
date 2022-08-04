import { expect } from 'chai';
import { Given, When, Then, Before } from "@cucumber/cucumber";

Before(function () {
  this.setupData()
})

Given('I run the application', function () {
  return true
});

Given('{string} exists from step one', function (string) {
  expect(this.existsFile(string)).is.true
});

When('I provide one actor\'s {string} as a parameter', function (actorName) {
  this.setStartNode(actorName);
});

Then('I should see the {string} of separation from {string}', function (numberOfDegrees, actorName) {
  this.setEndNode(actorName)
  this.bfs()
  const numberOfDegreesText = this.getNumOfDegreesText()
  expect(numberOfDegreesText).eq(numberOfDegrees)
});

Then('I see a list of movies describing the degree steps', function (docString) {
  const listOfMoviesText = this.getListOfMoviesText()
  expect(listOfMoviesText).eq(docString)
});

When('I provide two actors\' names as a parameters {string}, {string}', function (startActor, endActor) {
  this.setStartNode(startActor);
  this.setEndNode(endActor);
});

Then('I should see the {string} of separation between the two actors', function (numberOfDegrees) {
  this.bfs()
  const numberOfDegreesText = this.getNumOfDegreesText()
  expect(numberOfDegreesText).eq(numberOfDegrees)
});

When('I provide a name not in the data as a parameter {string}', function (actorName) {
  this.setStartNode(actorName);
});

Then('I should see a message stating that name did not star in a movie', function () {
  this.bfs()
  const numberOfDegreesText = this.getNumOfDegreesText()
  expect(numberOfDegreesText).equal(`${this.startActor} did not star in a movie in the data provided.`)
});

When('I provide two actors\' names one known, and one not in the data as a parameters {string}, {string}', function (startActor, endActor) {
  this.setStartNode(startActor);
  this.setEndNode(endActor);
});

