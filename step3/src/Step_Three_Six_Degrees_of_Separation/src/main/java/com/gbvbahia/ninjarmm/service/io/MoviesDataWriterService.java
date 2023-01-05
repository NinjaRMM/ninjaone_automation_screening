package com.gbvbahia.ninjarmm.service.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.gbvbahia.ninjarmm.model.Movie;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MoviesDataWriterService implements WriterService<Movie>, Closeable {

  private final String outputFolder;
  private JsonWriter writer;
  private String filePath;
  
  public MoviesDataWriterService(@Value("${app.data.movies.output}") String outputFolder) {
    super();
    this.outputFolder = outputFolder;
  }

  public MoviesDataWriterService begin(String outputFileName) throws IOException {
    
    File outputFile = new File(outputFolder, outputFileName);
    filePath = outputFile.getCanonicalPath();
    writer = new JsonWriter(new FileWriter(outputFile));
    writer.beginArray();
    
    return this;
  }
  
  @Override
  public void process(Movie movie) throws IOException {
    writer.beginObject();
    
    writer.name("title").value(movie.getTitle());
    writer.name("year").value(movie.getYear());
    
    writer.name("cast");  
    writer.beginArray();
    for (String cast : movie.getCast()) {
      writer.value(cast);
    }
    writer.endArray();
    
    writer.name("genres");
    writer.beginArray();
    for (String cast : movie.getGenres()) {
      writer.value(cast);
    }
    writer.endArray();
    
    writer.endObject();
  }
  
  public void close() throws IOException {
    if (writer != null) {
      log.info("Process completed.");
      log.info("File saved at: {}", filePath);
      writer.endArray();
      writer.close();
    }
  }

}
