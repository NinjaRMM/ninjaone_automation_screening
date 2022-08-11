import fs from "fs";
import { createRequire } from "module";

const require = createRequire(import.meta.url);
const path = require("path");
const { fileURLToPath } = require("url");
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const movies = require("../../../data/movies.json");

export default class FilterMovies {
  constructor() {
    this.path = path.resolve(__dirname);
    this.folderPath = `${this.path.substring(
      0,
      this.path.indexOf("step2") - 1
    )}/data`;
    this.localFilePath = "";
  }

  createFile(decade) {
    this.localFilePath = `${this.folderPath}/${decade}s-movies.json`;
    if (fs.existsSync(this.localFilePath)) {
      fs.unlinkSync(this.localFilePath);
    }
    try {
      fs.createWriteStream(this.localFilePath);
    } catch (error) {
      console.error(`Error writing file ${this.localFilePath}.`);
    }
  }

  writeJsonToFile(content) {
    fs.writeFile(this.localFilePath, JSON.stringify(content), (error) => {
      if (error) return error;
    });
  }

  getFourDigitYear(val) {
    let strYear = val;
    if (strYear.length === 2 && val % 10 === 0) {
      if (Number(val) > 0 || val === "00") {
        // this number controls how far back to go in the 20th century
        strYear = `19${strYear}`;
        return Number(strYear);
      }
    } else if (strYear.length === 4 && val % 10 === 0) {
      return Number(strYear);
    }
  }

  filterByDecade(startYear) {
    const moviesForDecade = [];
    movies.filter((movie) => {
      if (movie.year >= startYear && movie.year <= startYear + 9) {
        moviesForDecade.push(movie);
      }
    });
    return { movies: moviesForDecade };
  }
}
