import movies from "../../../data/movies.json";
import DegreesOfSeparation from "../lib/DegreesOfSeparation";
const degree = new DegreesOfSeparation();

// TODO: Ideally we use a mock data, but at the moment Jest mock function does not support ESM (ES Modules)

describe("Six degrees of Separation App", () => {
  beforeEach(() => {
    degree.setupData(movies);
  });

  it("Should return correct message when comparing two actors.", () => {
    const actor1 = "Tom Cruise";
    const actor2 = "Sylvester Stallone";
    degree.setStartNode(actor1);
    degree.setEndNode(actor2);
    degree.bfs();

    const numberOfDegreesText = degree.getNumOfDegreesText();
    expect(numberOfDegreesText).toEqual(
      "There are 2 degrees of separation between Tom Cruise and Sylvester Stallone"
    );

    const listOfMoviesText = degree.getListOfMoviesText();
    expect(listOfMoviesText).toEqual(
      "Tom Cruise starred with Diane Lane in The Outsiders\nDiane Lane starred with Sylvester Stallone in Judge Dredd\n"
    );
  });

  it('Should return correct message when one actor is compared with "Kevin Bacon".', () => {
    const actor = "Tom Cruise";
    degree.setStartNode(actor);
    degree.setEndNode("Kevin Bacon");
    degree.bfs();

    const numberOfDegreesText = degree.getNumOfDegreesText();
    expect(numberOfDegreesText).toEqual(
      "There is 1 degree of separation between Tom Cruise and Kevin Bacon"
    );

    const listOfMoviesText = degree.getListOfMoviesText();
    expect(listOfMoviesText).toEqual(
      "Tom Cruise starred with Kevin Bacon in A Few Good Men\n"
    );
  });

  it('Should return correct message when one Unknown actor in the data is entered".', () => {
    const actor = "Buzz Lightyear";
    degree.setStartNode(actor);
    degree.bfs();

    const numberOfDegreesText = degree.getNumOfDegreesText();
    expect(numberOfDegreesText).toEqual(
      "Buzz Lightyear did not star in a movie in the data provided."
    );

    const listOfMoviesText = degree.getListOfMoviesText();
    expect(listOfMoviesText).toHaveLength(0);
  });

  it('Should return correct message when one Unknown actor is compared to a known actor in the data".', () => {
    const actor = "Buzz Lightyear";
    degree.setStartNode(actor);
    degree.setEndNode("Kevin Bacon");
    degree.bfs();

    const numberOfDegreesText = degree.getNumOfDegreesText();
    expect(numberOfDegreesText).toEqual(
      "Buzz Lightyear did not star in a movie in the data provided."
    );

    const listOfMoviesText = degree.getListOfMoviesText();
    expect(listOfMoviesText).toHaveLength(0);
  });
});
