package com.gbvbahia.ninjarmm.model;

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
  
}
