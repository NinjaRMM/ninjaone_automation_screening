import { setWorldConstructor } from "@cucumber/cucumber";
import fs from "fs";
import { createRequire } from "module";

const require = createRequire(import.meta.url);
const path = require("path");
const { fileURLToPath } = require("url");
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const movies = require("../../../../data/movies.json");

class FilterMovies {
  constructor() {
    this.path = path.resolve(__dirname);
    this.folderPath = `${this.path.substring(
      0,
      this.path.indexOf("step2") - 1
    )}/data`;
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
      fs.unlinkSync(this.localFilePath);
    }
    try {
      fs.createWriteStream(this.localFilePath);
    } catch (error) {
      console.error(`Error writing file ${this.localFilePath}.`);
    }
  }

  filterByDecade(startYear, endYear) {
    // TODO: Change to use the decadeParam property instead
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
    return { path: this.localFilePath, totalMovies: moviesForDecade.length };
  }
}

setWorldConstructor(FilterMovies);
