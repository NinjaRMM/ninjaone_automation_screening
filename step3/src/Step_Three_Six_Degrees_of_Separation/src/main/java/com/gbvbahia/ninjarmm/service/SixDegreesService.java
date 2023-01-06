package com.gbvbahia.ninjarmm.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import org.springframework.stereotype.Service;
import com.gbvbahia.ninjarmm.model.Graph;
import com.gbvbahia.ninjarmm.model.Movie;
import com.gbvbahia.ninjarmm.model.Node;
import com.gbvbahia.ninjarmm.model.NodeType;
import com.gbvbahia.ninjarmm.model.Summary;
import com.gbvbahia.ninjarmm.model.SummaryType;
import com.gbvbahia.ninjarmm.service.io.Movies80ServiceReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SixDegreesService {

  private final Movies80ServiceReaderService movies80ServiceReaderService;

  public Summary calculateSeparation(String actor1, String actor2)
      throws Exception {

    log.info("Actors to verify: {} and {}", actor1, actor2);
    List<Movie> movies = movies80ServiceReaderService.get80sMovies();
    
    Graph graph = generateGraph(movies);

    Optional<Node> iOpt = graph.getNode(actor1);
    if (iOpt.isEmpty()) {
      return createSummaryActorNotFound(actor1);
    }
    
    Optional<Node> eOpt = graph.getNode(actor2);
    if (eOpt.isEmpty()) {
      return createSummaryActorNotFound(actor2);
    }
    
    Node ini = iOpt.get();
    Node end = eOpt.get();
    
    Optional<Node> found = executeBreadthFirstSearch(ini, end);
    
    if (found.isEmpty()) {
      Summary summary = createSumarryNotFoundDegreesBetweenActors(actor1, actor2, ini, end);
      return summary;
    }
    
    List<Node> path = createTrackPath(ini, found.get());
    
    Summary summary = createSummaryDegreesFound(actor1, actor2, path);
    
    return summary;

  }

  private Graph generateGraph(List<Movie> movies) {
    Graph graph = new Graph();
    
    for (Movie movie : movies) {
      Node movieNode = new Node();
      movieNode.setName(movie.getTitle());
      movieNode.setType(NodeType.MOVIE);
      graph.add(movieNode);
      
      for (String actor : movie.getCast()) {
        Optional<Node> actorNodeOpt = graph.getNode(actor);
        Node actorNode = actorNodeOpt.orElse(new Node());
        actorNode.setName(actor);
        actorNode.setType(NodeType.ACTOR);
        movieNode.addEdge(actorNode);
        graph.add(actorNode);
      }
    }
    return graph;
  }
 
  private Summary createSummaryActorNotFound(String actor) {
    return Summary.builder()
        .actor1(actor)
        .type(SummaryType.ACTOR_NOT_FOUND)
        .build();
  }
  
  private Optional<Node> executeBreadthFirstSearch(Node ini, Node end) {
    
    Node found = null;
    Queue<Node> queue = new LinkedList<Node>();  
    ini.setSearched(true);
    queue.offer(ini);
    
    while (!queue.isEmpty()) {
      
      Node currentNode = queue.poll();
      if (end.equals(currentNode)) {
        found = currentNode;
        break;
      }
      
      for (Node edge : currentNode.getEdges()) {
        if (!edge.isSearched()) {
          edge.setSearched(true);
          edge.setParent(currentNode);
          queue.offer(edge);
        }
      }
    }
    return Optional.ofNullable(found);
  }
  
  private Summary createSumarryNotFoundDegreesBetweenActors(String actor1, String actor2,
      Node ini, Node end) {
    
    Summary summary = Summary.builder()
        .degrees(0)
        .actor1(actor1)
        .actor2(actor2)
        .type(SummaryType.DEGREE_NOT_FOUND)
        .build();
    
    return summary;
  }
  
  private List<Node> createTrackPath(Node ini, Node found) {
    List<Node> path = new ArrayList<>(); 

    while (found.getParent() != null) {
      path.add(found);
      found = found.getParent();
    }
    path.add(ini);
    return path;
  }
  
  private Summary createSummaryDegreesFound(String actor1, String actor2, List<Node> path) {
    Summary summary = Summary.builder()
        .degrees(path.stream().filter(n -> NodeType.MOVIE.equals(n.getType())).count())
        .actor1(actor1)
        .actor2(actor2)
        .type(SummaryType.FOUND)
        .build();
    
    for (int i = 0; i < path.size(); i = i + 3) {
      Node n1 = path.get(i);
      Node nm = path.get(i + 1);
      Node n2 = path.get(i + 2);
      summary.getSteps().add(String.format("%s starred with %s in %s", n1.getName(), n2.getName(), nm.getName()));
    }
    return summary;
  }
}
