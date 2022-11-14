package com.henriquebjr.sixdegrees.model;

import java.util.HashSet;
import java.util.Set;

public class ActorsGraph {

    private Set<ActorNode> nodes = new HashSet<>();

    public void add(ActorNode actorNode) {
        nodes.add(actorNode);
    }

    public Set<ActorNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<ActorNode> nodes) {
        this.nodes = nodes;
    }
}
