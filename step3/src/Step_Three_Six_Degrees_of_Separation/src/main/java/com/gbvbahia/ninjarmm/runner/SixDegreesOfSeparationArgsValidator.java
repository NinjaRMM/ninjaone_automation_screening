package com.gbvbahia.ninjarmm.runner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class SixDegreesOfSeparationArgsValidator implements ApplicationRunner {

  private static final int MIN_ACTORS = 1;
  private static final int MAX_ACTORS = 2;
  
  private final String actorsArg;
  private final String splitBy;

  public SixDegreesOfSeparationArgsValidator(
      @Value("${app.args.actors}") String actorsArg,
      @Value("${app.args.split-by}") String splitBy) {

    super();
    this.actorsArg = actorsArg;
    this.splitBy = splitBy;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    validateAllRequiredArgsWereInformed(args);
    validateActorsCount(args);
  }

  private void validateAllRequiredArgsWereInformed(ApplicationArguments args) {
    if (!args.containsOption(actorsArg)) {
      String error = String.format("This arg: '%s' is expected!", actorsArg);
      log.error(error);
      throw new SixDegreesOfSeparationArgsException(error);
    }
  }

  private void validateActorsCount(ApplicationArguments args) {
    String arg = args.getOptionValues(actorsArg).get(0);
    String[] actors = arg.split(splitBy);
    if (StringUtils.isBlank(arg) || actors.length < MIN_ACTORS || actors.length > MAX_ACTORS) {
      String error = String.format("The number of actors must be between %d and %d.", MIN_ACTORS, MAX_ACTORS);
      log.error(error);
      throw new SixDegreesOfSeparationArgsException(error);
    }
  }
}
