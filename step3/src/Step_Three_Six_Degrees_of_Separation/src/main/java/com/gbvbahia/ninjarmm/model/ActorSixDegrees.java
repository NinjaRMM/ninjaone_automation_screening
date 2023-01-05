package com.gbvbahia.ninjarmm.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorSixDegrees {

  private String name;
  
  @EqualsAndHashCode.Exclude
  @Builder.Default
  private Map<Integer, Set<Movie>> coworkerDegrees = new TreeMap<>();
  
}
