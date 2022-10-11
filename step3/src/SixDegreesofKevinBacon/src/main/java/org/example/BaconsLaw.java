package org.example;

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

public class BaconsLaw {
    @Parameter(names = {"--star", "-s"}, description = "Movie Stars to be evaluated")
    List<String> stars_input = new ArrayList<>();

    private static String inputFilePath = "C:\\Users\\lazar\\Projects\\ninjaone_automation_screening\\data\\test.json";
    List<Movie> movies;
    HashMap<String, Node> baconGraphNodes;
    Graph baconGraph;

    public static void main(String ... argv) throws IOException, ParseException {

        BaconsLaw baconsLaw = new BaconsLaw();
        JCommander.newBuilder()
                .addObject(baconsLaw)
                .build()
                .parse(argv);
        baconsLaw.processInput();
    }

    public BaconsLaw() throws IOException, ParseException {
        this.movies = readMovies();
        this.baconGraphNodes = buildNodes(movies);
        this.baconGraph = buildGraph(baconGraphNodes);
    }

    private void processInput(){

        switch(stars_input.size()) {
            case 1:
                if( starExist(stars_input.get(0))){
                    baconGraph = Dijkstra.calculateShortestPathFromSource(baconGraph,baconGraphNodes.get("Kevin Bacon"));
                    printBaconPath(stars_input.get(0), baconGraph);
                }
                else{
                    System.out.println(stars_input.get(0) + " did not star in a movie in the data provided.");
                }
                break;
            case 2:
                if( !starExist(stars_input.get(0))) {
                    System.out.println(stars_input.get(0) + " did not star in a movie in the data provided.");
                } else if (!starExist(stars_input.get(1))) {
                    System.out.println(stars_input.get(1) + " did not star in a movie in the data provided.");
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

            System.out.println(actorA.getName() + " starred with " + actorB.getName() + " in "
                    + actorA.getAdjacentMovies().get(actorB));
        }

        System.out.println(actorB.getName() + " starred with " + targetStar.getName() + " in "
                + actorB.getAdjacentMovies().get(targetStar) + ".");

    }

    private Graph buildGraph(HashMap<String, Node> nodes) {

        Graph graph = new Graph();

        for (Node node : nodes.values()){
            graph.addNode(node);
        }

        return graph;
    }

    private HashMap<String, Node> buildNodes(List<Movie> movies) {

        HashMap<String, Node> nodes = new HashMap<>();

        List<Pair<String,String>> actorsCombination;
        String actorA;
        String actorB;

        for (Movie movie : movies) {
            actorsCombination = simpleActorsCombination(movie);

            for (Pair<String, String> actorPair : actorsCombination){

                actorA = actorPair.getValue0();
                actorB = actorPair.getValue1();

                nodes.putIfAbsent(actorA, new Node(actorA));
                nodes.putIfAbsent(actorB, new Node(actorB));

                nodes.get(actorA).addDestination(nodes.get(actorB), 1, movie.title);
                nodes.get(actorB).addDestination(nodes.get(actorA), 1, movie.title);
            }
        }

        return nodes;
    }

    private static List<Pair<String, String>> simpleActorsCombination(Movie movie){
        List<Pair<String,String>> combination = new ArrayList<>();

        if (movie.cast.size() > 1){
            for (int i = 0; i < movie.cast.size(); i++) {
                for (int j = i+1; j < movie.cast.size(); j++){
                    combination.add(new Pair<>(movie.cast.get(i), movie.cast.get(j)));
                }
            }
        }

        return combination;
    }

    private List<Movie> readMovies() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray parsedMoviesArray = (JSONArray) parser.parse(new FileReader(inputFilePath));

        List<Movie> movies = new ArrayList<>();

        for (Object parsedMovie : parsedMoviesArray)
        {
            JSONObject movieObject = (JSONObject) parsedMovie;
            String title = (String) movieObject.get("title");
            Integer year = (int)(long) movieObject.get("year");
            ArrayList<String> cast = new ArrayList<>();

            JSONArray parsedCast = (JSONArray) movieObject.get("cast");

            for (Object c : parsedCast)
            {
                cast.add((String) c);
            }

            movies.add(new Movie(title,year, cast));
        }

        return movies;
    }
}
