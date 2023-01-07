package com.gbvbahia.ninjarmm.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gbvbahia.ninjarmm.model.Movie;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MoviesDataReaderService {

  private static final Integer YEARS_AFTER_DECADE_INIT = 9; 
  
  private final String moviesFile;

  public MoviesDataReaderService(@Value("${app.data.movies.json}") String moviesFile) {
    super();
    this.moviesFile = moviesFile;
  }

  @PostConstruct
  void init() throws Exception {
    
    if (Files.exists(Path.of(moviesFile))) {
      log.info("File {} was found!", moviesFile);
      
    } else {
      String error = String.format("File %s was not found. pwd is: %s", moviesFile,
          new File(".").getCanonicalPath());
      throw new IOException(error);
    }
  }

  public void fetchAllMoviesByDecade(int decadeInit, WriterService<Movie> processMovie) throws Exception {

    try (
        InputStream inputStream = Files.newInputStream(Path.of(moviesFile));
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        ) {
      
      reader.beginArray();

      while (reader.hasNext()) {
        Movie movie = new Gson().fromJson(reader, Movie.class);
        if (isYearIsBetween(movie.getYear(), decadeInit, decadeInit + YEARS_AFTER_DECADE_INIT)) {
          processMovie.process(movie);
        }
      }
      
      reader.endArray();
    }
  }

  private boolean isYearIsBetween(int year, int decadeInit, int decadeEnd) {
    return year >= decadeInit && year <= decadeEnd;
  }
}
