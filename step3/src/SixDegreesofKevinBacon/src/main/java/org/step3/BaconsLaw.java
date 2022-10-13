package org.step3;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.dijkstra.Dijkstra;
import org.dijkstra.Graph;
import org.dijkstra.Node;
import org.javatuples.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// It does not cover when there are no connection between the stars.
// It does not cover when same stars are given as parameter
// It does not cover when more than 2 stars are given as parameter.

// It was really fun to find the Easter egg :]

public class BaconsLaw {

    @Parameter(names = {"--star", "-s"}, description = "Movie Stars to be evaluated", required = true)
    List<String> stars_input = new ArrayList<>();
    @Parameter(names = {"--path", "-p"}, description = "Path to the source JSON file", required = true)
    String jsonPath;

    private List<Movie> movies = new ArrayList<>();
    private Map<String, Node> baconGraphNodes = new HashMap<>();
    private Graph baconGraph = new Graph();

    public static void main(String ... argv) throws IOException, ParseException {

        BaconsLaw baconsLaw = new BaconsLaw();
        JCommander.newBuilder()
                .addObject(baconsLaw)
                .build()
                .parse(argv);

        baconsLaw.readMovies();
        baconsLaw.buildNodes();
        baconsLaw.buildGraph();
        baconsLaw.processInput();
    }

    private void processInput(){

        switch(stars_input.size()) {
            case 1:
                if( starExist(stars_input.get(0))){
                    baconGraph = Dijkstra.calculateShortestPathFromSource(baconGraph,baconGraphNodes.get("Kevin Bacon"));
                    printBaconPath(stars_input.get(0), baconGraph);
                }
                else{
                    printStarStatus(stars_input.get(0));
                }
                break;
            case 2:
                if( !starExist(stars_input.get(0))) {
                    printStarStatus(stars_input.get(0));
                } else if (!starExist(stars_input.get(1))) {
                    printStarStatus(stars_input.get(1));
                } else {
                    baconGraph = Dijkstra.calculateShortestPathFromSource(baconGraph, baconGraphNodes.get(stars_input.get(0)));
                    printBaconPath(stars_input.get(1), baconGraph);
                }
                break;
        }
    }

    private boolean starExist(String starName) {
        boolean starExist = false;
        int index = 0;

        while(!starExist && index < movies.size()) {
            starExist = movies.get(index).cast.contains(starName);
            index++;
        }
        return starExist;
    }

    private void printStarStatus(String starA, String StarB, String movie){
        System.out.println(starA + " starred with " + StarB + " in " + movie);
    }

    private void printStarStatus(String starA){
        System.out.println(starA + " did not star in a movie in the data provided.");
    }

    private void printBaconPath(String targetStarName, Graph baconGraph){
        Node targetStar = Dijkstra.findNodeFromName(baconGraph, targetStarName);
        List<Node> shortestPath = targetStar.getShortestPath();

        System.out.println();
        System.out.println("There are " + (targetStar.getDistance() - 1) + " degrees of separation between "
                + shortestPath.get(0).getName() + " and " + targetStarName);

        Node actorA;
        Node actorB = shortestPath.get(0);

        for (int i = 0; i < shortestPath.size() - 1; i++) {

            actorA = shortestPath.get(i);
            actorB = shortestPath.get(i+1);
            printStarStatus(actorA.getName(), actorB.getName(), actorA.getAdjacentMovies().get(actorB));
        }

        printStarStatus(actorB.getName(), targetStar.getName(), actorB.getAdjacentMovies().get(targetStar));
    }

    private void buildGraph() {
        for (Node node : baconGraphNodes.values()){
            baconGraph.addNode(node);
        }
    }

    private void buildNodes() {

        List<Pair<String,String>> actorsCombination;
        String actorA;
        String actorB;

        for (Movie movie : movies) {
            actorsCombination = simpleActorsCombination(movie);

            for (Pair<String, String> actorPair : actorsCombination){

                actorA = actorPair.getValue0();
                actorB = actorPair.getValue1();

                baconGraphNodes.putIfAbsent(actorA, new Node(actorA));
                baconGraphNodes.putIfAbsent(actorB, new Node(actorB));

                baconGraphNodes.get(actorA).addDestination(baconGraphNodes.get(actorB), 1, movie.title);
                baconGraphNodes.get(actorB).addDestination(baconGraphNodes.get(actorA), 1, movie.title);
            }
        }
    }

    private static List<Pair<String, String>> simpleActorsCombination(Movie movie){
        List<Pair<String,String>> combination = new ArrayList<>();

        if (movie.cast.size() > 1) {
            for (int i = 0; i < movie.cast.size(); i++) {
                for (int j = i+1; j < movie.cast.size(); j++){
                    combination.add(new Pair<>(movie.cast.get(i), movie.cast.get(j)));
                }
            }
        }

        return combination;
    }

    private void readMovies() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        System.out.println(jsonPath);
        JSONArray parsedMoviesArray = (JSONArray) parser.parse(new FileReader(jsonPath));

        for (Object parsedMovie : parsedMoviesArray)
        {
            JSONObject movieObject = (JSONObject) parsedMovie;
            String title = (String) movieObject.get("title");
            Long year = (Long) movieObject.get("year");
            ArrayList<String> cast = new ArrayList<>();

            JSONArray parsedCast = (JSONArray) movieObject.get("cast");

            for (Object c : parsedCast)
            {
                cast.add((String) c);
            }

            movies.add(new Movie(title,year, cast));
        }
    }
}
