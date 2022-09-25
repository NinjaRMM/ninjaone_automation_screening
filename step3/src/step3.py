import json
import pathlib
import argparse
import sys
import networkx as nx


DATA_FOLDER = pathlib.Path("../../data/")

MOVIES_FILENAME_DECADE = "1980s-movies.json"
DEFAULT_ACTOR = "Kevin Bacon"
MOVIES_FILE_PATH = pathlib.Path(DATA_FOLDER, MOVIES_FILENAME_DECADE)
MOVIES = []

first_actor = ""
second_actor = DEFAULT_ACTOR
graph = nx.Graph()

parser = argparse.ArgumentParser(description='Print the degrees of separation between two actors')
parser.add_argument('-a', '--actors', type=str, nargs='+', required=True,
                    help='one or two actors to get the degrees of deparation. '
                         'They must be passed using double quotes due the spaces: "Tom Hank" "Tom Cruise"')
parser.add_argument('-m', '--movies', type=str,
                    help=f'the movies json filename in data folder to use. Default is {MOVIES_FILENAME_DECADE}')

args = parser.parse_args()


def load_all_movies():
    global MOVIES
    result = True
    movies_filename = ""
    if args.movies:
        movies_filename = args.movies
    else:
        movies_filename = MOVIES_FILENAME_DECADE

    movies_file = pathlib.Path(DATA_FOLDER, movies_filename)
    if movies_file.exists():
        MOVIES = json.load(movies_file.open("r", encoding="UTF-8"))
        if not MOVIES:
            print(f"Movies list is empty.", file=sys.stderr)
            result = False
    else:
        print(f"Movie file {MOVIES_FILE_PATH} does not exist.", file=sys.stderr)
        result = False

    return result


def parse_arguments():
    global first_actor, second_actor

    if len(args.actors) == 2:
        first_actor = args.actors[0]
        second_actor = args.actors[1]
    elif len(args.actors) == 1:
        first_actor = args.actors[0]
    else:
        print(f"Actors name should be one or two, you passed {len(args.actors)}: {args.actors}", file=sys.stderr)
        return False

    for actor in [first_actor, second_actor]:
        if not is_actor_in_movies(actor):
            print(f"Actor {actor} do not starring in any movie.", file=sys.stderr)
            return False

    return True


def fill_graph():
    global graph

    for movie in MOVIES:
        actors = movie["cast"]
        index = 0

        for actor in actors:
            for actor2 in actors[index + 1:]:
                graph.add_edge(actor, actor2)
            index += 1


def print_degree_separation():
    global first_actor, second_actor

    shortest_path = nx.shortest_path(graph, first_actor, second_actor)

    if shortest_path:
        sep = len(shortest_path) - 2
        print(f'The degree of separation between "{first_actor}" and "{second_actor}" {"is" if sep == 1 else "are"} {sep}.')

        print_degree_separation_path(shortest_path)
    else:
        print(f'Does not exist any connection between "{first_actor}" and "{second_actor}".')


def find_first_movie(actors: list):
    """
    Find the movies where the actors starring together
    :param actors: a list of actors. Could be only one
    :return: The first movie where the actors starred
    """
    for d in MOVIES:
        if 'cast' in d and set(actors).issubset(d['cast']):
            return d['title']

    return None


def print_degree_separation_path(actors_path: list):
    index = 0
    for _ in range(len(actors_path) - 1):
        actor_1 = actors_path[index]
        index += 1
        actor_2 = actors_path[index]
        movie = find_first_movie([actor_1, actor_2])

        print(f"{actor_1} starred with {actor_2} in {movie}")


def is_actor_in_movies(actor: str):
    movie = find_first_movie([actor])
    return movie is not None


if __name__ == "__main__":
    if load_all_movies() and parse_arguments():
        fill_graph()
        print_degree_separation()
