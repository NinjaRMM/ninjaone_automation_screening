#Roberto Lavoie STEP 3
#!/usr/bin/python3
# I did not finished this one it will take more much time todo it
import json, sys, re
from collections import defaultdict

#I used in WSL in ubuntu for windows ..\..\data
datafolder = '../../data/'
actor1 = str(sys.argv[1])
#actor1 ="Tom Cruise"
actor2 = "Kevin Bacon"
#year = int("19"+decada)
cast =[]
groupcast =[]
result =[]
# Read the file with JSON and load
print(f"STEP 3 Find separation from {actor1}")
with open(r""+datafolder+"80s-movies.json", 'r') as file:
   fdata = json.load(file)

d=defaultdict(dict)
# loop its content
loop = 0
for item in fdata:
    loop = loop+1
    print(loop)
    for i in enumerate(item['cast']):
        cast.append(i)
for actors in enumerate(cast):
    print(actors)

