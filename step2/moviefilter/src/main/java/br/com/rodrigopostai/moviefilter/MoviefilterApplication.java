package br.com.rodrigopostai.moviefilter;

import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import br.com.rodrigopostai.moviefilter.reader.MovieReader;
import br.com.rodrigopostai.moviefilter.writer.MovieWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class MoviefilterApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(MoviefilterApplication.class, args);
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
        if (CollectionUtils.isNotEmpty(file)) {
            MovieFilter filter = new MovieFilter();
            if (CollectionUtils.isNotEmpty(decade)) {
                filter.decade(Integer.parseInt(decade.get(0)));
            }
            if (CollectionUtils.isNotEmpty(exactYear)) {
                filter.exactYear(Integer.parseInt(exactYear.get(0)));
            }
            if (CollectionUtils.isNotEmpty(afterYear)) {
                filter.exactYear(Integer.parseInt(afterYear.get(0)));
            }
            if (CollectionUtils.isNotEmpty(beforeYear)) {
                filter.exactYear(Integer.parseInt(beforeYear.get(0)));
            }
            if (CollectionUtils.isNotEmpty(title)) {
                filter.exactTitle(title.get(0));
            }
            if (CollectionUtils.isNotEmpty(titleContains)) {
                filter.titleContains(titleContains.get(0));
            }
            if (CollectionUtils.isNotEmpty(genres)) {
                for (String genre: genres) {
                    filter.genre(genre);
                }
            }
            if (CollectionUtils.isNotEmpty(cast)) {
                for (String act: cast) {
                    filter.cast(act);
                }
            }

            MovieReader movieReader = new MovieReader(Paths.get(file.get(0)));

            try (MovieWriter writer = new MovieWriter(filter.createFileName())) {
                StreamSupport.stream(movieReader, false).filter(filter::matches)
                        .forEach(writer::writeMovie);
            }
        }
        System.out.println("Finished!!!");
    }

}
