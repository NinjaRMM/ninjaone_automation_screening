import json
import pathlib
import argparse
import sys

DATA_FOLDER = pathlib.Path("../../data/")
MOVIES_FILENAME = "movies.json"
MOVIES_FILENAME_DECADE_SUFFIX = "s-movies.json"

MOVIES_FILE_PATH = pathlib.Path(DATA_FOLDER, MOVIES_FILENAME)
MOVIES = []

parser = argparse.ArgumentParser(description='Create a new file containing the movies from a given decade')
parser.add_argument('-d', '--decade', type=str, required=True,
                    help='a decade to create the new movie json file. Example: "80" or "1910"')
parser.add_argument('-o', '--output', type=str,
                    help='the new movie json filename', )

def load_all_movies():
    global MOVIES
    if MOVIES_FILE_PATH.exists():
        MOVIES = json.load(MOVIES_FILE_PATH.open("r", encoding="UTF-8"))
    else:
        print(f"Movie file {MOVIES_FILE_PATH} does not exist.", file=sys.stderr)
        return False

    return True


def parse_decade(decade: str):
    result_decade = None

    if decade == "00" or decade == "10":
        print(f"Decade {decade} is ambiguos, could be 19{decade} or 20{decade},"
              f" so write the complete year.", file=sys.stderr)
        return None
    elif len(decade) == 2:
        result_decade = 1900 + int(decade)
    elif len(decade) == 4:
        result_decade = int(decade)

    if result_decade is None:
        print(f"Decade {decade} is wrong, please try again using a correct value.", file=sys.stderr)

    return result_decade


def get_movies_by_year(decade: int):
    start_year = decade
    end_year = decade + 9
    return list(filter(lambda d: start_year <= d['year'] <= end_year, MOVIES))


def create_movies_by_decade(movies: list, decade: int):
    out_filename = str(decade) + MOVIES_FILENAME_DECADE_SUFFIX
    if args.output is not None:
        out_filename = args.output

    movies_decade_file = pathlib.Path(DATA_FOLDER, out_filename)

    json.dump(movies, movies_decade_file.open("w", encoding="UTF-8"))

    if movies_decade_file.exists():
        print(f"File '{movies_decade_file.resolve()}' created successfully")
    else:
        print(f"Error creating file {out_filename}", file=sys.stderr)


if __name__ == "__main__":
    args = parser.parse_args()

    if load_all_movies():
        decade_selected = parse_decade(args.decade)
        if decade_selected:
            create_movies_by_decade(get_movies_by_year(decade_selected), decade_selected)
