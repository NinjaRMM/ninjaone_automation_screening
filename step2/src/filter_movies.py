import sys
import json

data_path = '../../data/'
file_name = 'movies.json'


def load_movies_from_file():
    with open(f'{data_path}{file_name}') as file:
        return json.load(file)


def filter_movies(movies, decade):
    decade = 1900 + decade
    filtered = [movie for movie in movies if decade <= int(movie['year']) < decade + 10]
    return filtered


def write_file(data, output_file):
    with open(f'{data_path}{output_file}.json', 'w') as file:
        print(f'Printing output: {output_file}.json')
        file.write(json.dumps(data, indent=4))


if __name__ == '__main__':
    # validate args
    n = len(sys.argv)
    if n != 3:
        print("Wrong Arguments!")
        print("Example:\n $ python filter_movies.py 80 80s-movies")
        exit(1)

    decade_arg = int(sys.argv[1])
    output_file_name = sys.argv[2]
    if decade_arg not in range(10, 91, 10):
        print("Wrong Decade!")
        print("Supported: 10 to 90")
        exit(1)
    # Execute filtering
    all_movies = load_movies_from_file()
    movies_by_decade = filter_movies(all_movies, decade_arg)
    write_file(movies_by_decade, output_file_name)
    exit(0)
