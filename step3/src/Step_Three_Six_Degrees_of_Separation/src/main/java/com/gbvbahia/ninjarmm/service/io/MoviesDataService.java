package com.gbvbahia.ninjarmm.service.io;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MoviesDataService {

  private final MoviesDataReaderService moviesDataReaderService;
  private final ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider;
  private final Integer decadeDefault;
  private final String moviesJsonDefault;
  
  public MoviesDataService(
      @Value("${app.args.def-decade}") Integer decadeDefault,
      @Value("${app.data.movies-80s}") String moviesJsonDefault,
      MoviesDataReaderService moviesDataReaderService,
      ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider) {
    
    super();
    this.decadeDefault = decadeDefault;
    this.moviesJsonDefault = moviesJsonDefault;
    this.moviesDataReaderService = moviesDataReaderService;
    this.moviesDataWriterServiceProvider = moviesDataWriterServiceProvider;
  }
  
  public void generate80MoviesIfNotExists() throws Exception {
    
    if (Files.notExists(Path.of(moviesJsonDefault))) {
      MoviesDataWriterService writer = getMoviesDataWriterService();
      
      log.info("Starting to process {} decade movies...", decadeDefault);
      
      try (writer) {
        moviesDataReaderService.fetchAllMoviesByDecade(decadeDefault, writer.begin(moviesJsonDefault));
      }
      log.info("The file {} could not be found, so it was generated. .", moviesJsonDefault);
    }
  }
  
  public MoviesDataWriterService getMoviesDataWriterService() {
    return moviesDataWriterServiceProvider.getObject();
}
}
