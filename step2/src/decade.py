import json
import sys

decades = {'1900': [1900, 1909],
            '1910': [1910, 1919],
            '20': [1920, 1929],
            '30': [1930, 1939],
            '40': [1940, 1949],
            '50': [1950, 1959],
            '60': [1960, 1969],
            '70': [1970, 1979],
            '80': [1980, 1989],
            '90': [1990, 1999],
            '00': [2000, 2009],
            '10': [2010, 2019]}

def main(decade):

    movies_file = open('/workspaces/ninjaone_automation_screening/data/movies.json', encoding='utf-8')
    movies = json.load(movies_file)

    file_name = decade + 's-movies.json'
    print(file_name)

    result_file = open(file_name, "w", encoding='utf-8')
    result_list = []

    for movie in movies:
        if movie['year'] in range(decades[decade][0], decades[decade][1] + 1):
            result_list.append(movie)

    result_file.write(json.dumps(result_list, indent=4))
    result_file.close()
    movies_file.close()


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Please specify a decade!')
    elif sys.argv[1] not in decades.keys():
        print('Please select a valid decade!')
    else:
        main(sys.argv[1])