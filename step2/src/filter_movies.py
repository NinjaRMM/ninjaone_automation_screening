import json, os, re, sys


def validate_input():
    if len(sys.argv) == 2:
        decade = sys.argv[1]
        if re.match(r"^\d{2}$", decade):
            return decade
    sys.exit('Please provide a valid input for decade (like "80").')


# I'm designing this to spec, but I would try to convince the product team that the spec is a bad idea.
# This is because supplying 80 to represent the 1980's decade is ambiguous and could represent multiple decades.
# This becomes a more obvious problem when you supply 10 which returns movies from the 1910's and the 2010's.
def is_year_in_decade(year, decade):
    decade_important_digit = str(decade)[0]
    if re.match(rf"^\d{{2}}{decade_important_digit}\d$", str(year)):
        return True
    return False


decade = validate_input()

# ensure that we can read from our input DB no matter what our working dir is when we call the script
script_dir = os.path.dirname(__file__)
input_movies_file_path = os.path.join(script_dir, "../../data/movies.json")

with open(input_movies_file_path, "r") as file:
    movies = json.load(file)

filtered_movies = [
    movie for movie in movies if is_year_in_decade(movie["year"], decade)
]

# ensure that we write our output DB to the correct location no matter what our working dir is when we call the script
output_movies_file_path = os.path.join(script_dir, f"../../data/{decade}s-movies.json")

with open(output_movies_file_path, "w") as file:
    json.dump(filtered_movies, file, indent=4)
