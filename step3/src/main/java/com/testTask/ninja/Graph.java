package com.testTask.ninja;

import com.testTask.ninja.objects.Actor;
import com.testTask.ninja.objects.Movie;
import com.testTask.ninja.objects.Pair;
import com.testTask.ninja.objects.TraverseResult;

import java.util.*;

public class Graph {
    private final Map<Actor, List<Movie>> graph = new HashMap<>();
    private final Set<String> visited = new HashSet<>();

    public Graph(List<Movie> movies) {
        for (Movie movie : movies) {
            List<String> cast = movie.getCast();
            for (String name : cast) {
                Actor actor = new Actor(name);
                graph.computeIfAbsent(actor, p -> new ArrayList<>());
                graph.get(actor).add(movie);
                graph.put(actor, graph.get(actor));
            }
        }
    }

    public TraverseResult traverse(String actor, String actor2) {
        if (validate(actor))
            return new TraverseResult(actor);
        if (validate(actor2))
            return new TraverseResult(actor2);

        Queue<Actor> queue = new LinkedList<>();
        queue.add(new Actor(actor));
        while (!queue.isEmpty()) {
            Actor current = queue.poll();
            if (current.getName().equalsIgnoreCase(actor2)) {
                return new TraverseResult(current.getPairs());
            }
            addToQueue(current, queue);
        }
        return new TraverseResult(actor);
    }

    private void addToQueue(Actor current, Queue<Actor> queue) {
        for (Movie movie : graph.get(current)) {
            movie.getCast().forEach(name -> {
                if (!visited.contains(name)) {
                    Actor actor = new Actor(name);
                    actor.setPairs(new ArrayList<>(current.getPairs()));
                    actor.addPair(new Pair(current, actor, movie));
                    visited.add(name);
                    queue.add(actor);
                }
            });
        }
    }

    private boolean validate(String name) {
        return !graph.containsKey(new Actor(name));
    }
}
