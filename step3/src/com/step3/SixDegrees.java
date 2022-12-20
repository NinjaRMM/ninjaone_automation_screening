package com.step3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class SixDegrees {
    private static String KEVIN_BACON = "Kevin Bacon";

    // A class to represent a movie
    static class Movie {
        String title;
        List<String> cast;
        int year;
        List<String> genres;
    }

    static class Result {
        String title;
        String actor;
        Result(String title, String actor){
            this.title = title;
            this.actor = actor;
        }
    }

    // A class to represent the data from the "80s-movies.json" file
    static class MovieData {
        List<Movie> movies;
        MovieData(List<Movie> movies){
            this.movies = movies;
        }
    }

    public static void main(String[] args) throws IOException {
        // Parse the "80s-movies.json" file
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader("./data/80s-movies.json"));
        Type collectionType = new TypeToken<Collection<Movie>>(){}.getType();
        Collection<Movie> collection = gson.fromJson(reader, collectionType);

        MovieData data = new MovieData(collection.stream().collect(Collectors.toList()));
        reader.close();

        // Prompt the user for an actor's name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an actor's name: ");
        String actorName = scanner.nextLine();

        System.out.print("Enter Kevin Bacon's name: ");
        KEVIN_BACON = scanner.nextLine();

        // Search for the number of degrees of separation from Kevin Bacon
        List<Result> movies = getDegreeSteps(data, actorName);

        int degrees = (movies.size()-1);
        // Display the results to the user
        System.out.println(degrees);
        System.out.println("There are "+degrees+" degrees of separation between "+actorName+" and "+KEVIN_BACON);
        System.out.println("Movies describing the degree steps: ");
        if(degrees == -1){
            System.out.println(actorName + " did not star in a movie in the data provided.");
            System.exit(0);
        }
        String lastActor = actorName;
        for (Result movie: movies){
            System.out.println(lastActor + " starred with " + movie.actor + " in "+ movie.title);
            lastActor = movie.actor;
        }
    }

    // Returns a list of movies describing the degree steps from the given actor to Kevin Bacon
    public static List<Result> getDegreeSteps(MovieData data, String actorName) {
        // Create a map to store the movies that each actor has appeared in
        Map<String, Set<String>> actorMovies = new HashMap<>();
        for (Movie movie : data.movies) {
            for (String actor : movie.cast) {
                Set<String> movies = actorMovies.get(actor);
                if (movies == null) {
                    movies = new HashSet<>();
                    actorMovies.put(actor, movies);
                }
                movies.add(movie.title);
            }
        }

        // Use a breadth-first search to find the shortest path from the given actor to Kevin Bacon
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Result> predecessor = new HashMap<>();
        queue.add(actorName);
        visited.add(actorName);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentActor = queue.poll();
                if (currentActor.equals(KEVIN_BACON)) {
                    // We found Kevin Bacon, so we can reconstruct the path of movies
                    List<Result> movies = new ArrayList<>();
                    String current = currentActor;
                    while (current != null) {
                        if(predecessor.get(current) != null){
                            movies.add(new Result(predecessor.get(current).title, current));
                        }
                        current = predecessor.get(current) == null ? null : predecessor.get(current).actor;
                    }
                    // Reverse the list of movies to get the path from the given actor to Kevin Bacon
                    Collections.reverse(movies);
                    // Return the list of movies
                    return movies;
                }
                Set<String> movies = actorMovies.get(currentActor);
                if (movies != null) {
                    for (String movie : movies) {
                        for (String actor : getActors(movie, data.movies)) {
                            if (!visited.contains(actor)) {
                                queue.add(actor);
                                visited.add(actor);
                                predecessor.put(actor, new Result(movie, currentActor));
                            }
                        }
                    }
                }
            }
        }
        // We didn't find Kevin Bacon, so return an empty list
        return Collections.emptyList();
    }

    private static List<String> getActors(String movie, List<Movie> movies) {
        for (Movie mv: movies) {
            if(mv.title.equals(movie)){
                return mv.cast;
            }
        }
        return new ArrayList<>();
    }
}