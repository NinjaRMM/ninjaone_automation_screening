package com.gbvbahia.ninjarmm.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(2)
public class MoviesByYearRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    for (String arg : args.getOptionNames()) {
      log.info("OptionName to process: {} OptionValues: {}", arg, args.getOptionValues(arg));
    }
  }

}
