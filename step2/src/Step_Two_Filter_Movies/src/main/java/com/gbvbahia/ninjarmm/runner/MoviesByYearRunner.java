package com.gbvbahia.ninjarmm.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.gbvbahia.ninjarmm.service.MoviesDataService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(2)
public class MoviesByYearRunner implements ApplicationRunner {

  private final MoviesDataService moviesDataService;
  private final String decadeArg;
  private final String outputArg;

  public MoviesByYearRunner(
      @Value("${app.args.decade}") String decadeArg,
      @Value("${app.args.output}") String outputArg,
      MoviesDataService moviesDataService) {
    
    super();
    this.moviesDataService = moviesDataService;
    this.decadeArg = decadeArg;
    this.outputArg = outputArg;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    
    for (String arg : args.getOptionNames()) {
      log.info("OptionName to process: {} OptionValues: {}", arg, args.getOptionValues(arg));
    }
    
    Integer decade = Integer.valueOf(args.getOptionValues(decadeArg).get(0));
    String output = args.getOptionValues(outputArg).get(0);
    
    log.info("Starting to process {} decade movies...", decade);
    
    moviesDataService.generateMovies(decade, output);
  }
  

}
