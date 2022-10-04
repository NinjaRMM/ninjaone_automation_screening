from os import path
import sys
import json
from queue import Queue

data_path = '../../data/'


def shortest_distance_actor(movies_for_actor, actors_for_movie, first_actor, second_actor=None):
    main_actor = 'Kevin Bacon'
    find_actor = first_actor
    if not movies_for_actor.get(first_actor) or (second_actor and not movies_for_actor.get(second_actor)):
        return f'{first_actor if not movies_for_actor.get(first_actor) else second_actor} ' \
               f'did not star in a movie in the data provided.'
    if second_actor:
        main_actor = first_actor
        find_actor = second_actor
    # Queue with actors to evaluate
    q = Queue()
    # Map with actor and distance number to main actor
    m = {}
    # List of visited movies
    v = []
    # start with main actor (0 distance)
    q.put(main_actor)
    m.update({main_actor: []})

    while not q.empty():
        actor = q.get()
        if actor == find_actor:
            return m.get(actor)
        for movie in movies_for_actor.get(actor):
            if movie in v:
                continue
            for other_actor in actors_for_movie.get(movie):
                if other_actor == actor:
                    continue
                if not m.get(other_actor):
                    m.update({other_actor: m.get(actor) + [{f'{actor} | {other_actor}': movie}]})
                    if other_actor == find_actor:
                        return m.get(other_actor)
                    q.put(other_actor)
            v.append(movie)


def execute(file_name, first_actor, second_actor):
    with open(f'{data_path}{file_name}.json') as file:
        movies = json.load(file)
        actors_for_movie = {}
        movies_for_actor = {}
        for movie in movies:
            actors_for_movie.update({movie['title']: movie['cast']})
            for actor in movie['cast']:
                exist = movies_for_actor.get(actor)
                if exist:
                    exist.append(movie['title'])
                else:
                    movies_for_actor.update({actor: [movie['title']]})

    res = shortest_distance_actor(movies_for_actor, actors_for_movie, first_actor, second_actor)
    if isinstance(res, str):
        print(res)
    else:
        print(len(res))


if __name__ == '__main__':
    # validate args
    n = len(sys.argv)
    if n < 3 or n > 4:
        print("Wrong Arguments!")
        print("Example 1:\n $ python six_degrees.py 80s-movies 'Tom Cruise'")
        print("Example 2:\n $ python six_degrees.py 80s-movies 'Tom Cruise' 'Sylvester Stallone'")
        exit(1)
    input_file = sys.argv[1]
    if not path.exists(f'{data_path}{input_file}.json'):
        # Not validating the content for now
        print('File does not exist! \nExample: 80s-movies (Should be .json) ')
        exit(1)
    actor1 = sys.argv[2]
    actor2 = sys.argv[3] if n == 4 else None

    execute(input_file, actor1, actor2)
    exit(0)

