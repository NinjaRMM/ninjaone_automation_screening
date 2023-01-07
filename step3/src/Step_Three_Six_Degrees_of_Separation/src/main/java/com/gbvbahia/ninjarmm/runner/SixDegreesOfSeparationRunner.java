package com.gbvbahia.ninjarmm.runner;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.gbvbahia.ninjarmm.service.SixDegreesService;
import com.gbvbahia.ninjarmm.service.io.MoviesDataService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(2)
public class SixDegreesOfSeparationRunner implements ApplicationRunner {

  private static final int ONE_ACTOR = 1;
  
  private final MoviesDataService moviesDataService;
  private final SixDegreesService sixDegreesService;
  private final String actorsArg;
  private final String actorDefault;
  private final String splitBy;
  private final String[] actorsDegrees = new String[2];
  
  public SixDegreesOfSeparationRunner(
      @Value("${app.args.def-actor}") String actorDefault,
      @Value("${app.args.actors}") String actorsArg,
      @Value("${app.args.split-by}") String splitBy,
      MoviesDataService moviesDataService,
      SixDegreesService sixDegreesService) {
    
    super();
    this.actorDefault = actorDefault;
    this.actorsArg = actorsArg;
    this.splitBy = splitBy;
    this.moviesDataService = moviesDataService;
    this.sixDegreesService = sixDegreesService;
  }

  @PostConstruct
  void init() {
    this.sixDegreesService.addSummaryListener((summary) -> log.info("{}", summary));
  }
  
  @Override
  public void run(ApplicationArguments args) throws Exception {
    
    for (String arg : args.getOptionNames()) {
      log.info("OptionName to process: {} OptionValues: {}", arg, args.getOptionValues(arg));
    }
    
    generate80Movies();
    
    fetchActors(args);
    
    sixDegreesService.calculateSeparation(actorsDegrees[0], actorsDegrees[1]);
    
  }

  protected void generate80Movies() throws Exception {
    moviesDataService.generate80MoviesIfNotExists();
  }

  protected void fetchActors(ApplicationArguments args) {
    String arg = args.getOptionValues(actorsArg).get(0);
    String[] actors = arg.split(splitBy);
    if (actors.length == ONE_ACTOR) {
      actorsDegrees[0] = actorDefault;
      actorsDegrees[1] = actors[0];
    } else {
      actorsDegrees[0] = actors[0];
      actorsDegrees[1] = actors[1];
    }
  }
}