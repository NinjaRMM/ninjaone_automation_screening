package com.gbvbahia.ninjarmm.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Movie {

  private String title;
  private int year;
  private List<String> cast;
  private List<String> genres;


  public List<String> getCast() {
    if (cast == null) {
      cast = new ArrayList<>();
    }
    return cast;
  }

  public List<String> getGenres() {
    if (genres == null) {
      genres = new ArrayList<>();
    }
    return genres;
  }
}
