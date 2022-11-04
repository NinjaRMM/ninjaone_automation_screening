import sys, os, json


def get_movies():
    # Declaring arguments
    arg1 = sys.argv[1].replace("-", "")
    arg2 = sys.argv[2]
    arg3 = sys.argv[3].replace("-", "")
    arg4 = sys.argv[4]

    # Array of expected year arguments
    y = ['10', '20', '30', '40', '50', '60', '70', '80', '90', '00']

    output = []

    # Invalid arguments handling
    if arg1 != "decade":
        raise ValueError(f"Unknown arg: '{arg1}'. 'decade' argument expected.")
    if arg2 not in y:
        raise ValueError(f"Please check your input. Expected decade range: 1900 - 2018")
    if arg3 != "output":
        raise ValueError(f"Unknown arg: '{arg3}'. 'output' argument expected.")
    if ".json" not in arg4:
        raise ValueError(f"Output file must be JSON file")

    # There are 2 pairs of decades that can be confused: 1900 & 2000, 1910 & 2010
    if arg2 == '00' or arg2 == '10':
        inp = input("Please clarify the century: enter 'xx' for 20th or 'xxi' for 21st: ")
        if inp == 'xx':
            decade = '19' + arg2
        elif inp == 'xxi':
            decade = '20' + arg2
        else:
            raise ValueError
    else:
        decade = '19' + arg2

    # Parsing the 'movies.json' file
    j = open(f'{os.getcwd()}/data/movies.json', 'r', encoding='utf-8')
    movies = json.load(j)

    # Checking against each entry if it is in the decade range
    for i in movies:
        if i['year'] in range(int(decade), int(decade) + 10):
            output.append(i)

    # Creating a new JSON output file with the final result
    result = json.dumps(output, indent=4)
    with open(f'{os.getcwd()}/step2/src/output/{arg4}', 'w') as outfile:
        outfile.write(result)


if __name__ == "__main__":
    get_movies()
