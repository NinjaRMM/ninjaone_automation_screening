package com.gbvbahia.ninjarmm.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.gbvbahia.ninjarmm.service.MoviesDataReaderService;
import com.gbvbahia.ninjarmm.service.MoviesDataWriterService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(2)
public class SixDegreesOfSeparationRunner implements ApplicationRunner {

  private final MoviesDataReaderService moviesDataReaderService;
  private final ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider;
  private final Integer decadeDefault;
  private final String actorDefault;
  private final String moviesJsonDefault;
  
  public SixDegreesOfSeparationRunner(
      @Value("${app.args.def-decade}") Integer decadeDefault,
      @Value("${app.args.def-actor}") String actorDefault,
      @Value("${app.data.movies-80s}") String moviesJsonDefault,
      MoviesDataReaderService moviesDataReaderService,
      ObjectFactory<MoviesDataWriterService> moviesDataWriterServiceProvider) {
    
    super();
    this.decadeDefault = decadeDefault;
    this.actorDefault = actorDefault;
    this.moviesJsonDefault = moviesJsonDefault;
    this.moviesDataReaderService = moviesDataReaderService;
    this.moviesDataWriterServiceProvider = moviesDataWriterServiceProvider;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    
    for (String arg : args.getOptionNames()) {
      log.info("OptionName to process: {} OptionValues: {}", arg, args.getOptionValues(arg));
    }
    
    generate80Movies();
    //TODO get actors names
  }

  private void generate80Movies() throws Exception, IOException {
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
