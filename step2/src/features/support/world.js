import { setWorldConstructor } from "@cucumber/cucumber";
import fs from "fs";
import { createRequire } from "module";

const require = createRequire(import.meta.url);
const movies = require("../../../../data/movies.json");

class FilterMovies {
  constructor() {
    this.folderPath =
      "/Users/williamestrada-ninjarmm/Documents/GitHub/ninjaone_automation_screening/data";
    this.filePath = "";
    this.localFilePath = "";
    this.decadeParam = "";
  }

  existsFile(fileName) {
    this.filePath = `${this.folderPath}/${fileName}`;
    return fs.existsSync(this.filePath);
  }

  setDecadeParam(decade) {
    this.decadeParam = decade;
  }

  createFile(fileName) {
    this.localFilePath = `${this.folderPath}/${fileName}`;
    if (fs.existsSync(this.localFilePath)) {
      fs.unlinkSync(this.localFilePath)
    }
    fs.createWriteStream(this.localFilePath, );
  }

  filterByDecade(startYear, endYear) { // TODO: Change to use the decadeParam
    const moviesForDecade = [];
    movies.filter((movie) => {
      if (movie.year >= startYear && movie.year <= endYear) {
        moviesForDecade.push(movie);
      }
    });
    fs.writeFile(
      this.localFilePath,
      JSON.stringify(moviesForDecade),
      (error) => {
        if (error) console.log(error);
      }
    );
    return { path: this.localFilePath, totalMovies: moviesForDecade.length}
  }
}

setWorldConstructor(FilterMovies);
