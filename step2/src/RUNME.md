## Usage
```
usage: step2.py [-h] -d DECADE [-o OUTPUT]

Create a new file containing the movies from a given decade

optional arguments:
  -h, --help            show this help message and exit
  -d DECADE, --decade DECADE
                        a decade to create the new movie json file. Example:
                        "80" or "1910"
  -o OUTPUT, --output OUTPUT
                        the new movie json filename
```
## Examples
    step2.py -d 80

Create a file named "1980s-movies.json" in the data folder containning only movies from 1980 to 1989

    step2.py -d 1910
Create a file named "1980s-movies.json" in the data folder containning only movies from 1910 to 1919

    step2.py -d 2010 -o my_file.json
Create a file named "my_file.json" in the data folder containning only movies from 2010 to 2019

> Note:
I assume we only want ONE decade per file, for example if decade is 10 could be 1910 or 2010 so user need to pass the complete number.
For this reason I decided to use the default output filename as "1910s-movies.json" instead of "10s-movies.json"