package com.gbvbahia.ninjarmm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.gbvbahia.ninjarmm.model.ActorSixDegrees;
import com.gbvbahia.ninjarmm.model.Movie;
import com.gbvbahia.ninjarmm.service.io.Movies80ServiceReaderService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SixDegreesService {

  private Movies80ServiceReaderService movies80ServiceReaderService;
  private final Integer maxDegrees;

  public SixDegreesService(Movies80ServiceReaderService movies80ServiceReaderService,
      Integer maxDegrees) {
    super();
    this.movies80ServiceReaderService = movies80ServiceReaderService;
    this.maxDegrees = maxDegrees;
  }



  public void calculateSeparation(String actor1, String actor2) throws Exception {
    
    log.info("Actors to verify: {} and {}", actor1, actor2);
    List<Movie> movies = movies80ServiceReaderService.get80sMovies();
    
    
  }
  
  private void generateDegreeTree(String actorKey, int degree, ActorSixDegrees actorSixDegrees, List<Movie> movies) {
    if (degree > maxDegrees) {
      return;
    }
    
    List<Movie> filtered = movies.stream().filter(m -> {
      return m.getCast().contains(actorKey);
    }).collect(Collectors.toList());
    
    if (actorSixDegrees.getCoworkerDegrees().get(degree) == null) {
      actorSixDegrees.getCoworkerDegrees().put(degree, new HashSet<>(filtered));
    } else {
      actorSixDegrees.getCoworkerDegrees().get(degree).addAll(filtered);
    }
    
    
    
  }
}
