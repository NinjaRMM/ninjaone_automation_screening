import { expect } from 'chai';
import { Given, When, Then } from "@cucumber/cucumber";
import { readFile } from "fs";

Given('a json file of all movies between 1900-2018 {string}', function (string) {
  expect(this.existsFile(string)).is.true
});

When('I provide a decade like {int} as a parameter', function (int) {
  this.setDecadeParam(int);
});

Then('a file is created called {string} in the data folder', function (string) {
  this.createFile(string);
});

Then('it contains a JSON array of movies from "movies.json" from the years {int}-{int}', function (year1, year2) {
  const { path, totalMovies } = this.filterByDecade(year1, year2)
  readFile(path, 'utf8', (err, data) => {
    if (err) {
      console.log(`Error reading file: ${err}`);
    } else {
      const movies = JSON.parse(data);
      expect(movies.length).to.be.eql(totalMovies)
      movies.forEach(({ year }) => {
        expect(year >= year1 && year <= year2).is.true
      });
    }
  });
});