package com.gbvbahia.ninjarmm.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Summary {

  private SummaryType type;
  private long degrees;
  private String actor1;
  private String actor2;
  
  @Builder.Default
  private List<String> steps = new ArrayList<>();

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("\n");
    builder.append("************************************************************************");
    builder.append("\n");
    switch (type) {
      case ACTOR_NOT_FOUND:
        builder.append(String.format("%s did not star in a movie in the data provided.", actor1));
        builder.append("\n");
        break;
        
      case DEGREE_NOT_FOUND:
        builder.append(String.format("Not found degrees between %s and %s.", actor1, actor2));
        builder.append("\n");
        break;
        
      default:
        builder.append(String.format("There are %d degrees of separation between %s and %s", degrees, actor1, actor2));
        builder.append("\n");
        steps.forEach(s -> {
          builder.append(s);
          builder.append("\n");
        });
    }
    builder.append("************************************************************************");
    builder.append("\n");
    return builder.toString();
  }
  
  
}
