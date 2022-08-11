import inquirer from "inquirer";
import colors from "colors";
import glob from "glob";
import { createRequire } from "module";
import DegreesOfSeparation from "./lib/DegreesOfSeparation.js";
import {
  actorOneQuestion,
  actorTwoQuestion,
  askActorTwoQuestion,
} from "./utils/questions.js";

const require = createRequire(import.meta.url);

const degreesOfSeparation = new DegreesOfSeparation();

const setupData = async () => {
  glob("**/*.json", { cwd: "../../data/" }, async function (err, files) {
    if (err) throw new Error("Error Finding source file");
    const moviesFile =
      files.find((file) => file.includes("s-movies.json")) || "movies.json";
    const movies = require(`../../data/${moviesFile}`);
    degreesOfSeparation.setupData(movies);
  });
};

const setPrompt = async (questions) => {
  return inquirer.prompt(questions);
};

const startProgram = async () => {
  await setupData();
  const { actorName } = await setPrompt(actorOneQuestion);
  const startNode = degreesOfSeparation.setStartNode(actorName);
  if (!startNode) {
    getResults();
  } else {
    const { secondActor } = await setPrompt(askActorTwoQuestion);
    if (secondActor === "Yes") {
      const { secondActorName } = await setPrompt(actorTwoQuestion);
      if (!secondActorName.length) {
        degreesOfSeparation.setEndNode("Kevin Bacon");
      } else degreesOfSeparation.setEndNode(secondActorName);
      getResults();
    } else {
      degreesOfSeparation.setEndNode("Kevin Bacon");
      getResults();
    }
  }
};

const getResults = () => {
  degreesOfSeparation.bfs();
  const numberOfDegreesText = degreesOfSeparation.getNumOfDegreesText();
  console.log(colors.green(numberOfDegreesText));
  const listOfMoviesText = degreesOfSeparation.getListOfMoviesText();
  console.log(colors.green(listOfMoviesText));
};

const setIntro = () => {
  console.log(colors.green("*").repeat(110));
  console.log(
    colors.yellow(
      "\tWelcome to 'Six Degrees of Separation Game'\n\n" +
        "\tFirst type one actor's name. (You can also press enter to default to 'Tom Cruise')\n" +
        "\tThen, select whether you want to input another actor or not\n" +
        "\tIf you select 'No' the program will compare with 'Kevin Bacon'\n\n" +
        "\tEnjoy!"
    )
  );
};

setIntro();
startProgram();
