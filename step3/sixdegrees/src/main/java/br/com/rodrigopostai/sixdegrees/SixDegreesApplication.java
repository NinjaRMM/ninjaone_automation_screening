package br.com.rodrigopostai.sixdegrees;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SixDegreesApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SixDegreesApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        if (!arguments.containsOption("file")) {
            throw new RuntimeException("File must be informed. ie: --file movies.json");
        }
        List<String> file = arguments.getOptionValues("file");
        List<String> decade = arguments.getOptionValues("decade");
        List<String> exactYear = arguments.getOptionValues("exactYear");
        List<String> afterYear = arguments.getOptionValues("afterYear");
        List<String> beforeYear = arguments.getOptionValues("beforeYear");
        List<String> title = arguments.getOptionValues("title");
        List<String> titleContains = arguments.getOptionValues("titleContains");
        List<String> genres = arguments.getOptionValues("genres");
        List<String> cast = arguments.getOptionValues("cast");
        System.out.println("Finished!!!");
    }

}
