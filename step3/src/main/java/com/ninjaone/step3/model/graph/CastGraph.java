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

/**
 * A graph in which vertices are {@link ActorVertex}s, where connecting edges are the movies in which both actors starred.
 */
@Getter
@Setter
public class CastGraph {

    private static final String ERROR_ACTOR_DID_NOT_STAR = "%s did not star in a movie in the data provided.";

    private Map<ActorVertex, List<ActorVertex>> adjacentVertices;

    /**
     * Creates the graph from <b>movies</b>. First it loads all actors as {@link ActorVertex}s, then it connects the ones that
     * have at least one common entry in their <b>movieTitles</b> set.
     */
    public CastGraph(Collection<Movie> movies) {

        adjacentVertices = new HashMap<>();

        movies.forEach(movie -> addVerticesByMovie(movie));

        createEdgesByMovieTitles();
    }

    /**
     * Returns a list "connecting" <b>root</b> and <b>target</b> actors, linked by common movies in which they starred.
     * <br/><br/>
     * For example, consider that actors "Alice" and "Bob" are respectively <b>root</b> and <b>target</b>, and acted
     * together in "The Movie 1". In this case, the list returned will be [Alice, Bob].
     * <br/><br/>
     * On another example, consider again "Alice" and "Bob" as root and target, but they never acted together. In this example,
     * "Charles", a third actor, starred in "The Movie 2" with Alice, and also starred in "The Movie 3", with Bob.
     * <br/>
     * In this case, the return will be [Alice, Charles, Bob].
     */
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
