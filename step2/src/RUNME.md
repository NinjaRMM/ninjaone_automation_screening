# How to run filter_movies_by_decade.py

We call the script from a terminal as follows:

```
python filter_movies_by_decade <decade>
```

And we need to provide a decade like 80 as a parameter.
It generates the json output in the ../../data folder.
The output uses 4 digits for the decade to manage the 2000 problem.
Example: When the decade parameter is 10 it creates 2 output files
+ 1910s-movies.json
+ 2010s-movies.json
