import os, json


# This function is checking all movies that actor starring in
def check_neighbors(actor):
    connections = []
    for cast in movies:
        if actor in cast['cast']:
            if actor2 in cast['cast'] and actor == actor1:
                print(cnt)
                exit(0)
            connections.append(cast['cast'])
    return connections


# Removing duplicates actors from the list on the current connection level
def remove_duplicates(actor_cast):
    unique_actors = []

    for actors in actor_cast:
        for actor in actors:
            if actor not in unique_actors:
                if actor == actor1:
                    continue
                unique_actors.append(actor)

    return unique_actors


# Condition to check if Actor #1 and Actor #2 are in the json data
def actors_check():
    actor1_exist = False
    actor2_exist = False

    for actors in movies:
        if actor1 in actors['cast']: actor1_exist = True
        if actor2 in actors['cast']: actor2_exist = True

    if not actor1_exist and not actor2_exist:
        print(f"Both {actor1} and {actor2} did not star in a movie in the data provided.")
        exit(0)
    if not actor1_exist:
        print(f"{actor1} did not star in a movie in the data provided.")
        exit(0)
    if not actor2_exist:
        print(f"{actor2} did not star in a movie in the data provided.")
        exit(0)


# Main iteration checking if one of the connection on the current level is our desired actor
def six_degrees_connections():
    global cnt, unique_cast_connections

    unique_cast_connections_temp = []

    for actor in unique_cast_connections:
        connections = check_neighbors(actor)
        unique_cast_connections_step = remove_duplicates(connections)
        if actor2 in unique_cast_connections_step:
            print(cnt)
            exit(0)

        for i in unique_cast_connections_step:
            unique_cast_connections_temp.append(i)

    unique_cast_connections = unique_cast_connections_temp
    cnt += 1


def main():
    global actor1, actor2, movies, cnt, unique_cast_connections

    # Start of the application. We're getting both actors' names by user's input
    print("Six Degrees Solution by Art")
    actor1 = input("Please enter first actor's name (mandatory): \n")
    if len(actor1) <= 5:
        raise ValueError("Invalid actor's name. Please try again.")

    actor2 = input("Please enter second actor's name (optional): \n")
    if len(actor2) <= 3:
        actor2 = 'Kevin Bacon'

    cnt = 0
    unique_cast_connections = []

    # Parsing the 'movies.json' file
    j = open(f'{os.getcwd()}\\step2\\src\\output\\80s-movies.json', 'r', encoding='utf-8')
    movies = json.load(j)

    actors_check()

    connections = check_neighbors(actor1)

    cnt += 1

    unique_cast_connections = remove_duplicates(connections)

    for actor in unique_cast_connections:
        connections = check_neighbors(actor)
        unique_cast_connections_step = remove_duplicates(connections)
        if actor2 in unique_cast_connections_step:
            print(cnt)
            exit(0)

    while cnt <= 7: six_degrees_connections()

    print(f"No six degree connection between {actor1} and {actor2} in the data provided.")


if __name__ == "__main__":
    main()
