#Roberto Lavoie STEP 3
#!/usr/bin/python3

import json, sys

#I used in WSL in ubuntu 
datafolder = '../../data/'
filename = "80s-movies.json"
# Arguments for script

###############################################################################
# INPUT ARGUMENTS
###############################################################################
if len(sys.argv) == 1:
    #if no arguments where given to the script
    print(f'This script will need maximum 2 arguments to find  Degrees of Separation')
    print(f'_________________________________________________________________________')
    print(f'Script needs an Actor Name from the 80`s wth ex: {sys.argv[0]}  "Tom Cruise" ')
    print(f'Script needs an Actor Name from the 80`s wth ex: {sys.argv[0]}  "Tom Cruise" "Arnold Schwarzenegger"')
    exit() 
if len(sys.argv) == 2:
        actor1 = str(sys.argv[1])
        actor2 = "Kevin Bacon"
if len(sys.argv) == 3:
        actor1 = str(sys.argv[1])
        actor2 = str(sys.argv[2])
if len(sys.argv)>3:
    print(f'Too many arguments')
    exit() 

########################
# DECLARATION
# Not needed but i did anyway
actor1titles =[]
actor1friends=[]
actor2titles =[]
actor2friends=[]
bothfriends=[]
commonfriend=[]
bothtitles =[]
result =[]


##############################################################################
# READ FILE 
##############################################################################
# Read the file with JSON and load to the memory
#
with open(r""+datafolder+ filename, 'r') as file:
   fdata = json.load(file)

##############################################################################
# FUNCTIONS
##############################################################################
# FUNCTIONS : UNIQUELIST
# DESCRIPTION : Remove all the duplicates
# ARGUMENTS : List of names 
# RETURN : Same list with unique names (no duplicates)
def uniquelist(listofnames):
    temp = []
    for array in listofnames:
        for name in array:
            if name not in temp:
                temp.append(name)
    return temp                
# FUNCTION : union
# DESCRIPTION : 2 LISTS are received and it returns elements that are the same
# ARGUMENTS : LISTS of names
# RETURN list of names that are the same between them
def union(list1, list2):
    result =[]
    for name2 in list2:
        for name1 in list1:
            if name1 ==name2:
                result.append(name1)
    return result
# FUNCTION listofmovies
# DESCRIPTION : Receive actor name and return movie list
# ARGUMENTS actor name string
# RETURN list of movies that actor appeared in
def listofmovies(actor):
    result = []
    for item in fdata:
        movie =(item["title"])
        for i in (item['cast']):
            if i==actor:
                result.append(movie)        
    return result

# FUNCTION listoffilms
# DESCRIPTON : RECEIVE LISTS of ACTORS and RETURN LIST of ALL Movies 
# ARGUMENTS List of actors
# RETURN list of all movies of the actors
def listoffilms(actors):
    temp= []
    for actor in actors:
        temp=(listofmovies(actor))
    return temp

# FUNCTIONS listofactors
# ARGUMENTS actor name string
# RETURN list of cast that actor appeared in
def listofactors(actor):
    result = []
    for item in fdata:
        friends =(item["cast"])
        for i in (item['cast']):
           if i==actor:
                result.append(friends)
    return result


# FUNCTIONS printresult
# ARGUMENTS degree ( degrees of union
# ARGUMENTS friend1 actor name related to actor 1
# ARGUMENTS friend2 actor name related to actor 2
# ARGUMENTS film1 film related to friend 1 and friend 2
# ARGUMENTS movie2 film related to actor 2 and friend 2
# Return Zero
def printresult(degree,friend1,friend2,film1, movie1,movie2):
    print(f'____________________________________________________________________________________')    
    if(degree==1):
        print(f'There is {degree} degrees of Separation bettwen {actor1} and {actor2} :')
        print(f'    {actor1} starred with {friend1} in {movie1}')
        print(f'    {friend1} starred with {actor2} in  {movie2}')
    if(degree==2):
        print(f'There is {degree} degrees of Separation bettwen {actor1} and {actor2} :')
        print(f'    {actor1} starred with {friend1} in {movie1}')
        print(f'    {friend1} starred with {friend2} in {film1}')
        print(f'    {friend2} starred with {actor2} in {movie2}')
    
    return 0
############################################################3
##########################################################3
# MAIN 
#############################################################

# Check if actor exist otherwise exit
actor1titles= listofmovies(actor1)
actor2titles= listofmovies(actor2)
if len(actor1titles)==0:
    print(f'{actor1} did not star in a movie in the data provided.')
    exit()
elif len(actor2titles)==0:
    print(f'{actor2} did not star in a movie in the data provided. ')
    exit()

# 
# IF they have played with each other means the level 0 , means they know each other
# 
print(f"******************************************************************************************")
print(f"STEP 3 Find Degree of Separation of {actor1} and {actor2}")
commonfriend = union(actor1titles, actor2titles)
# If This is Right exit code
if len(commonfriend) >= 1:
    print(f'There is 0 level of union bettwen {actor1} and {actor2}')
    for movie in commonfriend:
        print(f'{actor1} starred with {actor2} in {movie}')
        exit()   
#########################################################################################################
#
# 1 degree of separation 
# THIS WAS NOT ASKED But I think this is the spirit of this script
#
# List all the cast from all the movies played by Actor1 and Actor2 
actor1friends= listofactors(actor1)
actor2friends= listofactors(actor2)
# Remove duplicates
actor1friends= uniquelist(actor1friends)
actor2friends= uniquelist(actor2friends)
#Union of the 2 list means common actors that link btw the 2
commonfriend = union(actor1friends, actor2friends)
# IF COMMON FRIEND IS LESS THAN 1 THAN it is not a 1 degree of Separation , skip ahead
if len(commonfriend) >= 1:
    #list of movies of common friend
    commonmovie= (listoffilms(commonfriend))
    #list of the movies the actor1 is in
    movielist1= (union(commonmovie,actor1titles))
    #list of the movies the actor2 is in 
    movielist2=(union(commonmovie,actor2titles))
    #For each actor that is common print the list of the movies 
    for friends in commonfriend:
        #go to the list of movies from actor 1
        for movie1 in movielist1:
            #go to the list of movies from actor 2
            for movie2 in movielist2:
                #print results
                printresult(1,friends,"","", movie1, movie2)
                    
#if zero we will go to level 2 of union for each friend from actor1 we must find a common movie with actor2
else:   
    #SECOND DEG OF union
    #For each friend of actor seek the movies they are on 
    for friend1 in actor1friends:
        #print(f' {actor1} FRIEND :{friend}')
        movies1= (listofmovies(friend1))
        #search in actor2 movies list
        for friend2 in actor2friends:
            movies2= (listofmovies(friend2))
            for film2 in movies2:
                for film1 in movies1:
                    #print(f'FILM : {film1} and {film2}')
                    if film1 == film2:
                        degreesofunionlist1= (union(movies1,actor1titles))
                        degreesofunionlist2= (union(movies2,actor2titles))
                        for f_movies1 in degreesofunionlist1:
                            for f_movies2 in degreesofunionlist2:
                                printresult(2,friend1,friend2,film1, f_movies1, f_movies2)

