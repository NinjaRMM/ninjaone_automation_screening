import json
import sys

def find_path_dfs(first, target, movies, visited_movies, visited_actors, path):

    if len(visited_movies) == len(movies):
        return False

    for movie in movies:
        if movie not in visited_movies and first in movie['cast']:
            visited_movies.append(movie)
            
            if target in movie['cast']:
                path.append(target + ' starred with ' + first + ' in ' + movie['title'] + '.')
                return True

            for actor in movie['cast']:
                if actor not in visited_actors:
                    visited_actors.append(actor)
                    if find_path_dfs(actor, target, movies, visited_movies, visited_actors, path):
                        path.append(actor + ' starred with ' + first + ' in ' + movie['title'] + '.')
                        return True

    return False


def degrees(first_actor, second_actor):

    movies_file = open('/workspaces/ninjaone_automation_screening/step3/src/80s-movies.json', encoding='utf-8')
    movies = json.load(movies_file)
    movies_file.close()

    print('Total movies: ' + str(len(movies)))

    path = []
    find_path_dfs(first_actor, second_actor, movies, [], [], path)

    if len(path) == 0:
        print(first_actor + ' did not star in a movie in the data provided.')
        return

    print('There are ' + str(len(path)) + ' degrees of separation between ' + first_actor + ' and ' + second_actor)

    path.reverse()
    for relation in path:
        print(relation)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Please provide one or two actors names!')
    elif len(sys.argv) == 2:
        degrees(sys.argv[1], 'Kevin Bacon')
    else:
        degrees(sys.argv[1], sys.argv[2])