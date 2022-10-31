package com.ninjaone;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.exit;

public class SixDegrees {

    String start;
    String finish;
    Map<String, List<String>> moviesByStar;
    Map<String, List<String>> shortestPath;
    Queue<Node> candidates = null;


    static class Node implements Comparable<Node> {
        Integer distance;
        String person;
        Node(Integer distance, String person){
            this.distance = distance;
            this.person = person;
        }

        @Override
        public int compareTo(Node o) {
            return o.distance < this.distance ? 1 : -1;
        }

        @Override
        public String toString() {
            return "[" + this.distance + "," + this.person + "]";
        }
    }

    public SixDegrees() {
        finish = "Kevin Bacon";
        moviesByStar = new HashMap<>();
    }

    public void setActorName(String actor1) {
        start = actor1;
    }

    public SixDegrees(String person) throws IOException {
        start = person;
        finish = "Kevin Bacon";
        moviesByStar = new HashMap<>();
        createStarMoviesMap(start);
    }


    public SixDegrees(String person1, String person2) throws IOException {
        start = person1;
        finish = person2;
        moviesByStar = new HashMap<>();
        createStarMoviesMap(start);
        createStarMoviesMap(finish);
    }


    public static void main(String[] args) throws IOException {
        SixDegrees solution = null;
        // provide 1 or 2 as argument
        int n = Integer.parseInt(args[0]);
        if (n == 1) {
            String actor = new SixDegrees().enterStarName();
            solution = new SixDegrees(actor);
        } else {
            if (n == 2) {
                String actor1 = new SixDegrees().enterStarName();
                String actor2 = new SixDegrees().enterStarName();
                solution = new SixDegrees(actor1, actor2);
            }
            else
                System.out.println("Incorrect argument. It must be '1' to provide 1 actor or '2' for 2 actors");
        }
        assert solution != null;
        // Calculate the degrees of distance
        solution.calculateDegrees();
        solution.displayThePath();

    }

    /**
     * Calculate the 6 degrees of distance according to Dijkstras algorithm
     * @throws IOException  It is thrown when the json movies file is missing
     */
    public void calculateDegrees() throws IOException {
        candidates = new PriorityQueue<>();
        Set<String> certain = new HashSet<>();
        Map<String, List<String>> tentative = new HashMap<>();
        Node node = new Node(0, start);
        Node current;

        candidates.add(node);
        tentative.put(start, new ArrayList<>());
        while (candidates.size() > 0) {
            current = candidates.poll();

            if (current.person.equals(finish)) { // We are done!
                shortestPath = tentative;
                String result = "There are " + tentative.get(finish).size() + " degrees of separation between " +
                        start + " and " + finish + ".";
                System.err.println(result);
                break;
            }

            if (certain.contains(current.person))
                continue;
            certain.add(current.person);

            if (!moviesByStar.containsKey(current.person)){
                createStarMoviesMap(current.person);
            }

            // Get the list of node neighbors
            Set<String> neighbors;
            neighbors = getNeighbors(current.person);
            neighbors.removeAll(certain);

            ArrayList<String> path;
            path = new ArrayList<>(tentative.get(current.person));
            path.add(current.person);

            // Calculate the shortest distance for each neighbor node
            for (String neighbor : neighbors) {
                if (tentative.get(current.person).size() <= path.size()) {
                    if (tentative.containsKey(neighbor)) {
                        continue;
                    }
                }
                tentative.put(neighbor, path);
                candidates.add(new Node(path.size(), neighbor));
            }

        }

    }

    /**
     * Display the final result
     */
    public void displayThePath() {
        String result ="";
        ArrayList<String> path = (ArrayList<String>) shortestPath.get(finish);
        path.add(finish);

        for (int i=0; i < path.size() - 1; i++){
            for (int j=0; j < moviesByStar.get(path.get(i)).size(); j++) {

                if (moviesByStar.get(path.get(i)).get(j).contains(path.get(i + 1))){
                    JSONObject movieJson = new JSONObject(moviesByStar.get(path.get(i)).get(j));
                    result = path.get(i) + " starred with "+ path.get(i + 1) + " in " + movieJson.get("title");
                    System.out.println(result);
                }
            }
        }
    }

    /**
     * Prompt the user to enter the Star's name
     * @return returns the star name entered by the user, in the format First Letter Capitalized
     */
    public String enterStarName() {
        String name = "";
        System.out.print("Enter Person's name: ");
        Scanner sc = new Scanner(System.in);
        try {
            if (sc.hasNextLine()) {
                String[] words = sc.nextLine().toLowerCase().split("\\s");
                for (int i = 0; i < words.length; i++) {
                    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1, words[i].length());
                }
                name = String.join(" ", words);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Get the people who have co-starred in a movie with a given person
     * @param person  It is the person in the current node
     * @return set of neighbor nodes
     */
    private Set<String> getNeighbors(String person) {
        Set<String> neighbors = new HashSet<>();
        for(int i =0; i < moviesByStar.get(person).size(); i++){
            JSONObject movieJson = new JSONObject(moviesByStar.get(person).get(i));
            JSONArray castArray = (JSONArray) movieJson.get("cast");
            for (int j = 0; j < castArray.length(); j++) {
                neighbors.add(castArray.get(j).toString());
            }
        }
        return neighbors;
    }

    /**
     * Create the list of movies starred by person
     * @param person         It is the star who we'll collect their movies
     * @throws IOException   It is thrown when the json movies is missing
     */
    private void createStarMoviesMap(String person) throws IOException {
        String fileName = "../step2/80s-movies.json";
        JSONArray jsonArray = new JSONArray(new String(Files.readAllBytes(Paths.get(fileName))));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieJson = jsonArray.getJSONObject(i);
            JSONArray castArray = (JSONArray) movieJson.get("cast");
            for (int j = 0; j < castArray.length(); j++) {
                if (person.equals(castArray.get(j))) {
                    if (moviesByStar.containsKey(person)) {
                        moviesByStar.get(person).add(movieJson.toString());
                    }
                    else {
                        moviesByStar.put(person, new ArrayList<>(List.of(movieJson.toString())));
                    }
                }
            }
        }
        if (!moviesByStar.containsKey(person)){
            System.out.println(person + " did not star in a movie in the data provided.");
            exit(0);
        }
    }
}
