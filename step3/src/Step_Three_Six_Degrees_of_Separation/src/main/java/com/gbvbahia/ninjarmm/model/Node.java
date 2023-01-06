package com.gbvbahia.ninjarmm.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

  private String name;
  private NodeType type;
  
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private final Set<Node> edges = new HashSet<>();
  
  @EqualsAndHashCode.Exclude
  private boolean searched;
  
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Node parent;
  
  public void addEdge(Node edgeNode) {
    if (StringUtils.equals(name, edgeNode.name)) {
      return;
    }
    getEdges().add(edgeNode);
    edgeNode.getEdges().add(this);
  }
  
  public Optional<Node> getEdge(String name) {
    return getEdges().stream()
        .filter(n -> StringUtils.equals(n.getName(), name))
        .findFirst();
  }
    
  
}
