import FilterMovies from "../lib/FilterMovies";

const filterMovies = new FilterMovies();

describe("Filter Movies App", () => {
  test("should return four digit integer when a 2 digit decade is passed as a string", async () => {
    const year = "90";
    const fourDigitYear = filterMovies.getFourDigitYear(year);
    expect(fourDigitYear).toEqual(1990);
  });

  test("should return undefined when a 2 digit decade is passed as an integer", async () => {
    const year = 90;
    const fourDigitYear = filterMovies.getFourDigitYear(year);
    expect(fourDigitYear).toBeUndefined();
  });

  test("should return four digit integer when a 4 digit year is passed as a string", async () => {
    const year = "1990";
    const fourDigitYear = filterMovies.getFourDigitYear(year);
    expect(fourDigitYear).toEqual(Number(year));
  });

  test("should return an array of movie data filtered by a decade", async () => {
    const startYear = 1990;
    const { movies } = filterMovies.filterByDecade(startYear);
    movies.forEach(({ year }) => {
      expect(year >= 1990).toEqual(true);
      expect(year > startYear + 9).toEqual(false);
    });
  });
});
