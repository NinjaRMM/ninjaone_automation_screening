import inquirer from "inquirer";
import colors from "colors";
import FilterMovies from "./lib/FilterMovies.js";

const filterMovies = new FilterMovies();

const getDecade = async () => {
  const { decade } = await inquirer.prompt([
    {
      type: "input",
      name: "decade",
      message:
        "Please enter the decade you would like to filter the movies by?: ",
    },
  ]);
  return decade;
};

const startProgram = async (decade) => {
  if (isNaN(decade)) {
    console.log(colors.red("Decade must be a number"));
    startProgram(await getDecade());
  } else if (!filterMovies.getFourDigitYear(decade)) {
    console.log(colors.red("Please enter a valid decade. Example: 90 or 2010"));
    startProgram(await getDecade());
  } else if (Number(decade) >= 2020) {
    console.log(
      colors.red("Only decades 1900-2010 is valid, please try again")
    );
    startProgram(await getDecade());
  } else {
    filterMovies.createFile(decade);
    const startYear = filterMovies.getFourDigitYear(decade);
    const { movies } = filterMovies.filterByDecade(startYear);
    filterMovies.writeJsonToFile(movies);
    console.log(
      colors.green(
        `Success! File created with movies from ${startYear} - ${Number(
          startYear + 9
        )}`
      )
    );
  }
};

startProgram(await getDecade());
