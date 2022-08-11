export const actorOneQuestion = [
  {
    type: "input",
    name: "actorName",
    message: "Please enter one actor's name: ",
    default: "Tom Cruise",
  },
];

export const actorTwoQuestion = [
  {
    type: "input",
    name: "secondActorName",
    message: "Please enter a second actor's name to compare: ",
  },
];

export const askActorTwoQuestion = [
  {
    type: "rawlist",
    name: "secondActor",
    message:
      'Would you like to input a second actor?: (if you choose No, it will be compared with "Kevin Bacon"',
    choices: ["Yes", "No"],
  },
];
