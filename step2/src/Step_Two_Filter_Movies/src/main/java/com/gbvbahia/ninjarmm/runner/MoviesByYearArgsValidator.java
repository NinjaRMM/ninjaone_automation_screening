package com.gbvbahia.ninjarmm.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class MoviesByYearArgsValidator implements ApplicationRunner {

  private final List<String> expectedArgs = new ArrayList<>();
  private final int decadeMin;
  private final int decadeMax;
  private final String outputFolder;

  public MoviesByYearArgsValidator(@Value("${app.args.decade}") String decadeArg,
      @Value("${app.args.output}") String outputArg,
      @Value("${app.valid.decade-min}") int decadeMin,
      @Value("${app.valid.decade-max}") int decadeMax,
      @Value("${app.data.movies.output}") String outputFolder) {
    
    super();
    expectedArgs.add(decadeArg);
    expectedArgs.add(outputArg);
    this.decadeMin = decadeMin;
    this.decadeMax = decadeMax;
    this.outputFolder = outputFolder;
  }

  @PostConstruct
  void init() {
    log.info("Expected args are: {}", String.join(", ", expectedArgs));
    log.info("Min and Max Decade: {} and {}", decadeMin, decadeMax);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    velidateAllRequiredArgsWereInformed(args);

    String decadeValueString = args.getOptionValues(expectedArgs.get(0)).get(0);
    String invalidDecadeText = String
        .format("The decade must ends with 0 and must be beetween %d and %d", decadeMin, decadeMax);

    String outputFileName = args.getOptionValues(expectedArgs.get(1)).get(0);
    
    
    validadeDecadeEndsWith0(decadeValueString, invalidDecadeText);

    validadeDecadeIsBetweenMinAndMax(decadeValueString, invalidDecadeText);
    
    validateFileAlreadyExists(outputFileName);
  }

  private void velidateAllRequiredArgsWereInformed(ApplicationArguments args) {
    for (String expected : expectedArgs) {
      if (!args.containsOption(expected)) {
        String error =
            String.format("These args: %s are expected!", String.join(", ", expectedArgs));
        log.error(error);
        throw new MoviesByYearArgsException(error);
      }
    }
  }

  private void validadeDecadeEndsWith0(String decadeValueString, String invalidDecade) {
    if (!StringUtils.endsWith(decadeValueString, "0")) {
      throw new MoviesByYearArgsException(invalidDecade);
    }
  }

  private void validadeDecadeIsBetweenMinAndMax(String decadeValueString, String invalidDecade) {
    try {
      
      Integer decadeValue = Integer.valueOf(decadeValueString);
      if (decadeValue < decadeMin || decadeValue > decadeMax) {
        throw new MoviesByYearArgsException(invalidDecade);
      }
      
    } catch (NumberFormatException e) {
      throw new MoviesByYearArgsException(invalidDecade, e);
    }
  }
  
  private void validateFileAlreadyExists(String outputFileName) {
    File outputFile = new File(outputFolder, outputFileName);
    if (outputFile.exists()) {
      String error = String.format("The file: %s already exists on folder: %s", outputFileName, outputFolder);
      log.error(error);
      throw new MoviesByYearArgsException(error);
    }
  }
}
