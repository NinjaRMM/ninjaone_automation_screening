package com.gbvbahia.ninjarmm.service;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoviesDataService {

  private final MoviesDataReaderService moviesDataReaderService;
  private final ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider;

  public void generateMovies(Integer decade, String moviesJsonDefault) throws Exception {

      MoviesDataWriterService writer = getMoviesDataWriterService();

      log.info("Starting to process {} decade movies...", decade);

      try (writer) {
        moviesDataReaderService.fetchAllMoviesByDecade(decade, writer.begin(moviesJsonDefault));
      }
      
      log.debug("The file was generated.", moviesJsonDefault);
  }

  public MoviesDataWriterService getMoviesDataWriterService() {
    return moviesDataWriterServiceProvider.getObject();
  }
}
