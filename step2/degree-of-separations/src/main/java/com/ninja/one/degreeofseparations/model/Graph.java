package com.ninja.one.degreeofseparations.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class Graph {
    private String actorName;
    private String movieName;
    private int degree;
    private String previousContact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return actorName.equals(graph.actorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorName);
    }
}
