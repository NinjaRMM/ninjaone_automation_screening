package com.ninjaone.step3.model.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.ninjaone.step3.model.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastGraph {

    private static final String ERROR_ACTOR_DID_NOT_STAR = "%s did not star in a movie in the data provided.";

    private Map<ActorVertex, List<ActorVertex>> adjacentVertices;

    public CastGraph(Collection<Movie> movies) {

        adjacentVertices = new HashMap<>();

        movies.forEach(movie -> addVerticesByMovie(movie));

        createEdgesByMovieTitles();
    }

    public List<ActorVertex> getDegreesOfSeparation(String root, String target) {

        // check if root and target vertices are present in the graph
        ActorVertex existentRoot = adjacentVertices.keySet().stream()
                                .filter(v -> v.getActor().equals(root))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_ACTOR_DID_NOT_STAR, root)));

        ActorVertex existentTarget = adjacentVertices.keySet().stream()
                                .filter(v -> v.getActor().equals(target))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_ACTOR_DID_NOT_STAR, target)));

        // Breadth First Search
        Set<ActorVertex> visited = new LinkedHashSet<ActorVertex>(Arrays.asList(existentRoot));
        Queue<ActorVertex> queue = new LinkedList<ActorVertex>(Arrays.asList(existentRoot));

        // list of lists to store the path between frontier vertices and the root
        List<LinkedList<ActorVertex>> backTracePaths = new ArrayList<LinkedList<ActorVertex>>();
        backTracePaths.add(new LinkedList<ActorVertex>(Arrays.asList(existentRoot)));

        while (!queue.isEmpty()) {

            ActorVertex vertex = queue.poll();

            LinkedList<ActorVertex> currentPath = backTracePaths.stream()
                                                  .filter(list -> list.getLast().equals(vertex))
                                                  .findAny()
                                                  .orElseThrow();

            for (ActorVertex v : adjacentVertices.get(vertex)) {

                // clone linkedList which last element is vertex
                @SuppressWarnings("unchecked")
                LinkedList<ActorVertex> pathWithCurrentVertex = (LinkedList<ActorVertex>) currentPath.clone();

                // add v to it
                pathWithCurrentVertex.addLast(v);

                // add new linkedList to arrayList
                backTracePaths.add(pathWithCurrentVertex);

                if (v.equals(existentTarget)) {
                     return new ArrayList<ActorVertex>(pathWithCurrentVertex);
                }

                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                }
            }

            // remove 'currentPath' as 'vertex' is no longer part of the search frontier
            backTracePaths.remove(currentPath);
        }

        return Collections.emptyList();
    }

    private void addVerticesByMovie(Movie movie) {

        movie.getCast().forEach(actor -> {

            ActorVertex vertex = new ActorVertex(actor);

            if (adjacentVertices.containsKey(vertex)) {

                ActorVertex existentVertex = adjacentVertices.keySet().stream()
                        .filter(v -> v.getActor().equals(actor))
                        .findAny()
                        .orElseThrow();

                existentVertex.getMovieTitles().add(movie.getTitle());

            } else {

                adjacentVertices.put(new ActorVertex(actor, new HashSet<>(Arrays.asList(movie.getTitle()))), new ArrayList<>());
            }
        });
    }

    private void createEdgesByMovieTitles() {

        Stack<ActorVertex> verticesStack = new Stack<>();
        verticesStack.addAll(adjacentVertices.keySet());

        while (!verticesStack.isEmpty()) {

            ActorVertex vertex = verticesStack.pop();

            verticesStack.stream()
                         .filter(v -> isActedTogether(v, vertex))
                         .forEach(v -> addEdge(v, vertex));
        }
    }

    private Boolean isActedTogether(ActorVertex v1, ActorVertex v2) {
        return v1.getMovieTitles().stream()
                                  .anyMatch(v2.getMovieTitles()::contains);
    }

    private void addEdge(ActorVertex v1, ActorVertex v2) {
        adjacentVertices.get(v1).add(v2);
        adjacentVertices.get(v2).add(v1);
    }
}
