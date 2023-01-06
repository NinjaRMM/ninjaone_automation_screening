package com.gbvbahia.ninjarmm.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Graph {

  private Map<String, Node> mapNode = new HashMap<>();
  
  public void add(Node node) {
    mapNode.put(node.getName(), node);
  }
  
  public Optional<Node> getNode(String name) {
    return Optional.ofNullable(mapNode.get(name));
  }
  
  public Collection<Node> getAllNodes() {
    return Collections.unmodifiableCollection(mapNode.values());
  }
  
  public Optional<Node> start(String actor) {
    return getNode(actor);
  }
  
  public Optional<Node> end(String actor) {
    return getNode(actor);
  }
}
