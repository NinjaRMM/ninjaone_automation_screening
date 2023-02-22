package org.svillegasz;

import org.svillegasz.exceptions.ActorNotFoundException;
import org.svillegasz.exceptions.ConnectionNotFoundException;

import java.util.*;

import static org.svillegasz.MovieGraphBuilder.buildGraph;
import static org.svillegasz.MovieLoader.loadMovies;

public class SixDegreesOfKevinBacon {
    private Map<String, Set<String>> graph;
    private List<Movie> movies;
    private final Map<String, Integer> degrees = new HashMap<>();
    private final Map<String, String> parents = new HashMap<>();

    public SixDegreesOfKevinBacon(String moviesFilePath) {
        this.movies = loadMovies(moviesFilePath);
        this.graph = buildGraph(movies);
    }

    public void findDegreesOfSeparation(String actor1, String actor2) throws Exception {
        if (!graph.containsKey(actor1)) {
            throw new Exception(actor1 + " did not star in a movie in the data provided.");
        }
        if (!graph.containsKey(actor2)) {
            throw new Exception(actor2 + " did not star in a movie in the data provided.");
        }

        // Start breadth first search over the generated graph starting from actor 1
        bfs(actor1);
        if (degrees.isEmpty() || !degrees.containsKey(actor2)) {
            throw new Exception("No connection found between " + actor1 + " and " + actor2);
        }
        int degree = degrees.get(actor2);
        String path = getPath(actor2);
        System.out.println("There are " + degree + " degrees of separation between " + actor1 + " and " + actor2);
        System.out.println(path);
    }

    private void bfs(String startActor) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(startActor);
        degrees.put(startActor, 0);
        while (!queue.isEmpty()) {
            String actor = queue.poll();
            for (String neighbourActor : graph.get(actor)) {
                if (!degrees.containsKey(neighbourActor)) {
                    degrees.put(neighbourActor, degrees.get(actor) + 1);
                    parents.put(neighbourActor, actor);
                    queue.offer(neighbourActor);
                }
            }
        }
    }

    private String getPath(String actor2) {
        List<String> path = new ArrayList<>();
        for (String actor = actor2; actor != null; actor = parents.get(actor)) {
            path.add(actor);
        }
        Collections.reverse(path);

        List<String> steps = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            Optional<Movie> movie = movies.stream()
                    .filter(m -> m.getCast().contains(from) && m.getCast().contains(to))
                    .findFirst();

            movie.ifPresent(value -> steps.add(String.format("%s starred with %s in %s", from, to, value.getTitle())));
        }

        return String.join("\n", steps);
    }
}
