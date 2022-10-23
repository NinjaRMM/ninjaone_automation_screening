import os
import json
from datetime import date
import sys


class MovieFilter:
    def __init__(self):
        self.movies_path = os.path.abspath('../../data/movies.json')
        self.array_of_movies = None

    def filter_json_movies_by(self, decade):
        """
        filters the decade movies from the array of movies
        :param decade: years of the decade
        :return: None
        """
        try:
            self.read_movies_json()
            self.array_of_movies.sort(key=lambda d: d.get('year'))
            for start_year in self.parse_decade(decade):
                result = []
                output_file_name = f"{start_year}s-movies.json"
                for movie in self.array_of_movies:
                    end_year = start_year + 10
                    if start_year <= movie.get('year') < end_year:
                        result.append(movie)
                    elif movie.get('year') == end_year:
                        break
                self.write_json_result(result, output_file_name)
        except Exception as e:
            print(e)

    def read_movies_json(self):
        """
        read json file and create a list of dictionaries
        :return: None
        """
        with open(self.movies_path, "r") as json_file:
            self.array_of_movies = json.load(json_file)

    def write_json_result(self, filtered_movies, output_file_name):
        """
        Creates a decades json file in the data folder
        :param filtered_movies: list of dictionaries
        :param output_file_name: json file name
        :return: None
        """
        movies_dir = os.path.dirname(self.movies_path)
        json_string = json.dumps(filtered_movies)
        json_fh = open(os.path.join(movies_dir, output_file_name), 'w')
        json_fh.write(json_string)
        json_fh.close()

    @staticmethod
    def parse_decade(two_digits_decade):
        """
        :param two_digits_decade: multiple of 10 to define a decade
        :return: a list with the start year of the decade. None if decade is not multiple of 10
        """
        decades = []
        if two_digits_decade % 10 != 0:
            raise ValueError("Invalid value for decade")

        start = 1900 + two_digits_decade
        decades.append(start)

        current_year = int(date.today().strftime("%y"))
        if two_digits_decade < current_year:
            start = 2000 + two_digits_decade
            decades.append(start)
        return decades


if __name__ == '__main__':
    movie_filter = MovieFilter()
    if len(sys.argv) > 1:
        decade = int(sys.argv[1])
        movie_filter.filter_json_movies_by(decade)
    else:
        print("""\tUsage:
        $ python filter_movies_by_decade <decade>
        
        Please provide a decade as 2 digits. Example: 80
        python filter_movies_by_decade 80
        """)

