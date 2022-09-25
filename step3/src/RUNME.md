## Requirements
Istall python module networkX:

    pip install networkx


## Usage
```
usage: step3.py [-h] -a ACTORS [ACTORS ...] [-m MOVIES]

Print the degrees of separation between two actors

optional arguments:
  -h, --help            show this help message and exit
  -a ACTORS [ACTORS ...], --actors ACTORS [ACTORS ...]
                        one or two actors to get the degrees of deparation.
                        They must be passed using double quotes due the
                        spaces: "Tom Hank" "Tom Cruise"
  -m MOVIES, --movies MOVIES
                        the movies json filename in data folder to use.
                        Default is 1980s-movies.json
```

## Examples
    python step3.py -a "Tom Cruise"

Show the degree of separation between Tom Cruise and the default actor Kevin Bacon.

    python step3.py -a "Tom Cruise" "Tom Hanks"

Show the degree of separation between Tom Cruise and Tom Hanks.

    python step3.py -a "Tom Cruise" "Tom Hanks" -m movies.json

Show the degree of separation between Tom Cruise and Tom Hanks using all the movies.