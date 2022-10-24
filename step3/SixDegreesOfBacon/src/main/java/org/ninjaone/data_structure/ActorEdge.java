package org.ninjaone.data_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ActorEdge {
    @Getter
    private final ActorNode source;
    @Getter
    private final ActorNode destination;
    @Getter
    private final int cost = 1;
    @Getter
    private final String movie;

    public ActorEdge(String movie, ActorNode source, ActorNode destination) {
        this.movie = movie;
        this.source = source;
        this.destination = destination;
    }
}
