#Roberto Lavoie STEP 2
#Python file to read data json
import json, sys


#I used in WSL in ubuntu for windows ..\..\data
datafolder = '../../data/'
decada = sys.argv[1]
year = int("19"+decada)
result =[]
print(f"STEP 2 YEAR to filter {year}'s")
# Read the file with JSON and load
with open(r""+datafolder+"movies.json", 'r') as file:
   fdata = json.load(file)
# loop its content
for item in fdata:
    # 1980 ->1989
    for i in range(0,10):
        if item['year']==year+i:     
            result.append(item)
            #print so i can see this can be removed
            print (item)
#save file as intended
with open(r""+datafolder+decada+"s-movies"+".json", "w") as outfile:
    json.dump(result,outfile)

