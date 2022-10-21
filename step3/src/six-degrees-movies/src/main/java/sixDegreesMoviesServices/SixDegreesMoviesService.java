package sixDegreesMoviesServices;

import DTO.MovieDTO;
import DTO.MovieNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.FileController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SixDegreesMoviesService {

    private static final String DEGREES_BETWEEN_ACTORS = "There are %d degrees of separation between %s and %s";
    private static final String ACTORS_STARRED_WITH = "%s starred with %s in %s";
    private static final String NO_PATH_FOUND = "There aren't path between %s and %s";
    private static final String MISSING_ARGUMENTS = "Missing arguments <actor1> <actor2>[Optional]";
    private static final String ACTOR_NOT_FOUND = "%s did not star in a movie in the data provided.";
    private static final String KEVIN_BACON = "Kevin Bacon";


    private final FileController fileController = new FileController();

    private List<Integer> finalMovies;
    private List<MovieDTO> movies = new ArrayList<>();
    private Set<String> actorVisited = new HashSet<>();
    private Queue<MovieNode> queueMovies = new LinkedList<>();

    private String fromActor = StringUtils.EMPTY;
    private String toActor = StringUtils.EMPTY;

    private MovieNode backwardParents;

    /**
     * Execute path calculator to validate six degree between two actors
     * @param args with actors to be analyzed
     * @return String with message
     */
    public String performanceExecution(String[] args){

        Gson gson = new Gson();

        if(args.length < 1){
            return MISSING_ARGUMENTS;
        }

        //Using a static path, but this could be parameterized
        try(JsonReader reader = fileController.openFile("../../../output-from-1980-to-1989.json")) {

            movies = gson.fromJson(reader, new TypeToken<List<MovieDTO>>(){}.getType());
            movies = movies.stream().filter(movie -> movie.getCast().size() > 0).collect(Collectors.toList());

            indexMovies(movies);

            fromActor = args[0];
            List<MovieDTO> fromActorMovies = getActorMovies(fromActor);
            if(fromActorMovies.size() == 0){
                return String.format(ACTOR_NOT_FOUND, fromActor);
            }

            if (args.length > 1) {
                toActor = args[1];
            }else{
                toActor = KEVIN_BACON;
            }

            List<MovieDTO> toActorMovies = getActorMovies(toActor);
            if(toActorMovies.size() == 0){
                return String.format(ACTOR_NOT_FOUND, fromActor);
            }

            finalMovies = toActorMovies.stream().map(toActorMovie -> toActorMovie.getIndexMovie())
                    .collect(Collectors.toList());

            setUp(fromActorMovies);

            backwardParents = thereArePathBetweenActors();



        }catch (FileNotFoundException e){
            return "Cant not find movies file, should be in resources folder";
        }catch (IOException e){
            return "Error reading or writing file";
        }

        return buildFinalStringMessage();
    }

    /**
     * Build message that will show on console
     * @return message
     */
    private String buildFinalStringMessage(){

        StringBuilder finalMessage = new StringBuilder();

        if(ObjectUtils.isNotEmpty(backwardParents)){

            Stack<String> stackActors = new Stack<>();
            Stack<String> stackMovies = new Stack<>();
            stackActors.push(toActor);

            finalMessage.append(String.format(DEGREES_BETWEEN_ACTORS, backwardParents.getLevel(), fromActor, toActor))
                    .append(System.lineSeparator());


            while(ObjectUtils.isNotEmpty(backwardParents)){
                stackActors.push(backwardParents.getLinkedActor());
                stackMovies.push(movies.get(backwardParents.getMovieIndex()).getTitle());
                backwardParents = backwardParents.getParent();
            }

            while (!stackActors.isEmpty() && !stackMovies.isEmpty()){
                String actorStaredWith = stackActors.pop();
                String actualActor = stackActors.pop();
                String movieTitle = stackMovies.pop();
                finalMessage.append(String.format(ACTORS_STARRED_WITH, actorStaredWith, actualActor, movieTitle))
                        .append(System.lineSeparator());
                if(!stackActors.isEmpty()) stackActors.push(actualActor);
            }

        }else{
            finalMessage.append(String.format(NO_PATH_FOUND, fromActor, toActor));
        }
        return finalMessage.toString();
    }

    /**
     * Calculate path between two actors
     * Just find movies from actor1 and actor2, and use BFS to get the path
     * @return a MovieNode if there are path between actors or null if is infinite
     */
    private MovieNode thereArePathBetweenActors(){
        int actualMovieIndex;
        MovieNode actualMovieToValidate;

        while (!queueMovies.isEmpty()){
            actualMovieToValidate = queueMovies.poll();
            actualMovieIndex = actualMovieToValidate.getMovieIndex();
            if(finalMovies.contains(actualMovieIndex)){
                return actualMovieToValidate;
            }

            movies.get(actualMovieIndex).setVisited(Boolean.TRUE);
            for(String actor : movies.get(actualMovieIndex).getCast()){
                if(!actorVisited.contains(actor)){
                    actorVisited.add(actor);
                    MovieNode finalActualMovieToValidate = actualMovieToValidate;
                    movies.stream().filter(movie -> !movie.isVisited() &&
                                    movie.getCast().contains(actor))
                            .forEach(movie -> enqueueMovie(finalActualMovieToValidate,
                                    movie.getIndexMovie(), actor));
                }
            }
        }
        return null;
    }

    /**
     * Insert first nodes to be analized
     * @param fromActorMovies list of movies from the actor started
     */
    private void setUp(List<MovieDTO> fromActorMovies){

        int initialStep = 0;

        for(MovieDTO movie : fromActorMovies){
            movie.setVisited(Boolean.TRUE);
            MovieNode movieNode = new MovieNode();
            movieNode.setMovieIndex(movie.getIndexMovie());
            movieNode.setLinkedActor(fromActor);
            movieNode.setLevel(initialStep);
            actorVisited.add(fromActor);
            queueMovies.add(movieNode);
        }
    }

    /**
     * Get all movies where actor starred
     * @param actorName actor's name
     * @return List of movies
     */
    private List<MovieDTO> getActorMovies(String actorName){
        return movies.stream().filter(movie -> movie.getCast()
                .contains(actorName)).collect(Collectors.toList());
    }

    /**
     * Enqueue new node into queue
     * @param movieNode
     * @param indexMovie
     * @param linkedActor
     */
    private void enqueueMovie(MovieNode movieNode, int indexMovie, String linkedActor){
        MovieNode newMovieNode = new MovieNode();
        newMovieNode.setParent(movieNode);
        newMovieNode.setMovieIndex(indexMovie);
        newMovieNode.setLinkedActor(linkedActor);
        newMovieNode.setLevel(movieNode.getLevel() + 1);
        queueMovies.add(newMovieNode);
    }

    /**
     * Index movie for each movie
     * @param movies
     */
    private void indexMovies(List<MovieDTO> movies){
        for(int i=0; i<movies.size(); i++){
            movies.get(i).setIndexMovie(i);
        }
    }

}
