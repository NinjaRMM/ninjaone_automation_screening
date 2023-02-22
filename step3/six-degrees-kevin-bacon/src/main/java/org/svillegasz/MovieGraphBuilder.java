package org.svillegasz;

import java.util.*;

public class MovieGraphBuilder {
    public static Map<String, Set<String>> buildGraph(List<Movie> movies) {
        Map<String, Set<String>> graph = new HashMap<>();

        for (Movie movie : movies) {
            List<String> cast = movie.getCast();
            for (int i = 0; i < cast.size(); i++) {
                String actor1 = cast.get(i);

                if (!graph.containsKey(actor1)) {
                    graph.put(actor1, new HashSet<>());
                }

                for (int j = i + 1; j < cast.size(); j++) {
                    String actor2 = cast.get(j);

                    if (!graph.containsKey(actor2)) {
                        graph.put(actor2, new HashSet<>());
                    }

                    graph.get(actor1).add(actor2);
                    graph.get(actor2).add(actor1);
                }
            }
        }
        return graph;
    }
}
