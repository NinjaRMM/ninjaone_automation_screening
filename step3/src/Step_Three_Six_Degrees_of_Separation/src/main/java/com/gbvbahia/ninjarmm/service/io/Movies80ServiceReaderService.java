package com.gbvbahia.ninjarmm.service.io;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gbvbahia.ninjarmm.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class Movies80ServiceReaderService {

  private final String folder;
  private final String file;
  private final Gson gson = new Gson();
  
  public Movies80ServiceReaderService(
      @Value("${app.data.movies.output}") String folder,
      @Value("${app.data.movies.movies-80s}") String file) {
    
    super();
    this.folder = folder;
    this.file = file;
  }
  
  public List<Movie> get80sMovies() throws Exception {
    
    FileReader fileReader = new FileReader(new File(folder, file));
    Type type = new TypeToken<List<Movie>>() {}.getType();
    return gson.fromJson(fileReader, type);
  }
  
  
}
