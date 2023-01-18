package com.ninjaone.step3.cli;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner, ExitCodeGenerator {

     private final ParseCommand parseCommand;

     private final IFactory factory; // auto-configured to inject PicocliSpringFactory
     
     private int exitCode;

     public ApplicationRunner(ParseCommand parseCommand, IFactory factory) {
          this.parseCommand = parseCommand;
          this.factory = factory;
     }

     @Override
     public void run(String... args) throws Exception {
          exitCode = new CommandLine(parseCommand, factory).execute(args);
     }

     @Override
     public int getExitCode() {
          return exitCode;
     }
}