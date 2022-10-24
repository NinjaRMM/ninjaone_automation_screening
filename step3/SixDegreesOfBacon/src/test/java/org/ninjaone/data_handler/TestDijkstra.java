package org.ninjaone.data_handler;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.ninjaone.data_handler.Dijkstra;

import org.junit.jupiter.api.Test;
import org.ninjaone.data_structure.ActorEdge;
import org.ninjaone.data_structure.ActorNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestDijkstra {

    public static final String MOVIE_A = "A";
    public static final String MOVIE_B = "B";
    public static final String MOVIE_C = "C";
    static Dijkstra dijkstra;

    static ActorNode a1 = new ActorNode("1");
    static ActorNode a2 = new ActorNode("2");
    static ActorNode a3 = new ActorNode("3");
    static ActorNode a4 = new ActorNode("4");

    @BeforeAll
    static void configure() {
        Map<ActorNode, List<ActorEdge>> actorNodeListMap = new HashMap<>();

        actorNodeListMap.put(a1, new LinkedList<>());
        actorNodeListMap.put(a2, new LinkedList<>());
        actorNodeListMap.put(a3, new LinkedList<>());
        actorNodeListMap.put(a4, new LinkedList<>());


        actorNodeListMap.get(a1).add(new ActorEdge(MOVIE_A, a1, a2));
        actorNodeListMap.get(a2).add(new ActorEdge(MOVIE_A, a2, a1));


        actorNodeListMap.get(a2).add(new ActorEdge(MOVIE_B, a2, a3));
        actorNodeListMap.get(a3).add(new ActorEdge(MOVIE_B, a3, a2));

        actorNodeListMap.get(a3).add(new ActorEdge(MOVIE_C, a3, a4));
        actorNodeListMap.get(a4).add(new ActorEdge(MOVIE_C, a4, a3));

        dijkstra = new Dijkstra(actorNodeListMap);
    }

    @Test
    void testPathFromA1toA2() {
        dijkstra.execute(a1);

        List<ActorEdge> path = dijkstra.getPath(a2);

        Assertions.assertSame(path.size(), 1);
        Assertions.assertEquals(path.get(0).getSource(), a2);
        Assertions.assertEquals(path.get(0).getDestination(), a1);
        Assertions.assertEquals(path.get(0).getMovie(), MOVIE_A);
    }

    @Test
    void testPathFromA1toA4() {
        dijkstra.execute(a1);

        List<ActorEdge> path = dijkstra.getPath(a4);

        Assertions.assertSame(path.size(), 3);
        Assertions.assertEquals(path.get(0).getSource(), a2);
        Assertions.assertEquals(path.get(0).getDestination(), a1);
        Assertions.assertEquals(path.get(0).getMovie(), MOVIE_A);

        Assertions.assertEquals(path.get(1).getSource(), a3);
        Assertions.assertEquals(path.get(1).getDestination(), a2);
        Assertions.assertEquals(path.get(1).getMovie(), MOVIE_B);

        Assertions.assertEquals(path.get(2).getSource(), a4);
        Assertions.assertEquals(path.get(2).getDestination(), a3);
        Assertions.assertEquals(path.get(2).getMovie(), MOVIE_C);
    }
}
